package com.reactive.homebanking.dtos.requestDtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientDto
{
    @NotNull(message = "[CLIENTE] [nombre] Campo Requerido: Id.")
    private String nombre;

    public ClientDto() {
    }

    public ClientDto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
