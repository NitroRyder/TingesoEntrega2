package com.example.credito_service.service;

import com.example.credito_service.entity.Credito;
import com.example.credito_service.model.Ahorro;
import com.example.credito_service.model.Usuario;
import com.example.credito_service.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CreditoService {

        @Autowired
        CreditoRepository creditoRepository;

        RestTemplate restTemplate;

        public List<Credito> getAll() {return creditoRepository.findAll();}

        public Credito getCreditoById(int id) {return creditoRepository.findById(id).orElse(null);}

        public Credito saveCredito(Credito credito) {
            Credito creditoNew = creditoRepository.save(credito);
            return creditoNew;
        }

        public void deleteCredito(int id) {creditoRepository.deleteById(id);}

        public List<Credito> byUsuarioId(int usuarioId) {return creditoRepository.findByUsuarioId(usuarioId);}
    //-----------------------[P3]- FUNCIONES DE CREACIÓN  DE SOLICITUD DE CRÉDITO-------------------------//
    // + CREACIÓN DE SOLICITUD DE CRÉDITO POR VALORES INGRESADOS BAJO ID DE USUARIO INGRESADO:
    public Credito createSolicitud(Long userId, double montop, int plazo, double intanu, double intmen, double segudesg, double seguince, double comiad, byte[] comprobanteIngresos, byte[] certificadoAvaluo, byte[] historialCrediticio, byte[] escrituraPrimeraVivienda, byte[] planNegocios, byte[] estadosFinancieros, byte[] presupuestoRemodelacion, byte[] dicom) {
        //Usuario usuario = restTemplate.getForObject("http;//usuario-service/usuario/" + userId, Usuario.class); // OBTENCION DE USUARIO POR ID
        //-------------------------------------------------------------------------//
        // CREACIÓN DE NUEVA SOLICITUD
        Credito solicitud = new Credito();
        solicitud.setMontop(montop);
        solicitud.setPlazo(plazo);
        solicitud.setIntanu(intanu);
        solicitud.setIntmen(intmen);
        solicitud.setSegudesg(segudesg);
        solicitud.setSeguince(seguince);
        solicitud.setComiad(comiad);
        solicitud.setComprobanteIngresos(comprobanteIngresos);
        solicitud.setCertificadoAvaluo(certificadoAvaluo);
        solicitud.setHistorialCrediticio(historialCrediticio);
        solicitud.setEscrituraPrimeraVivienda(escrituraPrimeraVivienda);
        solicitud.setPlanNegocios(planNegocios);
        solicitud.setEstadosFinancieros(estadosFinancieros);
        solicitud.setPresupuestoRemodelacion(presupuestoRemodelacion);
        solicitud.setDicom(dicom);
        solicitud.setState("PENDIENTE");
        solicitud.setUsuarioId(userId.intValue()); // ASIGNACION DE ID DE USUARIO A SOLICITUD
        //-------------------------------------------------------------------------//
        // GUARDADO DE NUEVA SOLICITUD
        Credito savedSolicitud = creditoRepository.save(solicitud);
        //-------------------------------------------------------------------------//
        return savedSolicitud;
    }
    //-----------------------[P5]- FUNCIONES DE SEGUIMENTO ---------------------------------------//
    // + SEQUIMIENTO DEL ESTADO DE LA SOLICITUD POR ID DEL USUARIO:
    public Credito followCredito(Long userId, Long creditId) {
        // Fetch the Usuario object using RestTemplate
        Usuario usuario = restTemplate.getForObject("http://localhost:8030/usuario/" + userId, Usuario.class); // OBTENCIÓN DE USUARIO COMPLETO POR ID
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO");
        }
        // ENTREGAME LA SOLICITUD POR SU ID
        Credito solicitud = getCreditoById(creditId.intValue()); // OBTENGO LA SOLICITUD DEL USUARIO -> PARA EL RETORNO DE ARCHIVOS
        if (solicitud == null) {
            throw new IllegalArgumentException("ERROR: SOLICITUD NO ENCONTRADA");
        }
        //-------------------------------------------------------------------------//
        return solicitud;
    }
    //-----------------------[P6]- FUNCIONES DE CALCULO DE COSTOS TOTALES-------------------//
    // + CALCULO DE COSTOS TOTALES DE LA SOLICITUD DE CRÉDITO POR ID DEL USUARIO:
    public List<Double> calcularCostosTotales(Long userId, Long creditId) {
        // Fetch the Usuario object using RestTemplate
        Usuario usuario = restTemplate.getForObject("http://localhost:8030/usuario/" + userId, Usuario.class); // OBTENCIÓN DE USUARIO COMPLETO POR ID
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO");
        }
        // ENTREGAME LA SOLICITUD POR SU ID
        Credito solicitud = getCreditoById(creditId.intValue()); // OBTENGO LA SOLICITUD DEL USUARIO -> PARA EL RETORNO DE ARCHIVOS
        if (solicitud == null){
            String notificationUrl = "http://localhost:8030/usuario/addnotification/" + userId;
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
        //-------------------------------------------------------------------------//
        // ANÁLISIS DE QUE LOS DATOS SOLICITADOS DEL CRÉDITO SEAN CORRECTOS// VALORES REDONDEADOS PARA COMPARAR
        if (Math.round(intmen) == Math.round(intanu / 12.0)) {
            //System.out.println("TASA DE INTERÉS MENSUAL CORRECTA");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//
            // GUARDAR LA SOLICITUD ACTUALIZADA
            Credito savedSolicitud = creditoRepository.save(solicitud);
            //-------------------------------------------------------------------------//
            // ENVIAR NOTIFICACIÓN AL SERVICIO DE USUARIO
            String notificationUrl = "http://localhost:8030/usuario/addnotification/" + userId;
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
        //System.out.println("CUOTA MENSUAL DEL PRÉSTAMO: "+Math.round(cuota));
        // [PASO 2]- CALCULO DE LOS SEGUROS------------------------------------//
        double segudesgtotal = montop * segudesg;
        resultados.add(segudesgtotal);
        resultados.add(seguince);
        //System.out.println("SEGURO DE DESGRAVAMEN: "+Math.round(segudesgtotal));
        // [PASO 3]- CALCULO DE COMISION POR ADMINISTRACIÓN--------------//
        double comiadtotal = montop * comiad;
        resultados.add(comiadtotal);
        //System.out.println("COMISIÓN ADMINISTRATIVA: "+Math.round(comiadtotal));
        // [PASO 4]- CALCULO DE COSTO TOTAL DE PRESTAMO------------------//
        double costomensual = cuota + segudesgtotal + seguince;
        double costototal = (costomensual * meses) + comiadtotal;
        resultados.add(costomensual);
        resultados.add(costototal);
        //System.out.println("COSTO MENSUAL DEL PRÉSTAMO: "+Math.round(costomensual));
        // [PASO 5]- REVICIÓN Y ENTREGA DE SOLICITUD-------------------------//
        //System.out.println("COSTO TOTAL DEL PRÉSTAMO: "+Math.round(costototal));

        return resultados;
    }
}
