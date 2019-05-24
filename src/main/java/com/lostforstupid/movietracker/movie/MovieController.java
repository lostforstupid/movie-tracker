package com.lostforstupid.movietracker.movie;

import com.lostforstupid.movietracker.movie.dto.MovieForm;
import com.lostforstupid.movietracker.movie.dto.MovieView;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("movies")
public class MovieController {

  private final MovieService movieService;

  @GetMapping
  public List<MovieView> getAllMovies(@RequestParam String userId) {
    return movieService.getAllMovies(userId);
  }

  @GetMapping("/{movieId}")
  public MovieView getMovie(@RequestParam String userId, @PathVariable String movieId) {
    return movieService.getMovie(movieId, userId);
  }

  @PostMapping
  public Movie createNewMovie(MovieForm movieForm) throws Exception {
    return movieService.create(movieForm);
  }

  @PutMapping("/{id}")
  public Movie updateMovie(@PathVariable String id, MovieForm movieForm) throws Exception {
    return movieService.update(id, movieForm);
  }

  @DeleteMapping("/{id}")
  public void deleteMovie(@PathVariable String id) {
    movieService.delete(id);
  }

  @GetMapping("/genres")
  public List<String> getAllGenres() {
    return movieService.getAllGenres();
  }
}
