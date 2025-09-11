package com.quadraOuro.domain.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;

}
