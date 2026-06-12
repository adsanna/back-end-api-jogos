package com.backend.apiJogos.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.backend.apiJogos.models.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGameDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "gameId não pode ser nulo!")
    private Long gameId;

    @NotNull(message = "Status não pode ser nulo!")
    private Status status;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal horasJogadas;

    private LocalDate dataInicio;

    private LocalDate dataFim;
}
