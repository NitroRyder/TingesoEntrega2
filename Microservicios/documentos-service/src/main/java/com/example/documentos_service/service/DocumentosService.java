package com.example.documentos_service.service;

import com.example.documentos_service.entity.Documentos;
import com.example.documentos_service.repository.DocumentosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentosService {

    @Autowired
    DocumentosRepository documentosRepository;

    public List<Documentos> getAll() {return documentosRepository.findAll();}

    public Documentos getDocumentosById(int id) {return documentosRepository.findById(id).orElse(null);}

    public Documentos saveDocumentos(Documentos documentos) {
        Documentos documentosNew = documentosRepository.save(documentos);
        return documentosNew;
    }
    public void deleteDocumentos(int id) {documentosRepository.deleteById(id);}

    public List<Documentos> byCreditoId(int creditoId) {
        return documentosRepository.findByCreditoId(creditoId);
    }

    public Documentos registrarDocumentos(int creditoId, byte[] documento) {
        Documentos documentos = new Documentos();
        documentos.setCreditoId(creditoId);
        documentos.setDocumento(documento);
        return documentosRepository.save(documentos);
    }
}