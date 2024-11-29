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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double montop;
    private int plazo;
    private double intanu;
    private double intmen;
    private double segudesg;
    private double seguince;
    private double comiad;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] comprobanteIngresos;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] certificadoAvaluo;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] historialCrediticio;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] escrituraPrimeraVivienda;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] planNegocios;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] estadosFinancieros;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] presupuestoRemodelacion;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] dicom;
    private String state;
    private int usuarioId;
}