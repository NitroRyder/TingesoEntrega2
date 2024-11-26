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
    public void addNotification(int userId, String notification) {
        Usuario usuario = usuarioRepository.findById(userId).orElse(null);
        if (usuario != null) {
            usuario.getNotifications().add(notification);
            usuarioRepository.save(usuario);
        }
    }
    //-----------------------[P2]- FUNCIONES DE REGISTRO DE USUARIO-------------------------//
    // + REGISTRO DE USUARIO POR VALORES INGRESADOS:
    public Usuario registerUsuario(String rut, String name, int age, int workage, int houses, int valorpropiedad, int ingresos, int sumadeuda, String objective, String independiente, List<Ahorro> ahorros, List<Credito> creditos) {
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

        for (Ahorro ahorro : ahorros) {
            ahorro.setUsuarioId(userSalvado.getId());
            HttpEntity<Ahorro> request = new HttpEntity<Ahorro>(ahorro);
            restTemplate.postForObject("http://localhost:8002/ahorro", request, Ahorro.class);
        }

        for (Credito credito : creditos) {
            credito.setUsuarioId(userSalvado.getId());
            HttpEntity<Credito> request = new HttpEntity<Credito>(credito);
            restTemplate.postForObject("http://localhost:8003/credito", request, Credito.class);
        }
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