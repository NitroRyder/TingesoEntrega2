package com.example.credito_service.model;

import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    private int id;
    private String rut;
    private String name;
    private int age;
    private int workage;
    private int houses;
    private int valorpropiedad;
    private int ingresos;
    private int sumadeuda;
    private String objective;
    private String independiente;
    @ElementCollection
    private List<String> notifications = new ArrayList<>();
}
