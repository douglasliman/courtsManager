/**
 * Porta de saída para persistência de quadras.
 * Define o contrato que qualquer implementação de repositório de quadra deve seguir (ex: JPA, memória, etc).
 * Permite desacoplar o domínio da tecnologia de persistência.
 */
package com.quadraOuro.ports.out;

import java.util.List;
import java.util.Optional;

import com.quadraOuro.domain.models.Court;
import com.quadraOuro.domain.models.CourtFilter;

public interface CourtRepositoryPort {
    List<Court> findAll(CourtFilter filter);

    Optional<Court> findById(Long id);

    Court save(Court court);

    void deleteById(Long id);

}
