package com.example.evalua_service.controller;

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

    @PostMapping("/evaluateCredito")
    public ResponseEntity<String> evaluateCredito(@RequestParam Long userId, @RequestParam Long creditId) {
        int response = evaluaService.evaluateCredito(userId, creditId);
        if (response == 1) {
            return ResponseEntity.ok("EVALUACIÓN TERMINADA");
        } else {
            return ResponseEntity.ok("EVALUACIÓN RECHAZADA");
        }
    }

    @PostMapping("/updateState")
    public ResponseEntity<String> updateState(@RequestParam Long userId, @RequestParam Long creditId, @RequestParam int state) {
        int result = evaluaService.updateState(userId, creditId, state);
        if (result == 0) {
            return ResponseEntity.ok("Evaluación actualizada");
        } else {
            return ResponseEntity.ok("Error al actualizar la evaluación");
        }
    }
}