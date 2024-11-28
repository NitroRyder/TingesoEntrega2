package com.example.seguimiento_service.repository;

import com.example.seguimiento_service.entity.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguimientoRepository extends JpaRepository<Seguimiento, Integer> { List<Seguimiento> findByIdusuario(int usuarioId);}