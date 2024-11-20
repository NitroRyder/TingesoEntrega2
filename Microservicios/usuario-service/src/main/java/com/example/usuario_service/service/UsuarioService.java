package com.example.usuario_service.service;


import com.example.usuario_service.entity.Usuario;
import com.example.usuario_service.model.Ahorro;
import com.example.usuario_service.model.Credito;
import com.example.usuario_service.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<Usuario> getAll() {return usuarioRepository.findAll();}

    public Usuario getUsuarioById(int id) {return usuarioRepository.findById(id).orElse(null);}

    public Usuario save(Usuario usuario) {
        Usuario usuarioNew = usuarioRepository.save(usuario);
        return usuarioNew;
    }

    public List<Ahorro> getAhorros(int usuarioId) {
        List<Ahorro> ahorros = restTemplate.getForObject("http://localhost:8002/ahorro/byusuario/" + usuarioId, List.class);
        return ahorros;
    }

    public List<Credito> getCreditos(int usuarioId) {
        List<Credito> creditos = restTemplate.getForObject("http://localhost:8003/credito/byusuario/" + usuarioId, List.class);
        return creditos;
    }

    public Ahorro saveAhorro(int usuarioId, Ahorro ahorro) {
        ahorro.setUsuarioId(usuarioId);
        HttpEntity<Ahorro> request = new HttpEntity<Ahorro>(ahorro);
        Ahorro ahorroNew = restTemplate.postForObject("http://localhost:8002/ahorro", request, Ahorro.class);
        return ahorroNew;
    }

    public Credito saveCredito(int usuarioId, Credito credito) {
        credito.setUsuarioId(usuarioId);
        HttpEntity<Credito> request = new HttpEntity<Credito>(credito);
        Credito creditoNew = restTemplate.postForObject("http://localhost:8003/credito", request, Credito.class);
        return creditoNew;
    }

}