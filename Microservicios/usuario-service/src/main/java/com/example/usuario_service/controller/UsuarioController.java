package com.example.usuario_service.controller;

import com.example.usuario_service.entity.Usuario;
import com.example.usuario_service.model.Ahorro;
import com.example.usuario_service.model.Credito;
import com.example.usuario_service.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable("id") int id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(usuario);
    }

    @PostMapping()
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        Usuario usuarioNew = usuarioService.save(usuario);
        return ResponseEntity.ok(usuarioNew);
    }

    @GetMapping("/ahorros/{usuarioId}")
    public ResponseEntity<List<Ahorro>> getAhorros(@PathVariable("usuarioId") int usuarioId) {
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        List<Ahorro> ahorros = usuarioService.getAhorros(usuarioId);
        return ResponseEntity.ok(ahorros);
    }

    @GetMapping("/creditos/{usuarioId}")
    public ResponseEntity<List<Credito>> getCreditos(@PathVariable("usuarioId") int usuarioId) {
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        List<Credito> creditos = usuarioService.getCreditos(usuarioId);
        return ResponseEntity.ok(creditos);
    }

    @PostMapping("/saveahorro/{usuarioId}")
    public ResponseEntity<Ahorro> saveAhorro(@PathVariable("usuarioId") int usuarioId, @RequestBody Ahorro ahorro) {
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        Ahorro ahorroNew = usuarioService.saveAhorro(usuarioId, ahorro);
        return ResponseEntity.ok(ahorroNew);
    }

    @PostMapping("/savecredito/{usuarioId}")
    public ResponseEntity<Credito> saveCredito(@PathVariable("usuarioId") int usuarioId, @RequestBody Credito credito) {
        Usuario usuario = usuarioService.getUsuarioById(usuarioId);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        Credito creditoNew = usuarioService.saveCredito(usuarioId, credito);
        return ResponseEntity.ok(creditoNew);
    }

    @PostMapping("/addnotification/{userId}")
    public ResponseEntity<String> addNotification(@PathVariable("userId") int userId, @RequestBody String notification) {
        Usuario usuario = usuarioService.getUsuarioById(userId);
        if (usuario == null)
            return ResponseEntity.notFound().build();
        usuarioService.addNotification(userId, notification);
        return ResponseEntity.ok("Notification added");
    }

    @GetMapping("/notifications/{userId}")
    public ResponseEntity<List<String>> getNotifications(@PathVariable("userId") Long userId) {
        List<String> notifications = usuarioService.getNotifications(userId);
        return ResponseEntity.ok(notifications);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUsuario(@PathVariable("id") int id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok("Usuario eliminado");
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> registerUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioRegistrado = usuarioService.registerUsuario(
                usuario.getRut(),
                usuario.getName(),
                usuario.getAge(),
                usuario.getWorkage(),
                usuario.getHouses(),
                usuario.getValorpropiedad(),
                usuario.getIngresos(),
                usuario.getSumadeuda(),
                usuario.getObjective(),
                usuario.getIndependiente()
        );
        return ResponseEntity.ok(usuarioRegistrado);
    }
}