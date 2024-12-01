package com.example.evalua_service.service;

import com.example.evalua_service.model.Credito;
import com.example.evalua_service.model.Ahorro;
import com.example.evalua_service.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EvaluaService {

    @Autowired
    private RestTemplate restTemplate;

    //-----------------------[P4]- EVALUACIÓN DE CRÉDITO-------------------------//
    // + REVICIÓN DE SOLCITUD CREADA Y ENTREGA DE ARCHIVOS EN CASO DE PASAR LAS PRUEBAS:
    public int evaluateCredito(Long userId, Long creditId) {
        //--------------------------------------------------------------------------------//
        //OBTENCION DEL USUARIO POR SU ID
        Usuario usuario = restTemplate.getForObject("http:/usuario-service/usuario/" + userId, Usuario.class); // OBTENCIÓN DE USUARIO COMPLETO POR ID
        if (usuario == null) {
            System.out.printf("ERROR: USUARIO NO ENCONTRADO");
            return 0;
        }
        //--------------------------------------------------------------------------------//
        // OBTENCIÓN DE LA LISTA DE CREDITOS DEL USUARIO POR SU ID DE USUARIO
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
        //--------------------------------------------------------------------------------//
        // OBTENCIÓN DEL CREDITO A MODIFICAR
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
            return 0;
        }
        //-------------------------------------------------------------------------//
        if ( !"PENDIENTE".equalsIgnoreCase(solicitud.getState())) {
            System.out.println("SOLICITUD NO ESTÁ EN ESTADO PENDIENTE");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ERROR: LA SOLICITUD DE CRÉDITO NO ESTÁ EN ESTADO PENDIENTE", String.class);
            return 0;
        }
        //-------------------------------------------------------------------------//
        // OBTENCIÓN DE DATOS DE LA SOLICITUD
        double montop = solicitud.getMontop();        // MONTO DEL PRÉSTAMO
        int plazo = solicitud.getPlazo();                      // PLAZO DEL PRÉSTAMO EN AÑOS
        double intanu = solicitud.getIntanu();            // TASA DE INTERES ANUAL
        double intmen = solicitud.getIntmen();          // TASA DE INTERES MENSUAL
        double segudesg = solicitud.getSegudesg();// SEGURO DE DESGRAVAMEN
        double seguince = solicitud.getSeguince();  // SEGURO DE INCENDIO
        double comiad = solicitud.getComiad();       // COMISIÓN ADMINISTRATIVA
        double meses = plazo * 12;                           // PASA DE AÑOS A MESES
        //-------------------------------------------------------------------------//
        // ANÁLISIS DE QUE LOS DATOS SOLICITADOS DEL CRÉDITO SEAN CORRECTOS// VALORES REDONDEADOS PARA COMPARAR
        if (Math.round(intmen) == Math.round(intanu / 12.0)) {
            //System.out.println("TASA DE INTERÉS MENSUAL CORRECTA");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "TASA DE INTERÉS MENSUAL ES INCORRECTA", String.class);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO

            HttpEntity<Credito> request = new HttpEntity<>(solicitud);
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

            return 0;
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R1]- RELACIÓN CUOTA/INGRESO------------------------------------//
        double cuota = montop * (intmen * Math.pow(1 + intmen, meses)) / (Math.pow(1 + intmen, meses) - 1);
        double cuotaing = (Math.round(cuota) / Math.round(usuario.getIngresos())) * 100;
        if (cuotaing <= 0.35) {
            // TIENE QUE SER MENOR O IGUAL QUE EL UMBRAL ESTABLECIDO POR EL BANCO
            //System.out.println("RELACIÓN CUOTA/INGRESO ACEPTADA");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "RELACIÓN CUOTA/INGRESO RECHAZADA: CUOTA/INGRESO TIENE QUE SER MENOR O IGUAL QUE EL UMBRAL ESTABLECIDO POR EL BANCO", String.class);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO

            HttpEntity<Credito> request = new HttpEntity<>(solicitud);
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

            return 0;
        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R2]- HISTORIAL DE  CREDITOS--------------------------------------//
        // OBRENCIÓN DE documents DE USUARIO

        // |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||| VER COMO HACER

        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // [R3]- -------------------------------------------------------------------//
        // VER SI DENTRO AÑOS DE TRABAJO ES MAYOR O IGUAL A 2
        if (usuario.getWorkage() >= 1 && usuario.getIndependiente().equalsIgnoreCase("ASALARIADO")) {
            //System.out.println("AÑOS DE TRABAJO ACTUAL ACEPTADOS");
        } else if (usuario.getIndependiente().equalsIgnoreCase("INDEPENDIENTE")) {
            //System.out.println("TRABAJADOR INDEPENDIENTE");
            // OBTENCIÓN DE LOS AHORROS DEL USUARIO
            List<Ahorro> ahorros =  restTemplate.getForObject("http://ahorro-service/ahorro/byusuario/" + userId, List.class); // OBTENGO LOS AHORROS DEL USUARIO -> PARA REALIZAR EVALUACIÓN
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "POR FAVOR, SI NO ERES UN TRABAJADOR INDEPENDIENTE, DEBES TENER MÁS DE 1 AÑO DE TRABAJO", String.class);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO

            HttpEntity<Credito> request = new HttpEntity<>(solicitud);
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

            return 0;
        }
        // [R4]--------------------------------------------------------------------//
        // RECHAZO DE LA SOLICITUD SI LA SUMA DE LAS DEUDAS ES MAYOR AL 50% DE LOS INGRESOS
        if (Double.valueOf(usuario.getSumadeuda()) > usuario.getIngresos() * 0.5) {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "LA SUMA DE DEUDAS NO PUEDE SER MAYOR AL 50% DE LOS INGRESOS", String.class);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO

            HttpEntity<Credito> request = new HttpEntity<>(solicitud);
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

            return 0;
        } else {
            //System.out.println("LA SUMA DE DEUDAS ES MENOR AL 50% DE LOS INGRESOS");
        }
        // [R5]--------------------------------------------------------------------//
        // REVICIÓN DEL TIPO DE CRÉDITO SOLICITADO, SI NO COINSIDE, RECHAZA
        if (plazo <= 30 && 0.035 <= intanu && intanu <= 0.05 && montop <= usuario.getValorpropiedad() * 0.8 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 &&
                solicitud.getHistorialCrediticio() != null && solicitud.getHistorialCrediticio().length > 0 && usuario.getHouses() == 0 && usuario.getObjective().equalsIgnoreCase("PRIMERA VIVIENDA")) {
            //System.out.println("CRÉDITO APROBADO: PRIMERA VIVIENDA");
            //-------------------------------------------------------------------------//
        } else if (plazo <= 20 && 0.04 <= intanu && intanu <= 0.06 && montop <= usuario.getValorpropiedad() * 0.7 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getCertificadoAvaluo() != null  && solicitud.getCertificadoAvaluo().length > 0 &&
                solicitud.getEscrituraPrimeraVivienda() != null && solicitud.getEscrituraPrimeraVivienda().length > 0 &&
                solicitud.getHistorialCrediticio() != null  && solicitud.getHistorialCrediticio().length > 0 && usuario.getHouses() > 0 && usuario.getObjective().equalsIgnoreCase("SEGUNDA VIVIENDA")) {
            //System.out.println("CRÉDITO APROBADO: SEGUNDA VIVIENDA");
            //-------------------------------------------------------------------------//
        } else if (plazo <= 25 && 0.05 <= intanu && intanu <= 0.07 && montop <= usuario.getValorpropiedad() * 0.6 &&
                solicitud.getEstadosFinancieros() != null && solicitud.getEstadosFinancieros().length > 0 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 &&
                solicitud.getPlanNegocios() != null && solicitud.getPlanNegocios().length > 0 && usuario.getObjective().equalsIgnoreCase("PROPIEDAD COMERCIAL")) {
            //System.out.println("CRÉDITO APROBADO: PROPIEDADES COMERCIALES");
            //-------------------------------------------------------------------------//
        } else if (plazo <= 15 && 0.045 <= intanu && intanu <= 0.06 && montop <= usuario.getValorpropiedad() * 0.5 &&
                solicitud.getComprobanteIngresos() != null && solicitud.getComprobanteIngresos().length > 0 &&
                solicitud.getPresupuestoRemodelacion() != null && solicitud.getPresupuestoRemodelacion().length > 0 &&
                solicitud.getCertificadoAvaluo() != null && solicitud.getCertificadoAvaluo().length > 0 && usuario.getObjective().equalsIgnoreCase("REMODELACION")) {
            //System.out.println("CRÉDITO APROBADO: REMODELACIÓN");
        } else {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            //System.out.println("CRÉDITO RECHAZADO: NO CUMPLE CON LOS REQUISITOS, PORFAVOR REVIZAR LOS VALORES INGRESADOS Y LOS ARCHIVOS");
            solicitud.setState("RECHAZADA");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "CREDITO RECHAZADO: NO CUMPLE CON LOS REQUISITOS, PORFAVOR REVIZAR LOS VALORES INGRESADOS Y LOS ARCHIVOS", String.class);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO

            HttpEntity<Credito> request = new HttpEntity<>(solicitud);
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

            return 0;
        }
        // [R6]--------------------------------------------------------------------//
        // RECHAZO DE LA SOLICITUD SI EL SOLICITANTE ESTÁ MUY CERCANO A LA EDAD MÁXIMA PERMITIDA
        if (usuario.getAge() + plazo > 75 || (usuario.getAge() + plazo >= 70 && usuario.getAge() + plazo <= 75)) {
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "CREDITO RECHAZADO: EL SOLICITANTE ESTÁ MUY CERCANO A LA EDAD MÁXIMA PERMITIDA", String.class);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO

            HttpEntity<Credito> request = new HttpEntity<>(solicitud);
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

            return 0;
        } else {
            //System.out.println("EDAD DENTRO DE RANGO ACEPTABLE");
        }
        // [R7]--------------------------------------------------------------------//
        int errores = 5; // CANTIDAD DE ERRORES PERMITIDOS PARA OTORGAR UN ESTADO A LA SOLICITUD
        // [R7]-----[R71]---------------------------------------------------------//--------------------------------------------------------------------//
        // + VERIFICO SI EL SALDO POSITIVO MÁS PEQUEÑO MAYOR O IGUAL AL 10% DEL MONTO DEL PRÉSTAMO:
        // OBTENGO LOS AHORROS DEL USUARIO
        List<Ahorro> ahorros = restTemplate.getForObject("http://ahorro-service/ahorro/byusuario/" + userId, List.class);
        // OBTENGO EL VALOR POSITIVO MÁS PEQUEÑO
        int valorPositivoMasPequeno = restTemplate.getForObject("http://ahorro-service/ahorro/valorpositivomaspequeno/" + userId, Integer.class);


        if(valorPositivoMasPequeno >= Math.round(solicitud.getMontop() * 0.1)){
            //System.out.println("SALDO POSITIVO MÁS PEQUEÑO MAYOR O IGUAL AL 10% DEL MONTO DEL PRÉSTAMO");
        }else{
            //-------------------------------------------------------------------------//
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ERROR-1: SALDO POSITIVO MÁS PEQUEÑO MENOR AL 10% DEL MONTO DEL PRÉSTAMO", String.class);
            //-------------------------------------------------------------------------//
            errores--; // CHEQUEO NEGATIVO
        }
        // [R7]-----[R72]---------------------------------------------------------//--------------------------------------------------------------------//
        // HISTORIAL DE AHORRO CONSISTENTE
        // VER QUE EL CLIENTE TENGA UN SALDO POSITIVO EN SU CUENTA DE AHORROS POR LO MENOS DURANTE LOS ÚLTIMOS 12 MESES, SIN REALIZAR RETIROS SIGNIFICATIVOS (> 50% DEL SALDO).
        int bandera = 0;            // VERIFICA SI HAY ALGÚN RETIRO MAYOR A 50% DEL SALDO
        double saldo = 0;          // SALDO DE LA CUENTA DE AHORROS -> SUMA DE TODAS LAS TRANSACCIONES
        double acumulado = 0; // SALDO ACUMUADO DE LA CUENTA DE AHORROS
        double cantidad = 0;    // CANTIDAD DE MESES DE AHORRO / CADA TRANSACCIÓN ES UN MES
        double analisis = 0;      // REPRESENTA LOS ULTIMOS 12 MESES
        // ------------------------------------------------------------------------//
        // OBTENCIÓN DE LA CANTIDAD DE MESES DE AHORRO DEL USUARIO
        for (Ahorro ahorro : ahorros) {
            cantidad = cantidad + 1;
        }
        //System.out.println("CANTIDAD DE MESES DE AHORRO: " + cantidad);
        analisis = cantidad; // REPRESENTA LOS ULTIMOS 12 MESES
        // ------------------------------------------------------------------------//
        // OBTENCIÓN DE LOS AHORROS DEL USUARIO TOTALES EN LOS ULTIMOS 12 MESES
        for (Ahorro ahorro : ahorros) {
            acumulado = acumulado + ahorro.getTransaccion();
            if (ahorro.getTipo().equalsIgnoreCase("RETIRO") && Math.abs(ahorro.getTransaccion()) > saldo * 0.5 && analisis <= 12) {
                bandera = 1; // SI ES 1, HAY UN RETIRO MAYOR A 50% DEL SALDO DENTRO DE LOS ULTIMOS 12 MESES
            }
            saldo = ahorro.getTransaccion() + saldo;
            analisis = analisis - 1;
        }
        // ------------------------------------------------------------------------//
        if (cantidad < 12) {   // SI NO HAY 12 MESES DE AHORRO MARCO MAL EL ANALISIS
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ERROR-2: MENOS DE 12 MESES DE AHORRO", String.class);
            //System.out.println("ERROR-2: MENOS DE 12 MESES DE AHORRO");
            //-------------------------------------------------------------------------//
            errores--;// CHEQUEO NEGATIVO
        }else if (saldo < 0) { // SI EL SALDO ES NEGATIVO MARCO MAL EL ANALISIS
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ERROR-2: SALDO TOTAL ES NEGATIVO", String.class);
            //-------------------------------------------------------------------------//
            errores--;// CHEQUEO NEGATIVO
        }else if (bandera == 1) { // SI HAY UN RETIRO MAYOR A 50% DEL SALDO DENTRO DE LOS ULTIMOS 12 MESES
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ERROR-2: RETIRO MAYOR A 50% DEL SALDO", String.class);
            //-------------------------------------------------------------------------//
            errores--;// CHEQUEO NEGATIVO
        }else{
            //System.out.println("HISTORIAL DE AHORRO CONSISTENTE");
        }
        // [R7]-----[R73]---------------------------------------------------------//--------------------------------------------------------------------//
        // DEPOSITOS PERIODICOS CON FRECUENCIA MENSUAL O TRIMESTRAL:
        bandera = 0;            // REUTILIZACIÓN, AHORA SI ES 1, ES PORQUE LOS DEPOSITOS NO RESPETAN EL SER MENSUALES O TRIMESTRALES
        int mensual = 0;      // VERIFICADOR DE DEPOSITOS MENSUALES
        int trimestral = 0;    // VERIFICADOR DE DEPOSITOS TRIMESTRALES
        int sumdepos = 0;   // SUMA DE DEPOSITOS TOTALES EN EL HISTORIAL
        analisis = cantidad; // REPRESENTA LOS ULTIMOS 12 MESES
        // ------------------------------------------------------------------------//
        // OBTENCIÓN DE LOS DEPOSITOS MENSUALES Y TRIMESTRALES DEL USUARIO SIEMPRE Y CUANDO LA CUENTA DE AHORRO POSEA 12 O MAS MESES DE HISTORIAL
        for (Ahorro ahorro : ahorros) {
            if (ahorro.getTipo().equalsIgnoreCase("DEPOSITO") && analisis <= 12 && cantidad >= 12) {
                sumdepos = sumdepos + ahorro.getTransaccion();
                mensual = mensual + 1;
                if (mensual % 3 == 0) {
                    trimestral = trimestral + 1;
                }
            }
            analisis = analisis - 1;
        }
        // ------------------------------------------------------------------------//
        // SI NO HAY DEPOSITOS MENSUALES O TRIMESTRALES MARCO MAL EL ANALISIS DENTRO DE LOS ULTIMOS 12 MESES
        if (mensual < 12 && trimestral < 4) {
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ERROR-3: HAY MENOS DE 12 DEPOSITOS MENSUALES O MENOS DE 4 DEPOSITOS TRIMESTRALES", String.class);
            //-------------------------------------------------------------------------//
            bandera = 1; // SI ES 1, NO NECESITO SEGUIR RESTANDO ERRORES, PARA LA COMPARACIÓN DIGUIENTE
        }
        // MONTO MINIMO: LOS DEPOSITOS DEBEN SUMAR AL MENOS EL 5% DE LOS INGRESOS MENSUALES
        if (sumdepos < usuario.getIngresos() * 0.05) {
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ERROR-3: LOS DEPOSITOS NO SUMAN AL MENOS EL 5% DE LOS INGRESOS MENSUALES", String.class);

            bandera = 1; // SI ES 1, NO NECESITO SEGUIR RESTANDO ERRORES, PARA LA COMPARACIÓN DIGUIENTE
        }
        // ------------------------------------------------------------------------//
        if (bandera > 0){
            errores--; // CHEQUEO NEGATIVO
        }else{
            //System.out.println("DEPOSITOS MENSUALES Y TRIMESTRALES ACEPTADOS Y SUMAN AL MENOS EL 5% DE LOS INGRESOS MENSUALES");
        }
        // [R7]-----[R74]---------------------------------------------------------//--------------------------------------------------------------------//
        // RELACIÓN ENTRE AÑOS DE ANTIUGEDAD Y SALDO ACUMULADO RESPECTO AL MONTO DEL PRÉSTAMO
        if (cantidad < 24 && acumulado >= solicitud.getMontop()*0.2 ){
            //System.out.println("CUENTA DE AHORROS MENOR A 2 AÑOS Y CON UN SALDO ACUMULADO AL MENOS DE 20% DEL PRESTAMO");
        }else if(cantidad >= 24 && acumulado >= solicitud.getMontop()*0.1){
            //System.out.println("CUENTA DE AHORROS MAYOR O IGUAL A 2 AÑOS Y CON UN SALDO ACUMULADO AL MENOS DE 10% DEL PRESTAMO");
        }else{
            //-------------------------------------------------------------------------//  -------------------------------------------------------------------------------------------------> REVIZAR |||||||||||||||||||||||||||||||||||||||||
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ERROR-4: CUENTA DE AHORROS NO CUMPLE CON RELACIÓN ENTRE AÑOS DE ANTIUGEDAD Y SALDO ACUMULADO RESPECTO AL MONTO DEL PRÉSTAMO", String.class);
            //-------------------------------------------------------------------------//
            //System.out.println("ERROR-4: CUENTA DE AHORROS NO CUMPLE CON RELACIÓN ENTRE AÑOS DE ANTIUGEDAD Y SALDO ACUMULADO RESPECTO AL MONTO DEL PRÉSTAMO");
            errores--; // CHEQUEO NEGATIVO
        }
        // [R7]-----[R75]---------------------------------------------------------//--------------------------------------------------------------------//
        // RECHAZO DE LA SOLICITUD SI HAY RETIROS MAYORES AL 30% DEL SALDO EN LOS ULTIMOS 6 MESES
        bandera = 0;  // REUTILIZACIÓN, AHORA SI ES 1 O MAYOR, ES PORQUE HAY UN RETIRO MAYOR AL 30% DEL SALDO EN LOS ULTIMOS 6 MESES
        saldo = 0;       // REUTILIZACIÓN, SALDO DE LA CUENTA DE AHORROS DE LOS ULTIMOS 6 MESES
        analisis = cantidad; // REPRESENTA LOS ULTIMOS 12 MESES
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        for(Ahorro ahorro : ahorros){
            if(analisis <= 6){
                saldo = saldo + ahorro.getTransaccion();
            }
            analisis = analisis - 1;
        }

        for (Ahorro ahorro : ahorros) {
            if(ahorro.getTipo().equalsIgnoreCase("RETIRO") && Math.abs(ahorro.getTransaccion()) > saldo*0.3){
                //System.out.println("tipo:" + ahorro.getTipo() + " transaccion:" + ahorro.getTransaccion() + " saldo:" + saldo);
                bandera = 1;
            }
        }
        // ------------------------------------------------------------------------//
        if (bandera == 1) {
            //System.out.println("RETIROS MAYORES AL 30% DEL SALDO EN LOS ULTIMOS 6 MESES");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ERROR-5: RETIROS MAYORES AL 30% DEL SALDO EN LOS ULTIMOS 6 MESES", String.class);
            errores--; // CHEQUEO NEGATIVO
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // [R7]-----[CHEQUEO DE EVALUACIONES]----------------------------//--------------------------------------------------------------------//
        // CONCLUCIÓN DE ANALISIS DE HISTORIAL DE AHORROS:
        //System.out.println("FINALIZACIÓN DE LA EVALUACIÓN DE CRÉDITO");
        //System.out.println("ERRORES ENCONTRADOS: " + errores);
        if (errores == 5) {
            //System.out.println("SOLICITUD DE CRÉDITO APROBADO");
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "APROBADO"
            solicitud.setState("APROBADO");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "CRÉDITO APROBADO", String.class);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO

            HttpEntity<Credito> request = new HttpEntity<>(solicitud);
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

            return 1;

        } else if (errores >= 3 && errores < 5) {
            //System.out.println("SOLICITUD DE CRÉDITO EN REVISIÓN ADICIONAL");
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("REVISION ADICIONAL");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "SOLICITUD DE CRÉDITO EN REVISIÓN ADICIONAL", String.class);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO

            HttpEntity<Credito> request = new HttpEntity<>(solicitud);
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

            return 1;
        } else {
            //System.out.println("SOLICITUD DE CRÉDITO RECHAZADO");
            // MODIFICAR EL ESTADO DE LA SOLICITUD A "RECHAZADA"
            solicitud.setState("RECHAZADA");
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "CRÉDITO RECHAZADO: EL SOLICITANTE NO CUMPLE CON LOS REQUISITOS", String.class);
            //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
            // ACTUALIZACIÓN DE solicitud EN EL ESPACIO DE solicitud DEL USUARIO

            HttpEntity<Credito> request = new HttpEntity<>(solicitud);
            Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

            return 1;
        }
    }

    //-----------------------[EXTRA]- FUNCIONES DE ACTUALIZACIÓN DE ESTADOS-----------------//
    // + ACTUALIZAR ESTADO DE LA SOLICITUD POR ID DEL USUARIO: -> PARA EL EJECUTIVO
    public int updateState(Long userId, Long creditId, int state) {
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
            return -1;
        }

        System.out.printf("ESTADO DE LA SOLICITUD ANTES DE LA ACTUALIZACIÓN: %s\n", solicitud.getState());

        //-------------------------------------------------------------------------//
        if (state ==1) {
            solicitud.setState("EN REVISION INICIAL");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN REVISIÓN INICIAL", String.class);

        }else if (state ==2) {
            solicitud.setState("PENDIENTE DE DOCUMENTACION");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: PENDIENTE DE DOCUMENTACIÓN", String.class);

        }else if (state ==3) {
            solicitud.setState("EN EVALUACION");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN EVALUACIÓN", String.class);

        }else if (state ==4) {
            solicitud.setState("PRE-APROBADA");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: PRE-APROBADA", String.class);

        }else if (state ==5) {
            solicitud.setState("EN APROBACION FINAL");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN APROBACIÓN FINAL", String.class);

        }else if (state ==6) {
            solicitud.setState("APROBADA");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: APROBADA", String.class);

        }else if (state ==7) {
            solicitud.setState("RECHAZADA");
            String notificationUrl = "http://usuario-service0/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: RECHAZADA", String.class);

        }else if (state ==8) {
            solicitud.setState("CANCELADA POR EL CLIENTE");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: CANCELADA POR EL CLIENTE", String.class);

        }else if (state ==9) {
            solicitud.setState("EN DESEMBOLOSO");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: EN DESEMBOLOSO", String.class);

        } else if (state == 10) {
            solicitud.setState("PENDIENTE");
            String notificationUrl = "http://usuario-service/usuario/addnotification/" + userId;
            restTemplate.postForObject(notificationUrl, "ESTADO DE SOLICITUD ACTUALIZADO: SU SOLICITUD SE ENCUENTRA EN EL ESTADO: PENDIENTE", String.class);

        }
        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//
        // Save the updated Credito object
        HttpEntity<Credito> request = new HttpEntity<>(solicitud);
        Credito savedSolicitud = restTemplate.postForObject("http://credito-service/credito/save", request, Credito.class);

        //-------------------------------------------------------------------------//-------------------------------------------------------------------------//

        //String updateUsuarioUrl = "http://usuario-service/usuario/updateSolicitud/" + userId;
        //restTemplate.postForObject(updateUsuarioUrl, solicitud, Usuario.class);

        return 0;
    }
}