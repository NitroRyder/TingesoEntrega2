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
}
