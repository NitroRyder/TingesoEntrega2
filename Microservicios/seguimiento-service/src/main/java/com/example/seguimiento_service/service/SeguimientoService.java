package com.example.seguimiento_service.service;

import com.example.seguimiento_service.model.Credito;
import com.example.seguimiento_service.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SeguimientoService {

    private final RestTemplate restTemplate;

    public SeguimientoService() {
        this.restTemplate = new RestTemplate();
    }
    //----------------------[P5]- FUNCIONES DE SEGUIMENTO ---------------------------------------//
    // + SEQUIMIENTO DEL ESTADO DE LA SOLICITUD POR ID DEL USUARIO:
    public Credito followCredito(Long userId, Long creditId) {
        Usuario usuario = restTemplate.getForObject("http://usuario-service/usuario/" + userId, Usuario.class);
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO");
        }
        Credito solicitud = restTemplate.getForObject("http://credito-service/credito/byusuario/" + creditId, Credito.class);
        if (solicitud == null) {
            throw new IllegalArgumentException("ERROR: SOLICITUD NO ENCONTRADA");
        }
        return solicitud;
    }
}