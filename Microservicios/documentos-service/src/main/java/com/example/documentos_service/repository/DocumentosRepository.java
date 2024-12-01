package com.example.documentos_service.repository;

import com.example.documentos_service.entity.Documentos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Integer> {
    List<Documentos> findByCreditoId(int creditoId);
}