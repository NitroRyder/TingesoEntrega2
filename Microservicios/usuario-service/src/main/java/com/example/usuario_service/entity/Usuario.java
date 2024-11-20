package com.example.usuario_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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