package com.lostforstupid.movietracker.user_movie;

import java.util.List;

import com.lostforstupid.movietracker.movie.dto.UserLibraryMovieView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("user-movie")
public class UserMovieController {

  private final UserMovieService userMovieService;

  @GetMapping("/{userId}")
  public List<UserLibraryMovieView> getUserLibrary(@PathVariable String userId) {
    return userMovieService.getUserMovies(userId);
  }

  @GetMapping("/{userId}/watched")
  public List<UserLibraryMovieView> getMoviesMarkedWatchedInUserLibrary(@PathVariable String userId) {
    return userMovieService.getUserMoviesMarkedWatched(userId);
  }

  @GetMapping("/{userId}/to-watch")
  public List<UserLibraryMovieView> getMoviesMarkedToWatchInUserLibrary(@PathVariable String userId) {
    return userMovieService.getUserMoviesMarkedToWatch(userId);
  }

  @PostMapping("/{userId}/add/{movieId}")
  public UserMovie addToUserLibrary(@PathVariable String userId,
                                    @PathVariable String movieId) throws Exception {
    return userMovieService.addMovieToUserLibrary(userId, movieId);
  }

  @DeleteMapping("/{userId}/remove/{movieId}")
  public void removeMovieFromMovieLibrary(@PathVariable String userId,
                                          @PathVariable String movieId) throws Exception {
    userMovieService.removeMovieFromUserLibrary(userId, movieId);
  }

  @PutMapping("/{userId}/update-status/{movieId}")
  public void updateMovieStatus(@PathVariable String userId, @PathVariable String movieId,
                                @RequestParam String status) throws Exception {
    userMovieService.updateStatus(userId, movieId, status);
  }
}
