package com.filmrental.service.impl;

import com.filmrental.entity.Film;
import com.filmrental.entity.Language;
import com.filmrental.dto.request.CreateFilmRequest;
import com.filmrental.dto.response.FilmResponse;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.mapper.FilmMapper;
import com.filmrental.repository.FilmRepository;
import com.filmrental.repository.LanguageRepository;
import com.filmrental.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    private final LanguageRepository languageRepository;
    private final FilmMapper filmMapper;


    @Override @Transactional(readOnly=true)
    public FilmResponse getFilmById(Integer id) {
        return filmMapper.toResponse(filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Film not found with id: " + id)));
    }

    @Override @Transactional(readOnly=true)
    public List<FilmResponse> getFilmsByCategoryId(Integer categoryId) {
        List<Film> films = filmRepository.findByCategories_CategoryId(categoryId);
        if (films.isEmpty()) throw new ResourceNotFoundException("No films found for category id: " + categoryId);
        return films.stream().map(filmMapper::toResponse).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly=true)
    public List<FilmResponse> getFilmsByActorId(Integer actorId) {
        List<Film> films = filmRepository.findByActors_ActorId(actorId);
        if (films.isEmpty()) throw new ResourceNotFoundException("No films found for actor id: " + actorId);
        return films.stream().map(filmMapper::toResponse).collect(Collectors.toList());
    }

    @Override @Transactional(readOnly=true)
    public List<FilmResponse> getTopRentedFilms() {
        return filmRepository.findTopRented().stream().map(filmMapper::toResponse).collect(Collectors.toList());
    }


}
