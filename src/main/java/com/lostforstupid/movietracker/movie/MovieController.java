package com.lostforstupid.movietracker.movie;

import com.lostforstupid.movietracker.movie.dto.MovieForm;
import com.lostforstupid.movietracker.movie.dto.MovieView;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("movies")
public class MovieController {

  private final MovieService movieService;

  @GetMapping
  public List<MovieView> getAllMovies() {
    return movieService.getAllMovies();
  }

  @PostMapping
  public Movie createNewMovie(MovieForm movieForm) throws Exception {
    return movieService.save(movieForm);
  }

  @DeleteMapping("/{id}")
  public void deleteMovie(@PathVariable String id) throws Exception {
    movieService.delete(id);
  }
}
