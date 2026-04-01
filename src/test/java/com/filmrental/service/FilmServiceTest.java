package com.filmrental.service;

import com.filmrental.dto.response.FilmResponse;
import com.filmrental.entity.Film;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.mapper.FilmMapper;
import com.filmrental.repository.FilmRepository;
import com.filmrental.service.impl.FilmServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private FilmMapper filmMapper;

    @InjectMocks
    private FilmServiceImpl filmService;

    // -- test cases for getFilmById --

    @Test
    void getFilmById_success() {
        Film film = new Film();
        film.setFilmId(1);

        when(filmRepository.findById(1)).thenReturn(Optional.of(film));
        when(filmMapper.toResponse(film)).thenReturn(FilmResponse.builder().filmId(1).build());

        FilmResponse result = filmService.getFilmById(1);

        assertEquals(1, result.getFilmId());
    }

    @Test
    void getFilmById_notFound() {
        when(filmRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> filmService.getFilmById(99));
    }

    @Test
    void getFilmById_edge_verifyAndMessage() {
        when(filmRepository.findById(99)).thenReturn(Optional.empty());

        Exception ex = assertThrows(ResourceNotFoundException.class,
                () -> filmService.getFilmById(99));

        assertTrue(ex.getMessage().contains("Film not found"));
        verify(filmRepository).findById(99);
    }

    // --test cases for getFilmsByCategoryId --

    @Test
    void getFilmsByCategory_success() {
        Film film = new Film();

        when(filmRepository.findByCategories_CategoryId(1)).thenReturn(List.of(film));
        when(filmMapper.toResponse(film)).thenReturn(FilmResponse.builder().build());

        List<FilmResponse> result = filmService.getFilmsByCategoryId(1);

        assertEquals(1, result.size());
    }

    @Test
    void getFilmsByCategory_empty() {
        when(filmRepository.findByCategories_CategoryId(99)).thenReturn(List.of());

        List<FilmResponse> result = filmService.getFilmsByCategoryId(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void getFilmsByCategory_edge_verifyCall() {
        when(filmRepository.findByCategories_CategoryId(1)).thenReturn(List.of());

        filmService.getFilmsByCategoryId(1);

        verify(filmRepository).findByCategories_CategoryId(1);
    }

    // -- test cases for getFilmsByActorId --

    @Test
    void getFilmsByActor_success() {
        Film film = new Film();

        when(filmRepository.findByActors_ActorId(1)).thenReturn(List.of(film));
        when(filmMapper.toResponse(film)).thenReturn(FilmResponse.builder().build());

        List<FilmResponse> result = filmService.getFilmsByActorId(1);

        assertEquals(1, result.size());
    }

    @Test
    void getFilmsByActor_empty() {
        when(filmRepository.findByActors_ActorId(99)).thenReturn(List.of());

        List<FilmResponse> result = filmService.getFilmsByActorId(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void getFilmsByActor_edge_largeList() {
        List<Film> films = List.of(new Film(), new Film(), new Film());

        when(filmRepository.findByActors_ActorId(1)).thenReturn(films);
        when(filmMapper.toResponse(any())).thenReturn(FilmResponse.builder().build());

        List<FilmResponse> result = filmService.getFilmsByActorId(1);

        assertEquals(3, result.size());
    }

    //-- test cases for getTopRentedFilms --

    @Test
    void getTopRented_success() {
        Film film = new Film();

        when(filmRepository.findTopRented()).thenReturn(List.of(film));
        when(filmMapper.toResponse(film)).thenReturn(FilmResponse.builder().build());

        List<FilmResponse> result = filmService.getTopRentedFilms();

        assertEquals(1, result.size());
    }

    @Test
    void getTopRented_empty() {
        when(filmRepository.findTopRented()).thenReturn(List.of());

        List<FilmResponse> result = filmService.getTopRentedFilms();

        assertTrue(result.isEmpty());
    }

    @Test
    void getTopRented_edge_verifyMapper() {
        Film film = new Film();

        when(filmRepository.findTopRented()).thenReturn(List.of(film));
        when(filmMapper.toResponse(film)).thenReturn(FilmResponse.builder().build());

        filmService.getTopRentedFilms();

        verify(filmMapper, atLeastOnce()).toResponse(any());
    }
}