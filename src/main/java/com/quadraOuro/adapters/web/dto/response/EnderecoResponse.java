package com.quadraOuro.adapters.web.dto.response;

public record EnderecoResponse(
    String cep,
    String logradouro,
    String bairro,
    String localidade,
    String uf,
    String estado,
    String regiao
) {}
