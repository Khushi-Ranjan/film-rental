package com.filmrental.service;

import com.filmrental.dto.response.FilmResponse;
import com.filmrental.entity.Film;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.mapper.FilmMapper;
import com.filmrental.repository.ActorRepository;
import com.filmrental.repository.FilmRepository;
import com.filmrental.service.impl.ActorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @Mock private ActorRepository actorRepository;
    @Mock private FilmRepository filmRepository;
    @Mock private FilmMapper filmMapper;

    @InjectMocks
    private ActorServiceImpl actorService;

    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setFilmId(1);
        film.setTitle("ACADEMY DINOSAUR");
    }

    @Test
    void getFilmsByActorId_positive_returnsListOfFilmResponses() {
        FilmResponse response = FilmResponse.builder()
                .filmId(1)
                .title("ACADEMY DINOSAUR")
                .build();

        when(actorRepository.existsById(1)).thenReturn(true);
        when(filmRepository.findByActors_ActorId(1)).thenReturn(List.of(film));
        when(filmMapper.toResponse(film)).thenReturn(response);

        List<FilmResponse> result = actorService.getFilmsByActorId(1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getFilmId());
        assertEquals("ACADEMY DINOSAUR", result.get(0).getTitle());
    }

    @Test
    void getFilmsByActorId_negative_actorNotFound_throwsResourceNotFound() {
        when(actorRepository.existsById(999)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> actorService.getFilmsByActorId(999));
    }

    @Test
    void getFilmsByActorId_negative_returnsEmptyList() {
        when(actorRepository.existsById(1)).thenReturn(true);
        when(filmRepository.findByActors_ActorId(1)).thenReturn(Collections.emptyList());

        List<FilmResponse> result = actorService.getFilmsByActorId(1);

        assertEquals(0, result.size());
    }
}

