package com.example.evalua_service.model;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Documentos {
    private int id;
    private int creditoId;
    @Lob
    private byte[] documento;
}
