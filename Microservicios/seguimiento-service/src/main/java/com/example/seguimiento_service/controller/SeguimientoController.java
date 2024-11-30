package com.example.seguimiento_service.controller;

import com.example.seguimiento_service.model.Credito;
import com.example.seguimiento_service.service.SeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seguimiento")
public class SeguimientoController {
    @Autowired
    SeguimientoService seguimientoService;

    @GetMapping("/followCredito")
    public ResponseEntity<Credito> followCredito(@RequestParam Long userId, @RequestParam Long creditId) {
        Credito solicitud = seguimientoService.followCredito(userId, creditId);
        return ResponseEntity.ok(solicitud);
    }
}
