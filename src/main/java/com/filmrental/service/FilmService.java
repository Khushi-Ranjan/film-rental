package com.filmrental.service;

import com.filmrental.dto.response.FilmResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FilmService {
    FilmResponse getFilmById(Integer id);

    Page<FilmResponse> getFilmsPage(Pageable pageable);

    List<FilmResponse> getFilmsByCategoryId(Integer categoryId);

    List<FilmResponse> getFilmsByActorId(Integer actorId);

    List<FilmResponse> getTopRentedFilms();
}
