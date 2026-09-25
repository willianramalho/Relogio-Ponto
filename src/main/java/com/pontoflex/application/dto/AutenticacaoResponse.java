package com.pontoflex.application.dto;

import java.util.List;

public record AutenticacaoResponse(
        String token, String tipo, long expiraEmSegundos, String email, List<String> roles) {}
