package com.example.credito_service.service;

import com.example.credito_service.entity.Credito;
import com.example.credito_service.model.Ahorro;
import com.example.credito_service.model.Usuario;
import com.example.credito_service.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
         @Transactional(readOnly = true)
        public void deleteCredito(int id) {creditoRepository.deleteById(id);}

    public List<Credito> byUsuarioId(int usuarioId) {
        return creditoRepository.findByUsuarioId(usuarioId);
    }
    //-----------------------[P3]- FUNCIONES DE CREACIÓN  DE SOLICITUD DE CRÉDITO-------------------------//
    // + CREACIÓN DE SOLICITUD DE CRÉDITO POR VALORES INGRESADOS BAJO ID DE USUARIO INGRESADO:
    public Credito registrarCredito(double montop, int plazo, double intanu, double intmen, double segudesg, double seguince, double comiad, byte[] comprobanteIngresos, byte[] certificadoAvaluo, byte[] historialCrediticio, byte[] escrituraPrimeraVivienda, byte[] planNegocios, byte[] estadosFinancieros, byte[] presupuestoRemodelacion, byte[] dicom, int usuarioId) {
        Credito credito = new Credito();
        credito.setMontop(montop);
        credito.setPlazo(plazo);
        credito.setIntanu(intanu);
        credito.setIntmen(intmen);
        credito.setSegudesg(segudesg);
        credito.setSeguince(seguince);
        credito.setComiad(comiad);
        credito.setComprobanteIngresos(comprobanteIngresos);
        credito.setCertificadoAvaluo(certificadoAvaluo);
        credito.setHistorialCrediticio(historialCrediticio);
        credito.setEscrituraPrimeraVivienda(escrituraPrimeraVivienda);
        credito.setPlanNegocios(planNegocios);
        credito.setEstadosFinancieros(estadosFinancieros);
        credito.setPresupuestoRemodelacion(presupuestoRemodelacion);
        credito.setDicom(dicom);
        credito.setState("PENDIENTE");
        credito.setUsuarioId(usuarioId);
        return creditoRepository.save(credito);
    }
}
