/**
 * Adapter externo para integração com o serviço ViaCEP.
 * Responsável por buscar dados de endereço a partir de um CEP usando HTTP.
 * Não expõe detalhes de infraestrutura para o domínio.
 */
package com.quadraOuro.adapters.external;

import com.quadraOuro.adapters.web.dto.EnderecoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {
    @GetMapping("/{cep}/json/")
    EnderecoResponse getEndereco(@PathVariable("cep") String cep);
}
