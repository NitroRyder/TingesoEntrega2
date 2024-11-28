package com.example.calculo_total_service.controller;

import com.example.calculo_total_service.entity.CalculoTotal;
import com.example.calculo_total_service.service.CalculoTotalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/calculo")

public class CalculoTotalController {

    @Autowired
    CalculoTotalService calculoTotalService;

    @GetMapping
    public ResponseEntity<List<CalculoTotal>> getAll() {
        List<CalculoTotal> calculoTotals = calculoTotalService.getAll();
        if(calculoTotals.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(calculoTotals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalculoTotal> getById(@PathVariable("id") int id) {
        CalculoTotal calculoTotal = calculoTotalService.getCalculoTotalById(id);
        if(calculoTotal == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(calculoTotal);
    }

    @PostMapping("/save")
    public ResponseEntity<CalculoTotal> saveCalculoTotal(@RequestBody CalculoTotal calculoTotal) {
        CalculoTotal calculoTotalNew = calculoTotalService.saveCalculoTotal(calculoTotal);
        return ResponseEntity.ok(calculoTotalNew);
    }

    @GetMapping("/byusuario/{usuarioId}")
    public ResponseEntity<List<CalculoTotal>> getByUsuarioId(@PathVariable("usuarioId") int usuarioId) {
        List<CalculoTotal> calculoTotals = calculoTotalService.byUsuarioId(usuarioId);
        return ResponseEntity.ok(calculoTotals);
    }
    @GetMapping("/calcularCostosTotales")
    public ResponseEntity<List<Double>> calcularCostosTotales(@RequestParam Long userId, @RequestParam Long creditId) {
        List<Double> costosTotales = calculoTotalService.calcularCostosTotales(userId, creditId);
        if(costosTotales == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(costosTotales);
    }
}