package com.example.calculo_total_service.repository;

import com.example.calculo_total_service.entity.CalculoTotal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalculoTotalRepository extends JpaRepository<CalculoTotal, Integer> { List<CalculoTotal> findByUsuarioId(int usuarioId);}