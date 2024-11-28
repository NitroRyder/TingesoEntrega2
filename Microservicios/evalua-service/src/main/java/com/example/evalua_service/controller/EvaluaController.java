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
    @GetMapping("/{id}")
    public ResponseEntity<Evalua> getEvaluaById(@PathVariable int id) {
        Evalua evalua = evaluaService.getEvaluaById(id);
        return ResponseEntity.ok(evalua);
    }

    @PostMapping("/save")
    public ResponseEntity<Evalua> saveEvalua(@RequestBody Evalua evalua) {
        Evalua savedEvalua = evaluaService.saveEvalua(evalua);
        return ResponseEntity.ok(savedEvalua);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEvalua(@PathVariable int id) {
        evaluaService.deleteEvalua(id);
        return ResponseEntity.noContent().build();
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