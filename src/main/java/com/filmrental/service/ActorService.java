package com.filmrental.service;
import com.filmrental.dto.response.FilmResponse;
import java.util.List;
public interface ActorService {
    List<FilmResponse> getFilmsByActorId(Integer actorId);
}
