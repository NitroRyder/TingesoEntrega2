package com.example.total_service.repository;

import com.example.total_service.entity.Total;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TotalRepository extends JpaRepository<Total, Integer> { List<Total> findByUsuarioId(int usuarioId);}