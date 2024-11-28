package com.example.total_service.controller;

import com.example.total_service.entity.Total;
import com.example.total_service.service.TotalService;

import com.example.total_service.service.TotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/total")

public class TotalController {

    @Autowired
    TotalService totalService;

    @GetMapping("/all")
    public ResponseEntity<List<Total>> getAll() {
        List<Total> totals = totalService.getAll();
        return ResponseEntity.ok(totals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Total> getTotalById(@PathVariable int id) {
        Total total = totalService.getTotalById(id);
        if (total == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(total);
    }

    @PostMapping("/save")
    public ResponseEntity<Total> saveTotal(@RequestBody Total total) {
        Total savedTotal = totalService.saveTotal(total);
        return ResponseEntity.ok(savedTotal);
    }

    @GetMapping("/byUsuarioId/{usuarioId}")
    public ResponseEntity<List<Total>> byUsuarioId(@PathVariable int usuarioId) {
        List<Total> totals = totalService.byUsuarioId(usuarioId);
        return ResponseEntity.ok(totals);
    }

    @GetMapping("/calcularCostosTotales")
    public ResponseEntity<List<Double>> calcularCostosTotales(@RequestParam Long userId, @RequestParam Long creditId) {
        List<Double> costosTotales = totalService.calcularCostosTotales(userId, creditId);
        if (costosTotales == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(costosTotales);
    }
}