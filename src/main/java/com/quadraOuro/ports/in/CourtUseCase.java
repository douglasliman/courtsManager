/**
 * Porta de entrada (Application Service) para casos de uso relacionados a quadras.
 * Define as operações disponíveis para a aplicação manipular quadras, sem expor detalhes de infraestrutura.
 */
package com.quadraOuro.ports.in;

import com.quadraOuro.domain.models.Court;
import com.quadraOuro.domain.models.CourtFilter;

import java.util.List;
// ...existing code...

public interface CourtUseCase {
    List<Court> findAll(CourtFilter filter);
    Court findById(Long id);
    Court save(Court court);
    void deleteById(Long id);
}