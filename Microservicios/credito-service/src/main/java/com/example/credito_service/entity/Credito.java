package com.example.credito_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private double montop;
    private int plazo;
    private double intanu;
    private double intmen;
    private double segudesg;
    private double seguince;
    private double comiad;
    private String state;
    private int usuarioId;
}