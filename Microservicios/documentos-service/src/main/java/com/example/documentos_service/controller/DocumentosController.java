package com.example.documentos_service.controller;

import com.example.documentos_service.entity.Documentos;
import com.example.documentos_service.repository.DocumentosRepository;
import com.example.documentos_service.service.DocumentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/documentos")
public class DocumentosController {

    @Autowired
    private DocumentosService documentosService;

    @GetMapping
    public ResponseEntity<List<Documentos>> getAll() {
        List<Documentos> documentos = documentosService.getAll();
        if(documentos.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documentos> getById(@PathVariable("id") int id) {
        Documentos documentos = documentosService.getDocumentosById(id);
        if(documentos == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(documentos);
    }

    @PostMapping("/save")
    public ResponseEntity<Documentos> saveDocumentos(@RequestBody Documentos documentos) {
        Documentos documentosNew = documentosService.saveDocumentos(documentos);
        return ResponseEntity.ok(documentosNew);
    }

    @GetMapping("/bycredito/{creditoId}")
    public ResponseEntity<List<Documentos>> getByCreditoId(@PathVariable("creditoId") int creditoId) {
        List<Documentos> documentos = documentosService.byCreditoId(creditoId);
        return ResponseEntity.ok(documentos);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable("id") int id) {
        Documentos documentos = documentosService.getDocumentosById(id);
        if (documentos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=document_" + id + ".bin")
                .body(documentos.getDocumento());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocumentos(@PathVariable("id") int id) {
        documentosService.deleteDocumentos(id);
        return ResponseEntity.ok("Documentos eliminado");
    }

    @PostMapping("/registrar")
    public ResponseEntity<Documentos> registrarDocumentos(
            @RequestParam("creditoId") Long creditoId,
            @RequestParam("documento") MultipartFile documento) throws IOException {

        byte[] documentoBytes = documento.getBytes();
        Documentos documentosRegistrado = documentosService.registrarDocumentos(creditoId.intValue(), documentoBytes);
        return ResponseEntity.ok(documentosRegistrado);
    }
}