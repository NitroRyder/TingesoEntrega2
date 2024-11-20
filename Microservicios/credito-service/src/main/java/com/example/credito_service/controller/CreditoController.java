package com.example.credito_service.controller;

import com.example.credito_service.entity.Credito;
import com.example.credito_service.service.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credito")

public class CreditoController {

    @Autowired
    CreditoService creditoService;

    @GetMapping
    public ResponseEntity<List<Credito>> getAll() {
        List<Credito> creditos = creditoService.getAll();
        if(creditos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(creditos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credito> getById(@PathVariable("id") int id) {
        Credito credito = creditoService.getCreditoById(id);
        if(credito == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(credito);
    }

    @PostMapping()
    public ResponseEntity<Credito> save(@RequestBody Credito credito) {
        Credito creditoNew = creditoService.save(credito);
        return ResponseEntity.ok(creditoNew);
    }

    @GetMapping("/byusuario/{usuarioId}")
    public ResponseEntity<List<Credito>> getByUsuarioId(@PathVariable("usuarioId") int usuarioId) {
        List<Credito> creditos = creditoService.byUsuarioId(usuarioId);
        return ResponseEntity.ok(creditos);
    }

}