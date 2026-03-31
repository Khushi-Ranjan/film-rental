package com.filmrental.repository;

import com.filmrental.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    List<Film> findByCategories_CategoryId(Integer categoryId);
    List<Film> findByActors_ActorId(Integer actorId);

    // Native SQL query to retrieve the top 10 most rented films ordered by rental count in descending order
    @Query(value = """
            SELECT f.* FROM film f
            INNER JOIN inventory i ON f.film_id = i.film_id
            INNER JOIN rental r   ON i.inventory_id = r.inventory_id
            GROUP BY f.film_id
            ORDER BY COUNT(r.rental_id) DESC
            LIMIT 10
            """, nativeQuery = true)
    List<Film> findTopRented();
}
