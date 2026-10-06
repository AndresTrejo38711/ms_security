package com.uc.ms_security.dto.session;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseSessionDTO {

    @NotBlank(
            message = "El token es obligatorio"
    )
    @Size(
            max = 512,
            message = "El token debe tener máximo 512 caracteres"
    )
    private String token;

    @NotNull(
            message = "La fecha de expiración es obligatoria"
    )
    @Future(
            message = "La fecha de expiración debe ser una fecha futura"
    )
    private LocalDateTime expiration;

    @Pattern(
            regexp = "^[0-9]{6}$",
            message = "El código 2FA debe tener 6 dígitos"
    )
    private String code2FA;
}
