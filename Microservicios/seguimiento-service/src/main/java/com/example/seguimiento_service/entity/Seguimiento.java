package com.example.seguimiento_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seguimiento {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        private int idusuario;
        private int idcredito;
}