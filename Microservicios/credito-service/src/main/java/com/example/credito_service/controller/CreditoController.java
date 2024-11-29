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

    // CreditoController.java
    @PostMapping("/createSolicitud")
    public ResponseEntity<?> createSolicitud(
            @RequestParam Long userId,
            @RequestParam double montop,
            @RequestParam int plazo,
            @RequestParam double intanu,
            @RequestParam double intmen,
            @RequestParam double segudesg,
            @RequestParam double seguince,
            @RequestParam double comiad,
            @RequestParam MultipartFile comprobanteIngresos,
            @RequestParam MultipartFile certificadoAvaluo,
            @RequestParam MultipartFile historialCrediticio,
            @RequestParam MultipartFile escrituraPrimeraVivienda,
            @RequestParam MultipartFile planNegocios,
            @RequestParam MultipartFile estadosFinancieros,
            @RequestParam MultipartFile presupuestoRemodelacion,
            @RequestParam MultipartFile dicom) {

        try {
            Credito solicitud = creditoService.createSolicitud(
                    userId,
                    montop,
                    plazo,
                    intanu,
                    intmen,
                    segudesg,
                    seguince,
                    comiad,
                    comprobanteIngresos.getBytes(),
                    certificadoAvaluo.getBytes(),
                    historialCrediticio.getBytes(),
                    escrituraPrimeraVivienda.getBytes(),
                    planNegocios.getBytes(),
                    estadosFinancieros.getBytes(),
                    presupuestoRemodelacion.getBytes(),
                    dicom.getBytes()
            );
            return ResponseEntity.ok(solicitud);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception details
            return ResponseEntity.status(500).body("ERROR INTERNO DEL SERVIDOR: " + e.getMessage());
        }
    }
}