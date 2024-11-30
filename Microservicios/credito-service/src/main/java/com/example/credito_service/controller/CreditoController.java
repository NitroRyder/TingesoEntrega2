package com.example.credito_service.controller;

import com.example.credito_service.entity.Credito;
import com.example.credito_service.service.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/credito")

public class CreditoController {

    @Autowired
    CreditoService creditoService;

    @GetMapping
    public ResponseEntity<List<Credito>> getAll() {
        List<Credito> creditos = creditoService.getAll();
        if(creditos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(creditos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credito> getById(@PathVariable("id") int id) {
        Credito credito = creditoService.getCreditoById(id);
        if(credito == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(credito);
    }

    @PostMapping("/save")
    public ResponseEntity<Credito> saveCredito(@RequestBody Credito credito) {
        Credito creditoNew = creditoService.saveCredito(credito);
        return ResponseEntity.ok(creditoNew);
    }

    @GetMapping("/byusuario/{usuarioId}")
    public ResponseEntity<List<Credito>> getByUsuarioId(@PathVariable("usuarioId") int usuarioId) {
        List<Credito> creditos = creditoService.byUsuarioId(usuarioId);
        return ResponseEntity.ok(creditos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCredito(@PathVariable("id") int id) {
        creditoService.deleteCredito(id);
        return ResponseEntity.ok("Credito eliminado");
    }

    @PostMapping("/registrar")
    public ResponseEntity<Credito> registrarCredito(
            @RequestParam("montop") double montop,
            @RequestParam("plazo") int plazo,
            @RequestParam("intanu") double intanu,
            @RequestParam("intmen") double intmen,
            @RequestParam("segudesg") double segudesg,
            @RequestParam("seguince") double seguince,
            @RequestParam("comiad") double comiad,
            @RequestParam("usuarioId") int usuarioId) {
        try {
            Credito credito = creditoService.registrarCredito(
                    montop,
                    plazo,
                    intanu,
                    intmen,
                    segudesg,
                    seguince,
                    comiad,
                    usuarioId
            );
            return ResponseEntity.ok(credito);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}