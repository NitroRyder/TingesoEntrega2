package com.example.total_service.service;

import com.example.total_service.model.Credito;
import com.example.total_service.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.ArrayList;

@Service
public class TotalService {

    @Autowired
    private RestTemplate restTemplate;

    //-----------------------[P6]- FUNCIONES DE CALCULO DE COSTOS TOTALES-------------------//
    // + CALCULO DE COSTOS TOTALES DE LA SOLICITUD DE CRÉDITO POR ID DEL USUARIO:
    public List<Double> calcularCostosTotales(Long userId, Long creditId) {
        // Fetch the Usuario object using RestTemplate
        Usuario usuario = restTemplate.getForObject("http://usuario-service/usuario/" + userId, Usuario.class); // OBTENCIÓN DE USUARIO COMPLETO POR ID
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO");
        }

        ResponseEntity<List<Credito>> response = restTemplate.exchange(
                "http://usuario-service/usuario/creditos/" + userId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Credito>>() {}
        );
        List<Credito> solicitudes = response.getBody();
        if (solicitudes == null) {
            throw new IllegalArgumentException("ERROR: USUARIO NO TIENE SOLICITUDES");
        }

        Credito solicitud = null;
        for (Credito c : solicitudes) {
            if (c.getId() == (creditId)) {
                solicitud = c;
                break;
            }
        }

        if (solicitud == null) {
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "SOLICITUD NO EXISTENTE EN EL USUARIO.", String.class);
            return null;
        }



        //-------------------------------------------------------------------------//
        double montop = solicitud.getMontop();        // MONTO DEL PRÉSTAMO
        int plazo = solicitud.getPlazo();                      // PLAZO DEL PRÉSTAMO EN AÑOS
        double intanu = solicitud.getIntanu();            // TASA DE INTERES ANUAL
        double intmen = solicitud.getIntmen();          // TASA DE INTERES MENSUAL
        double segudesg = solicitud.getSegudesg();// SEGURO DE DESGRAVAMEN
        double seguince = solicitud.getSeguince();  // SEGURO DE INCENDIO
        double comiad = solicitud.getComiad();       // COMISIÓN ADMINISTRATIVA
        double meses = plazo * 12; // PASA DE AÑOS A MESES

        System.out.printf("Monto: %f\n", montop);
        System.out.printf("Plazo: %d\n", plazo);
        System.out.printf("Tasa de interés anual: %f\n", intanu);
        System.out.printf("Tasa de interés mensual: %f\n", intmen);
        System.out.printf("Seguro de desgravamen: %f\n", segudesg);
        System.out.printf("Seguro de incendio: %f\n", seguince);
        System.out.printf("Comisión administrativa: %f\n", comiad);
        System.out.printf("Meses: %f\n", meses);
        //-------------------------------------------------------------------------//
        // ANÁLISIS DE QUE LOS DATOS SOLICITADOS DEL CRÉDITO SEAN CORRECTOS// VALORES REDONDEADOS PARA COMPARAR
        if (Math.round(intmen) == Math.round(intanu / 12.0)) {
            //System.out.println("TASA DE INTERÉS MENSUAL CORRECTA");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//
            // GUARDAR LA SOLICITUD ACTUALIZADA
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", solicitud, Credito.class);

            //-------------------------------------------------------------------------//
            // ENVIAR NOTIFICACIÓN AL SERVICIO DE USUARIO
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "TASA DE INTERÉS MENSUAL ES INCORRECTA", String.class);
            //-------------------------------------------------------------------------//
            return null;
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        List<Double> resultados = new ArrayList<>();
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [PASO 1]- CALCULO DE CUOTA MENSUAL DEL PRESTAMO------------------------------------//
        double cuota = montop * (intmen * Math.pow(1 + intmen, meses)) / (Math.pow(1 + intmen, meses) - 1);
        resultados.add(cuota);
        System.out.println("CUOTA MENSUAL DEL PRÉSTAMO: "+Math.round(cuota));
        // [PASO 2]- CALCULO DE LOS SEGUROS------------------------------------//
        double segudesgtotal = montop * segudesg;
        resultados.add(segudesgtotal);
        resultados.add(seguince);
        System.out.println("SEGURO DE DESGRAVAMEN: "+Math.round(segudesgtotal));
        // [PASO 3]- CALCULO DE COMISION POR ADMINISTRACIÓN--------------//
        double comiadtotal = montop * comiad;
        resultados.add(comiadtotal);
        System.out.println("COMISIÓN ADMINISTRATIVA: "+Math.round(comiadtotal));
        // [PASO 4]- CALCULO DE COSTO TOTAL DE PRESTAMO------------------//
        double costomensual = cuota + segudesgtotal + seguince;
        double costototal = (costomensual * meses) + comiadtotal;
        resultados.add(costomensual);
        resultados.add(costototal);
        System.out.println("COSTO MENSUAL DEL PRÉSTAMO: "+Math.round(costomensual));
        // [PASO 5]- REVICIÓN Y ENTREGA DE SOLICITUD-------------------------//
        System.out.println("COSTO TOTAL DEL PRÉSTAMO: "+Math.round(costototal));

        return resultados;
    }
}