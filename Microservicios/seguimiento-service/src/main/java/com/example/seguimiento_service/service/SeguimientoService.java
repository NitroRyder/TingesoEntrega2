package com.example.seguimiento_service.service;

import com.example.seguimiento_service.model.Credito;
import com.example.seguimiento_service.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SeguimientoService {

    @Autowired
    private RestTemplate restTemplate;
    //----------------------[P5]- FUNCIONES DE SEGUIMENTO ---------------------------------------//
    // + SEQUIMIENTO DEL ESTADO DE LA SOLICITUD POR ID DEL USUARIO:
    public Credito followCredito(Long userId, Long creditId) {
        Usuario usuario = restTemplate.getForObject("http://usuario-service/usuario/" + userId, Usuario.class);
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO");
        }
        List<Credito> solicitudes = restTemplate.getForObject("http://credito-service/credito/byusuario/" + creditId, List.class);
        if (solicitudes == null || solicitudes.isEmpty()) {
            throw new IllegalArgumentException("ERROR: SOLICITUD NO ENCONTRADA");
        }
        return solicitudes.get(0); // Asumiendo que quieres el primer elemento de la lista
    }
}