package com.example.ahorro_service.service;

import com.example.ahorro_service.entity.Ahorro;
import com.example.ahorro_service.repository.AhorroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AhorroService {

    @Autowired
    AhorroRepository ahorroRepository;

    public List<Ahorro> getAll() {return ahorroRepository.findAll();}

    public Ahorro getAhorroById(int id) {return ahorroRepository.findById(id).orElse(null);}

    public Ahorro save(Ahorro ahorro) {
        Ahorro ahorroNew = ahorroRepository.save(ahorro);
        return ahorroNew;
    }

    public List<Ahorro> byUsuarioId(int usuarioId) {return ahorroRepository.findByUsuarioId(usuarioId);}

    // CREAR AHORRO CON EL ID DE UN USUARIO
    public Ahorro createAhorro(int transaccion, String tipo, int usuarioId) {
        Ahorro ahorro = new Ahorro();
        ahorro.setTransaccion(transaccion);
        ahorro.setTipo(tipo);
        ahorro.setUsuarioId(usuarioId);
        return ahorroRepository.save(ahorro);
    }

    public void deleteAhorro(int id) {
        ahorroRepository.deleteById(id);
    }
    //------------------------------[OPERACIONES AHORRO]------------------------------------//
    // + OBTENER EL VALOR POSITIVO MAS CHICO DENTRO DE LA LISTA DE AHORROS DEL USUARIO
    public int obtenerValorPositivoMasPequeno(List<Ahorro> ahorros) {
        int valorPositivoMasPequeno = Integer.MAX_VALUE;
        boolean foundPositive = false;

        for (Ahorro ahorro : ahorros) {
            int transaccion = ahorro.getTransaccion();
            if (transaccion > 0 && transaccion < valorPositivoMasPequeno) {
                valorPositivoMasPequeno = transaccion;
                foundPositive = true;
            }
        }
        return foundPositive ? valorPositivoMasPequeno : -1; // Devuelve -1 si no se encuentra ningún valor positivo
    }
}