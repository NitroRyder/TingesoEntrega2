package com.example.simula_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Simula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double P;
    private double r;
    private double n;
    private double V;
    private int tipo;
}