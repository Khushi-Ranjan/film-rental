package com.filmrental.service.impl;

import com.filmrental.dto.response.FilmResponse;
import com.filmrental.exception.ResourceNotFoundException;
import com.filmrental.mapper.FilmMapper;
import com.filmrental.repository.ActorRepository;
import com.filmrental.repository.FilmRepository;
import com.filmrental.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    private final ActorRepository actorRepository;
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;

    @Override @Transactional(readOnly=true)
    public List<FilmResponse> getFilmsByActorId(Integer actorId) {
        if (!actorRepository.existsById(actorId))
            throw new ResourceNotFoundException("Actor not found with id: " + actorId);
        return filmRepository.findByActors_ActorId(actorId)
                .stream().map(filmMapper::toResponse).collect(Collectors.toList());
    }
}
