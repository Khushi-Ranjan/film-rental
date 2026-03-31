package com.filmrental.service;
import com.filmrental.dto.request.CreateFilmRequest;
import com.filmrental.dto.response.FilmResponse;
import java.util.List;
public interface FilmService {
    FilmResponse getFilmById(Integer id);
    List<FilmResponse> getFilmsByCategoryId(Integer categoryId);
    List<FilmResponse> getFilmsByActorId(Integer actorId);
    List<FilmResponse> getTopRentedFilms();
}
