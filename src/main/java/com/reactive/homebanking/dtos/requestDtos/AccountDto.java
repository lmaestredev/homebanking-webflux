package com.reactive.homebanking.dtos.requestDtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto{

    @Valid
    @NotNull(message = "[CUENTA] [Cliente] Campo Requerido: La Cuenta debe poseer información del Cliente.")
    private String clienteId;

    @DecimalMin(value = "0.00", inclusive = true, message = "[CUENTA] [saldo_Global] El Saldo Inicial deber ser mayor o igual a 0.00")
    @DecimalMax(value = "1000000.00", inclusive = true, message = "[CUENTA] [saldo_Global] El Saldo Inicial deber ser menor o igual a 1000000.00")
    //@Digits(integer = 7, fraction = 2, message = "[CUENTA] [saldo_Global] El Formato del Saldo debe ser 7 digitos enteros y 2 decimales")
    private BigDecimal totalBalance;


    public AccountDto(String clienteId, BigDecimal totalBalance) {
        this.clienteId = clienteId;
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "M_Cuenta_DTO{" +
                ", clienteId=" + clienteId +
                ", totalBalance=" + totalBalance +
                '}';
    }
}
