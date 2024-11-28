package com.example.simula_service.controller;

import com.example.simula_service.entity.Simula;
import com.example.simula_service.service.SimulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/simula")

public class SimulaController {
    @Autowired
    SimulaService simulaService;

    @GetMapping("/all")
    public ResponseEntity<ArrayList<Simula>> getAllSimulacion() {
        ArrayList<Simula> simulaciones = simulaService.getAllSimulacion();
        return ResponseEntity.ok(simulaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Simula> getSimulacionById(@PathVariable int id) {
        Simula simulacion = simulaService.getSimulacionById(id);
        return ResponseEntity.ok(simulacion);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveSimulacion(@RequestBody Simula simula) {
        simulaService.saveSimulacion(simula);
        return ResponseEntity.ok().build();
    }
    //----------------[P1]- FUNCIONES DE CALCULO DE CRÉDITO HIPOTECARIO-----------------//
    // + SIMULACIÓN DE CRÉDITO HIPOTECARIO POR VALORES INGRESADOS:
    @GetMapping("/simular")
    public ResponseEntity<Double> simularCredito(
            @RequestParam double P,
            @RequestParam double r,
            @RequestParam double n,
            @RequestParam double V,
            @RequestParam int tipo) {
        double resultado = simulaService.Credito_Hipotecario(P, r, n, V, tipo);
        return ResponseEntity.ok(resultado);
    }
}