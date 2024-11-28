package com.example.evalua_service.controller;


import com.example.evalua_service.entity.Evalua;
import com.example.evalua_service.service.EvaluaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/evalua")
public class EvaluaController {
    @Autowired
    EvaluaService evaluaService;

    @GetMapping("/all")
    public ResponseEntity<List<Evalua>> getAll() {
        List<Evalua> evaluaciones = evaluaService.getAll();
        return ResponseEntity.ok(evaluaciones);
    }
    /*
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Evalua>> getEvaluaciones(@PathVariable int usuarioId) {
        List<Evalua> evaluaciones = evaluaService.getEvaluaciones(usuarioId);
        return ResponseEntity.ok(evaluaciones);
    }

    @GetMapping("/credito/{creditoId}")
    public ResponseEntity<List<Evalua>> getEvaluacionesCredito(@PathVariable int creditoId) {
        List<Evalua> evaluaciones = evaluaService.getEvaluacionesCredito(creditoId);
        return ResponseEntity.ok(evaluaciones);
    }

    @GetMapping("/pendientes")
    public ResponseEntity<List<Evalua>> getEvaluacionesPendientes() {
        List<Evalua> evaluaciones = evaluaService.getEvaluacionesPendientes();
        return ResponseEntity.ok(evaluaciones);
    }

    @GetMapping("/aceptadas")
    public ResponseEntity<List<Evalua>> getEvaluacionesAceptadas() {
        List<Evalua> evaluaciones = evaluaService.getEvaluacionesAceptadas();
        return ResponseEntity.ok(evaluaciones);
    }
     */
    @PostMapping("/evaluateCredito")
    public ResponseEntity<Map<String, Object>> evaluateCredito(@RequestParam Long userId, @RequestParam Long creditId) {
        Map<String, Object> response = evaluaService.evaluateCredito(userId, creditId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateState")
    public ResponseEntity<String> updateState(@RequestParam Long userId, @RequestParam Long creditId, @RequestParam int state) {
        int result = evaluaService.updateState(userId, creditId, state);
        if (result == 1) {
            return ResponseEntity.ok("Evaluación actualizada");
        } else {
            return ResponseEntity.ok("Error al actualizar la evaluación");
        }
    }
}