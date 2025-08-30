package com.quadraOuro.adapters.web;

import com.quadraOuro.adapters.web.dto.CourtRequest;
import com.quadraOuro.adapters.web.dto.CourtResponse;
import com.quadraOuro.adapters.web.dto.error.NotFoundException;
import com.quadraOuro.domain.models.Court;
import com.quadraOuro.domain.models.CourtFilter;
import com.quadraOuro.domain.models.CourtStatus;
import com.quadraOuro.ports.in.CourtUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourtControllerTest {

    @Mock
    private CourtUseCase courtUseCase;

    @InjectMocks
    private CourtController courtController;

    public CourtControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListCourts() {
        // Arrange
        var court = new Court(1L, "Court 1", "tenis", CourtStatus.ATIVA, Instant.now(), Instant.now());
        when(courtUseCase.findAll(any(CourtFilter.class))).thenReturn(List.of(court));

        // Act
        ResponseEntity<List<CourtResponse>> response = courtController.list("tenis", CourtStatus.ATIVA);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Court 1", response.getBody().get(0).name());
        verify(courtUseCase, times(1)).findAll(any(CourtFilter.class));
    }

    @Test
    void testGetById() {
        // Arrange
        var court = new Court(1L, "Court 1", "tenis", CourtStatus.ATIVA, Instant.now(), Instant.now());
        when(courtUseCase.findById(1L)).thenReturn(Optional.of(court));

        // Act
        ResponseEntity<CourtResponse> response = courtController.getById(1L);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Court 1", response.getBody().name());
        verify(courtUseCase, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        // Arrange
        when(courtUseCase.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> courtController.getById(1L));
        verify(courtUseCase, times(1)).findById(1L);
    }

    @Test
    void testCreateCourt() {
        // Arrange
        var request = new CourtRequest("Court 1", "tenis", CourtStatus.ATIVA);
        var savedCourt = new Court(1L, "Court 1", "tenis", CourtStatus.ATIVA, Instant.now(), Instant.now());
        when(courtUseCase.save(any(Court.class))).thenReturn(savedCourt);

        // Act
        ResponseEntity<CourtResponse> response = courtController.create(request);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Court 1", response.getBody().name());
        verify(courtUseCase, times(1)).save(any(Court.class));
    }

    @Test
    void testUpdateCourt() {
        // Arrange
        var existingCourt = new Court(1L, "Court 1", "tenis", CourtStatus.ATIVA, Instant.now(), Instant.now());
        var request = new CourtRequest("Updated Court", "tenis", CourtStatus.INATIVA);
        var updatedCourt = new Court(1L, "Updated Court", "tenis", CourtStatus.INATIVA, existingCourt.getCreatedAt(), Instant.now());
        when(courtUseCase.findById(1L)).thenReturn(Optional.of(existingCourt));
        when(courtUseCase.save(any(Court.class))).thenReturn(updatedCourt);

        // Act
        ResponseEntity<CourtResponse> response = courtController.update(1L, request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Updated Court", response.getBody().name());
        verify(courtUseCase, times(1)).findById(1L);
        verify(courtUseCase, times(1)).save(any(Court.class));
    }

    @Test
    void testDeleteCourt() {
        // Arrange
        var existingCourt = new Court(1L, "Court 1", "tenis", CourtStatus.ATIVA, Instant.now(), Instant.now());
        when(courtUseCase.findById(1L)).thenReturn(Optional.of(existingCourt));
        doNothing().when(courtUseCase).deleteById(1L);

        // Act
        ResponseEntity<Void> response = courtController.delete(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(courtUseCase, times(1)).findById(1L);
        verify(courtUseCase, times(1)).deleteById(1L);
    }

}