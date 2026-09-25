package com.pontoflex.application.service;

import com.pontoflex.application.dto.AutenticacaoResponse;
import com.pontoflex.application.dto.LoginRequest;
import com.pontoflex.application.dto.RegistroRequest;
import com.pontoflex.application.dto.RegistroResponse;
import com.pontoflex.domain.exception.CpfJaCadastradoException;
import com.pontoflex.domain.exception.CredenciaisInvalidasException;
import com.pontoflex.domain.exception.EmailJaCadastradoException;
import com.pontoflex.domain.exception.MatriculaJaCadastradaException;
import com.pontoflex.infrastructure.persistence.entity.Colaborador;
import com.pontoflex.infrastructure.persistence.entity.Role;
import com.pontoflex.infrastructure.persistence.entity.Usuario;
import com.pontoflex.infrastructure.persistence.repository.ColaboradorRepository;
import com.pontoflex.infrastructure.persistence.repository.RoleRepository;
import com.pontoflex.infrastructure.persistence.repository.UsuarioRepository;
import com.pontoflex.infrastructure.security.JwtService;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final UUID ESCALA_PADRAO_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    private static final UUID LOCAL_TRABALHO_PADRAO_ID =
            UUID.fromString("22222222-2222-2222-2222-222222222222");
    private static final String ROLE_PADRAO = "COLABORADOR";

    private final UsuarioRepository usuarioRepository;
    private final ColaboradorRepository colaboradorRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public RegistroResponse registrar(RegistroRequest req) {
        if (usuarioRepository.existsByEmail(req.email())) {
            throw new EmailJaCadastradoException(req.email());
        }
        if (colaboradorRepository.existsByCpf(req.cpf())) {
            throw new CpfJaCadastradoException(req.cpf());
        }
        if (colaboradorRepository.existsByMatricula(req.matricula())) {
            throw new MatriculaJaCadastradaException(req.matricula());
        }

        Colaborador colaborador = new Colaborador();
        colaborador.setNome(req.nome());
        colaborador.setCpf(req.cpf());
        colaborador.setMatricula(req.matricula());
        colaborador.setEscalaId(ESCALA_PADRAO_ID);
        colaborador.setLocalTrabalhoId(LOCAL_TRABALHO_PADRAO_ID);
        colaborador.setDataAdmissao(req.dataAdmissao());
        colaborador = colaboradorRepository.save(colaborador);

        Role roleColaborador = roleRepository
                .findByNome(ROLE_PADRAO)
                .orElseThrow(() -> new IllegalStateException(
                        "Role padrao COLABORADOR nao encontrada - verifique a migration V1"));

        Usuario usuario = new Usuario();
        usuario.setColaborador(colaborador);
        usuario.setEmail(req.email());
        usuario.setSenhaHash(passwordEncoder.encode(req.senha()));
        usuario.setRoles(Set.of(roleColaborador));
        usuario = usuarioRepository.save(usuario);

        return new RegistroResponse(usuario.getId(), colaborador.getId(), usuario.getEmail(), colaborador.getMatricula());
    }

    @Transactional(readOnly = true)
    public AutenticacaoResponse login(LoginRequest req) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.email(), req.senha()));
        } catch (AuthenticationException ex) {
            throw new CredenciaisInvalidasException();
        }

        Usuario usuario = usuarioRepository.findByEmail(req.email()).orElseThrow(CredenciaisInvalidasException::new);

        List<String> roles = usuario.getRoles().stream().map(Role::getNome).toList();
        String token = jwtService.gerarToken(usuario.getEmail(), roles);

        return new AutenticacaoResponse(
                token, "Bearer", jwtService.getExpiracaoMinutos() * 60, usuario.getEmail(), roles);
    }
}
