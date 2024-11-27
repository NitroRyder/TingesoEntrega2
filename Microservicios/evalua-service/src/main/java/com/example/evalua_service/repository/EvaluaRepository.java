package com.example.evalua_service.repository;

import com.example.evalua_service.entity.Evalua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluaRepository extends JpaRepository<Evalua, Integer> {
}