package com.example.simula_service.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class SimulaService {

    //----------------[P1]- FUNCIONES DE CALCULO DE CRÉDITO HIPOTECARIO-----------------//
    // + SIMULACIÓN DE CRÉDITO HIPOTECARIO POR VALORES INGRESADOS:
    public double Credito_Hipotecario(double P, double r, double n, double V, int tipo) {
        // P = MONTO DEL PRESTAMO
        // r = TASA DE INTERES ANUAL
        // n = PLAZO DEL PRESTAMO EN AÑOS
        // V = VALOR ACTUAL DE LA PROPIEDAD
        // tipo = 1 -> PRIMERA VIVIENDA
        // tipo = 2 -> SEGUNDA VIVIENDA
        // tipo = 3 -> PROPIEDADES COMERCIALES
        // tipo = 4 -> REMODELACIÓN
        //-------------------------------------------------------------------------//
        if(n <= 30 && 0.035<= r && r <=0.05 && P <= V*0.8 && tipo == 1){ // Primera Vivienda
            System.out.println("PRÉSTAMO REALIZADO: Primera Vivienda");
            //System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            //System.out.println("1.- Comprobante de ingresos");
            //System.out.println("2.- Certificado de avalúo");
            //System.out.println("3.- Historial crediticio");
            r = r / 12; // PASA DE TASA ANUAL A MENSUAL
            n = n * 12;        // PASA DE AÑOS A CANTIDAD DE PAGOS
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
            //-------------------------------------------------------------------------//
        } else if(n <= 20 && 0.04<= r && r <=0.06 && P <= V*0.7 && tipo == 2){ //Segunda Vivienda
            System.out.println("PRESTAMO REALIZADO: Segunda Vivienda");
            //System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            //System.out.println("1.- Comprobante de ingresos");
            //System.out.println("2.- Certificado de avalúo");
            //System.out.println("3.- Escritura de la primera vivienda");
            //System.out.println("4.- Historial crediticio");
            r = r / 12;
            n = n * 12;
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
            //-------------------------------------------------------------------------//
        } else if(n <= 25 && 0.05<= r && r <=0.07 && P <= V*0.6 && tipo == 3){ //Propiedades Comerciales
            System.out.println("PRESTAMO REALIZADO: Propiedades Comerciales");
            //System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            //System.out.println("1.-Estado financiero del negocio");
            //System.out.println("2.- Comprobante de ingresos");
            //System.out.println("3.- Certificado de avalúo");
            //System.out.println("4.- Plan de negocios");
            r = r / 12;
            n = n * 12;
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
            //-------------------------------------------------------------------------//
        } else if(n <=15 && 0.045<= r && r <=0.06 && P<= V*0.5 && tipo == 4){ //Remodelación
            System.out.println("PRESTAMO REALIZADO: Remodelación");
            //System.out.println("LOS DOCUMENTOS A INGRESAR PARA ESTE TIPO DE PRÉSTAMO SON:");
            //System.out.println("1.- Comprobante de ingresos");
            //System.out.println("2.- Presupuesto de la remodelación\n");
            //System.out.println("3.- Certificado de avalúo actualizado");
            r = r / 12;
            n = n * 12;
            double M = P * (r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return M;
            //-------------------------------------------------------------------------//
        }else{
            //System.out.println("TOMA ESTE 0");
            return 0;
        }
    }
}