package com.reactive.homebanking.dtos.requestDtos;

public class RabbitErrorDto {

   private String error;

    public RabbitErrorDto() {
    }

    public RabbitErrorDto(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
