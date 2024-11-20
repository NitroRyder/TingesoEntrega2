package com.example.usuario_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credito {
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