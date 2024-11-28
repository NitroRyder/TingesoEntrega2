package com.example.total_service.controller;

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

    //-----------------------[P6]- FUNCIONES DE CALCULO DE COSTOS TOTALES-------------------//
    // + CALCULO DE COSTOS TOTALES DE LA SOLICITUD DE CRÉDITO POR ID DEL USUARIO:
    @GetMapping("/calcularCostosTotales")
    public ResponseEntity<List<Double>> calcularCostosTotales(@RequestParam Long userId, @RequestParam Long creditId) {
        List<Double> costosTotales = totalService.calcularCostosTotales(userId, creditId);
        if (costosTotales == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(costosTotales);
    }
}