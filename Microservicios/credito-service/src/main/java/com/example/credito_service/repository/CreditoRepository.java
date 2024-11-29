package com.example.credito_service.repository;

import com.example.credito_service.entity.Credito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditoRepository extends JpaRepository<Credito, Integer> {
    List<Credito> findByUsuarioId(int usuarioId);
}