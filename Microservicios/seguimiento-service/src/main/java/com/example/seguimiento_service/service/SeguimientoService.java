package com.example.seguimiento_service.service;

import com.example.seguimiento_service.model.Credito;
import com.example.seguimiento_service.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SeguimientoService {

    RestTemplate restTemplate;
    //----------------------[P5]- FUNCIONES DE SEGUIMENTO ---------------------------------------//
    // + SEQUIMIENTO DEL ESTADO DE LA SOLICITUD POR ID DEL USUARIO:
    public Credito followCredito(Long userId, Long creditId) {
        // Fetch the Usuario object using RestTemplate
        Usuario usuario = restTemplate.getForObject("http://usuario-service/usuario/" + userId, Usuario.class); // OBTENCIÓN DE USUARIO COMPLETO POR ID
        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO");
        }
        // Fetch the Credito object using RestTemplate
        Credito solicitud = restTemplate.getForObject("http://credito-service/credito/byusuario/" + creditId, Credito.class); // OBTENGO LA SOLICITUD DEL USUARIO -> PARA EL RETORNO DE ARCHIVOS
        if (solicitud == null) {
            throw new IllegalArgumentException("ERROR: SOLICITUD NO ENCONTRADA");
        }
        //-------------------------------------------------------------------------//
        return solicitud;
    }
}