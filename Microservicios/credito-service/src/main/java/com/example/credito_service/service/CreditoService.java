package com.example.credito_service.service;

import com.example.credito_service.entity.Credito;
import com.example.credito_service.model.Documentos;
import com.example.credito_service.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CreditoService {

        @Autowired
        CreditoRepository creditoRepository;

        @Autowired
        RestTemplate restTemplate;

        public List<Credito> getAll() {return creditoRepository.findAll();}

        public Credito getCreditoById(int id) {return creditoRepository.findById(id).orElse(null);}

        public Credito saveCredito(Credito credito) {
            Credito creditoNew = creditoRepository.save(credito);
            return creditoNew;
        }
        public void deleteCredito(int id) {creditoRepository.deleteById(id);}

    public List<Documentos> getDocumentoByCreditoId(int creditoId) {
        List<Documentos> documentos = restTemplate.getForObject("http://documentos-service/documentos/bycredito/" + creditoId, List.class);
        return documentos;
    }

    public List<Credito> byUsuarioId(int usuarioId) {
        return creditoRepository.findByUsuarioId(usuarioId);
    }
    //-----------------------[P3]- FUNCIONES DE CREACIÓN  DE SOLICITUD DE CRÉDITO-------------------------//
    // + CREACIÓN DE SOLICITUD DE CRÉDITO POR VALORES INGRESADOS BAJO ID DE USUARIO INGRESADO:
    public Credito registrarCredito(double montop, int plazo, double intanu, double intmen, double segudesg, double seguince, double comiad, int usuarioId) {
        Credito credito = new Credito();
        credito.setMontop(montop);
        credito.setPlazo(plazo);
        credito.setIntanu(intanu);
        credito.setIntmen(intmen);
        credito.setSegudesg(segudesg);
        credito.setSeguince(seguince);
        credito.setComiad(comiad);
        credito.setState("PENDIENTE");
        credito.setUsuarioId(usuarioId);
        return creditoRepository.save(credito);
    }
}
