package com.example.ahorro_service.repository;

import com.example.ahorro_service.entity.Ahorro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AhorroRepository extends JpaRepository<Ahorro, Integer> { List<Ahorro> findByUsuarioId(int usuarioId);}