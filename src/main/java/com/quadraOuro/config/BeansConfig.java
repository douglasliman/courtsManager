package com.quadraOuro.config;

import com.quadraOuro.adapters.persistence.jpa.CourtJpaAdapter;
import com.quadraOuro.adapters.persistence.jpa.SpringDataCourtRepository;
import com.quadraOuro.application.CourtQueryService;
import com.quadraOuro.ports.in.CourtUseCase;
import com.quadraOuro.ports.out.CourtRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    CourtRepositoryPort courtRepositoryPort(SpringDataCourtRepository repo) {
        return new CourtJpaAdapter(repo);
    }

    @Bean
    CourtUseCase courtQueryUseCase(CourtRepositoryPort repositoryPort) {
        return new CourtQueryService(repositoryPort);
    }

    @Bean
    CourtUseCase courtUseCase(CourtRepositoryPort repositoryPort) {
        return new CourtQueryService(repositoryPort);
    }

}
