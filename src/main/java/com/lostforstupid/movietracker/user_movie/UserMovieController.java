package com.lostforstupid.movietracker.user_movie;

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
@RequestMapping("user-movie")
public class UserMovieController {

  private final UserMovieService userMovieService;

  @GetMapping("/{userId}")
  public List<MovieView> getUserLibrary(@PathVariable String userId) {
    return userMovieService.getUserMovies(userId);
  }

  @PostMapping("/{userId}/add/{movieId}")
  public UserMovie addToUserLibrary(@PathVariable String userId, @PathVariable String movieId) throws Exception {
    return userMovieService.addMovieToUserLibrary(userId, movieId);
  }

  @DeleteMapping("/{userId}/remove/{movieId}")
  public void removeMovieFromMovieLibrary(@PathVariable String userId, @PathVariable String movieId) throws Exception {
    userMovieService.removeMovieFromUserLibrary(userId, movieId);
  }
}
