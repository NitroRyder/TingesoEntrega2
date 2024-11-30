package com.example.seguimiento_service.controller;

import com.example.seguimiento_service.model.Credito;
import com.example.seguimiento_service.service.SeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguimiento")
public class SeguimientoController {
    @Autowired
    SeguimientoService seguimientoService;

    @GetMapping("/followCredito")
    public ResponseEntity<List<Credito>> followCredito(@RequestParam Long userId) {
        try {
            List<Credito> solicitudes = seguimientoService.followCredito(userId);
            return ResponseEntity.ok(solicitudes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
