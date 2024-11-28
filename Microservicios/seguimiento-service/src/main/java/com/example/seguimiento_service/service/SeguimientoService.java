package com.example.seguimiento_service.service;

import com.example.seguimiento_service.entity.Seguimiento;
import com.example.seguimiento_service.model.Credito;
import com.example.seguimiento_service.model.Usuario;
import com.example.seguimiento_service.repository.SeguimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.ArrayList;

@Service
public class SeguimientoService {

    @Autowired
    SeguimientoRepository seguimientoRepository;

    RestTemplate restTemplate;

    public List<Seguimiento> getAll() {return seguimientoRepository.findAll();}

    public Seguimiento getSeguimientoById(int id) {return seguimientoRepository.findById(id).orElse(null);}

    public Seguimiento saveSeguimiento(Seguimiento seguimiento) {
        Seguimiento seguimientoNew = seguimientoRepository.save(seguimiento);
        return seguimientoNew;
    }

    public void deleteSeguimiento(int id) {seguimientoRepository.deleteById(id);}

    public List<Seguimiento> byUsuarioId(int usuarioId) {return seguimientoRepository.findByIdusuario(usuarioId);}

    //-----------------------[P5]- FUNCIONES DE SEGUIMENTO ---------------------------------------//
    // + SEQUIMIENTO DEL ESTADO DE LA SOLICITUD POR ID DEL USUARIO:
    public Credito followCredito(Long userId, Long creditId) {
        // Fetch the Usuario object using RestTemplate
        Usuario usuario = restTemplate.getForObject("http://localhost:8030/usuario/" + userId, Usuario.class); // OBTENCIÓN DE USUARIO COMPLETO POR ID
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO");
        }
        // Fetch the Credito object using RestTemplate
        Credito solicitud = restTemplate.getForObject("http://localhost:8020/credito/" + creditId, Credito.class); // OBTENGO LA SOLICITUD DEL USUARIO -> PARA EL RETORNO DE ARCHIVOS
        if (solicitud == null) {
            throw new IllegalArgumentException("ERROR: SOLICITUD NO ENCONTRADA");
        }
        //-------------------------------------------------------------------------//
        return solicitud;
    }
}