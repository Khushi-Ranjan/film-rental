package com.filmrental.controller;

import com.filmrental.dto.request.CreateFilmRequest;
import com.filmrental.dto.response.FilmResponse;
import com.filmrental.service.FilmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/v1/films") @RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    // GET /films/{film_id}
    @GetMapping("/{filmId}")
    public ResponseEntity<FilmResponse> getFilmById(@PathVariable Integer filmId) {
        return ResponseEntity.ok(filmService.getFilmById(filmId));
    }

    // GET /films/category/{category_id}
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<FilmResponse>> getFilmsByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(filmService.getFilmsByCategoryId(categoryId));
    }

    // GET /films/actor/{actor_id}
    @GetMapping("/actor/{actorId}")
    public ResponseEntity<List<FilmResponse>> getFilmsByActor(@PathVariable Integer actorId) {
        return ResponseEntity.ok(filmService.getFilmsByActorId(actorId));
    }

    // GET /films/top-rented
    @GetMapping("/top-rented")
    public ResponseEntity<List<FilmResponse>> getTopRentedFilms() {
        return ResponseEntity.ok(filmService.getTopRentedFilms());
    }
}