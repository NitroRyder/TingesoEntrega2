package com.example.credito_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
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
    @Lob
    private byte[] comprobanteIngresos;
    @Lob
    private byte[] certificadoAvaluo;
    @Lob
    private byte[] historialCrediticio;
    @Lob
    private byte[] escrituraPrimeraVivienda;
    @Lob
    private byte[] planNegocios;
    @Lob
    private byte[] estadosFinancieros;
    @Lob
    private byte[] presupuestoRemodelacion;
    @Lob
    private byte[] dicom;
    private String state;
    private int usuarioId;
}