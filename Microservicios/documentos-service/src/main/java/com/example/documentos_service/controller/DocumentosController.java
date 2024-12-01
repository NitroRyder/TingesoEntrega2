package com.example.documentos_service.controller;

import com.example.documentos_service.entity.Documentos;
import com.example.documentos_service.repository.DocumentosRepository;
import com.example.documentos_service.service.DocumentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentosController {

    @Autowired
    DocumentosRepository documentosRepository;
    @Autowired
    private DocumentosService documentosService;

    @GetMapping
    public ResponseEntity<List<Documentos>> getAll() {
        List<Documentos> documentos = documentosRepository.findAll();
        if(documentos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documentos> getById(@PathVariable("id") int id) {
        Documentos documentos = documentosRepository.findById(id).orElse(null);
        if(documentos == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(documentos);
    }

    @PostMapping("/save")
    public ResponseEntity<Documentos> saveDocumentos(@RequestBody Documentos documentos) {
        Documentos documentosNew = documentosRepository.save(documentos);
        return ResponseEntity.ok(documentosNew);
    }

    @GetMapping("/bycredito/{creditoId}")
    public ResponseEntity<List<Documentos>> getByCreditoId(@PathVariable("creditoId") int creditoId) {
        List<Documentos> documentos = documentosRepository.findByCreditoId(creditoId);
        return ResponseEntity.ok(documentos);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocumentos(@PathVariable("id") int id) {
        documentosRepository.deleteById(id);
        return ResponseEntity.ok("Documentos eliminado");
    }

    @PostMapping("/registrar")
    public ResponseEntity<Documentos> registrarDocumentos(
            @RequestParam int creditoId,
            @RequestParam byte[] documento) {
        Documentos documentosRegistrado = documentosService.registrarDocumentos(
                creditoId,
                documento
        );
        return ResponseEntity.ok(documentosRegistrado);
    }
}