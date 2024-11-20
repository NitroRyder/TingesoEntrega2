package com.example.credito_service.entity;

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

public class Credito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double montop;
    private int plazo;
    private double intanu;
    private double intmen;
    private double segudesg;
    private double seguince;
    private double comiad;
    private byte[] comprobanteIngresos;
    private byte[] certificadoAvaluo;
    private byte[] historialCrediticio;
    private byte[] escrituraPrimeraVivienda;
    private byte[] planNegocios;
    private byte[] estadosFinancieros;
    private byte[] presupuestoRemodelacion;
    private byte[] dicom;
    private String state;
    private int usuarioId;
}