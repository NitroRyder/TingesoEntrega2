package com.example.simula_service.repository;

import com.example.simula_service.entity.Simula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimulaRepository extends JpaRepository<Simula, Integer> { List<Simula> findByTipo(int tipo);}