package com.example.credito_service.service;

import com.example.credito_service.entity.Credito;
import com.example.credito_service.repository.CreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CreditoService {

        @Autowired
        CreditoRepository creditoRepository;

        public List<Credito> getAll() {return creditoRepository.findAll();}

        public Credito getCreditoById(int id) {return creditoRepository.findById(id).orElse(null);}

        public Credito save(Credito credito) {
            Credito creditoNew = creditoRepository.save(credito);
            return creditoNew;
        }

        public void deleteCredito(int id) {creditoRepository.deleteById(id);}

        public List<Credito> byUsuarioId(int usuarioId) {return creditoRepository.findByUsuarioId(usuarioId);}
}
