package com.example.ahorro_service.controller;

import com.example.ahorro_service.entity.Ahorro;
import com.example.ahorro_service.service.AhorroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ahorro")
public class AhorroController {

    @Autowired
    AhorroService ahorroService;

    @GetMapping
    public ResponseEntity<List<Ahorro>> getAll() {
        List<Ahorro> ahorros = ahorroService.getAll();
        if (ahorros.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(ahorros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ahorro> getById(@PathVariable("id") int id) {
        Ahorro ahorro = ahorroService.getAhorroById(id);
        if (ahorro == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ahorro);
    }

    @PostMapping
    public ResponseEntity<Ahorro> save(@RequestBody Ahorro ahorro) {
        Ahorro ahorroNew = ahorroService.save(ahorro);
        return ResponseEntity.ok(ahorroNew);
    }

    @GetMapping("/byusuario/{usuarioId}")
    public ResponseEntity<List<Ahorro>> getByUsuarioId(@PathVariable("usuarioId") int usuarioId) {
        List<Ahorro> ahorros = ahorroService.byUsuarioId(usuarioId);
        return ResponseEntity.ok(ahorros);
    }

    @GetMapping("/valorpositivomaspequeno/{usuarioId}")
    public ResponseEntity<Integer> getValorPositivoMasPequeno(@PathVariable("usuarioId") int usuarioId) {
        List<Ahorro> ahorros = ahorroService.byUsuarioId(usuarioId);
        int valorPositivoMasPequeno = ahorroService.obtenerValorPositivoMasPequeno(ahorros);
        return ResponseEntity.ok(valorPositivoMasPequeno);
    }
}
