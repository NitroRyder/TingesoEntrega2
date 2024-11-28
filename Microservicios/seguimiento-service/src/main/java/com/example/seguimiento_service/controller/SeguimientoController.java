package com.example.seguimiento_service.controller;

import com.example.seguimiento_service.entity.Seguimiento;
import com.example.seguimiento_service.model.Credito;
import com.example.seguimiento_service.model.Usuario;
import com.example.seguimiento_service.service.SeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seguimiento")
public class SeguimientoController {
    @Autowired
    SeguimientoService seguimientoService;

    @GetMapping("/all")
    public ResponseEntity<List<Seguimiento>> getAll() {
        List<Seguimiento> seguimientos = seguimientoService.getAll();
        return ResponseEntity.ok(seguimientos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Seguimiento>> getSeguimientos(@PathVariable int usuarioId) {
        List<Seguimiento> seguimientos = seguimientoService.byUsuarioId(usuarioId);
        return ResponseEntity.ok(seguimientos);
    }

    @PostMapping("/create")
    public ResponseEntity<Seguimiento> createSeguimiento(@RequestBody Seguimiento seguimiento) {
        Seguimiento newSeguimiento = seguimientoService.saveSeguimiento(seguimiento);
        return ResponseEntity.ok(newSeguimiento);
    }

    @PostMapping("/update")
    public ResponseEntity<Seguimiento> updateSeguimiento(@RequestBody Seguimiento seguimiento) {
        Seguimiento updatedSeguimiento = seguimientoService.saveSeguimiento(seguimiento);
        return ResponseEntity.ok(updatedSeguimiento);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSeguimiento(@PathVariable int id) {
        seguimientoService.deleteSeguimiento(id);
        return ResponseEntity.ok("Seguimiento eliminado");
    }
    @GetMapping("/followCredito")
    public ResponseEntity<Credito> followCredito(@RequestParam Long userId, @RequestParam Long creditId) {
        Credito solicitud = seguimientoService.followCredito(userId, creditId);
        return ResponseEntity.ok(solicitud);
    }
}
