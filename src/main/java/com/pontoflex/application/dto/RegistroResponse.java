package com.pontoflex.application.dto;

import java.util.UUID;

public record RegistroResponse(UUID usuarioId, UUID colaboradorId, String email, String matricula) {}
