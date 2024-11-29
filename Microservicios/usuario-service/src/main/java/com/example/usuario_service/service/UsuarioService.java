package com.example.usuario_service.service;


import com.example.usuario_service.entity.Usuario;
import com.example.usuario_service.model.Ahorro;
import com.example.usuario_service.model.Credito;
import com.example.usuario_service.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
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
        List<Ahorro> ahorros = restTemplate.getForObject("http://ahorro-service/ahorro/byusuario/" + usuarioId, List.class);
        return ahorros;
    }

    public List<Credito> getCreditos(int usuarioId) {
        String url = "http://credito-service/credito/byusuario/" + usuarioId;
        List<Credito> creditos = restTemplate.getForObject(url, List.class);
        return creditos;
    }

    public Ahorro saveAhorro(int usuarioId, Ahorro ahorro) {
        ahorro.setUsuarioId(usuarioId);
        HttpEntity<Ahorro> request = new HttpEntity<Ahorro>(ahorro);
        Ahorro ahorroNew = restTemplate.postForObject("http://ahorro-service/ahorro", request, Ahorro.class);
        return ahorroNew;
    }

    public Credito saveCredito(int usuarioId, Credito credito) {
        credito.setUsuarioId(usuarioId);
        HttpEntity<Credito> request = new HttpEntity<Credito>(credito);
        Credito creditoNew = restTemplate.postForObject("http://credito-service/credito", request, Credito.class);
        return creditoNew;
    }
    public void addNotification(int userId, String notification) {
        Usuario usuario = usuarioRepository.findById(userId).orElse(null);
        if (usuario != null) {
            usuario.getNotifications().add(notification);
            usuarioRepository.save(usuario);
        }
    }
    // delete usuario
    public void deleteUsuario(int id) {
        usuarioRepository.deleteById(id);
    }
    //-----------------------[P2]- FUNCIONES DE REGISTRO DE USUARIO-------------------------//
    // + REGISTRO DE USUARIO POR VALORES INGRESADOS:
    public Usuario registerUsuario(String rut, String name, int age, int workage, int houses, int valorpropiedad, int ingresos, int sumadeuda, String objective, String independiente) {
        Usuario usuario = new Usuario();
        usuario.setRut(rut);
        usuario.setName(name);
        usuario.setAge(age);
        usuario.setWorkage(workage);
        usuario.setHouses(houses);
        usuario.setValorpropiedad(valorpropiedad);
        usuario.setIngresos(ingresos);
        usuario.setSumadeuda(sumadeuda);
        usuario.setObjective(objective.toUpperCase());
        usuario.setIndependiente(independiente);


        Usuario userSalvado = usuarioRepository.save(usuario);
        return userSalvado;
    }
    //-----------------------[EXTRA]- FUNCIONES DE NOTIFICACIONES-------------------------------//
    // + OBTENER NOTIFICACIONES DEL USUARIO POR ID DEL USUARIO:
    public List<String> getNotifications(Long userId) {
        // Convert Long to Integer
        Integer userIdInt = userId.intValue();

        // Fetch the Usuario object using the converted Integer ID
        Usuario usuario = usuarioRepository.findById(userIdInt).orElse(null);

        if (usuario == null) {
            throw new IllegalArgumentException("ERROR: USUARIO NO ENCONTRADO");
        }

        return usuario.getNotifications();
    }
}