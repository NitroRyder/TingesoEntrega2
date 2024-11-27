package com.example.credito_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ahorro {
    private int id;
    private int transaccion;
    private String tipo;
    private int usuarioId;
}