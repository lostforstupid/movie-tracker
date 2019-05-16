package com.lostforstupid.movietracker.movie;

import com.lostforstupid.movietracker.movie.dto.MovieView;
import com.lostforstupid.movietracker.movie.dto.UserLibraryMovieView;
import com.lostforstupid.movietracker.poster.PosterService;
import com.lostforstupid.movietracker.user_movie.UserMovie;
import com.lostforstupid.movietracker.user_movie.UserMovieRepository;
import com.lostforstupid.movietracker.user_movie.UserMovieStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MovieUtils {

  private final UserMovieRepository userMovieRepository;
  private final PosterService posterService;

  MovieView convertMovieDomainToView(Movie movie, String userId) {

    MovieView movieView = new MovieView();
    movieView.setName(movie.getName());
    movieView.setDescription(movie.getDescription());

    Long movieId = movie.getId();
    String movieIdAsString = Long.toString(movieId);
    movieView.setId(movieIdAsString);

    String posterName = movie.getPosterName();
    byte[] poster = posterService.getPoster(posterName);
    movieView.setPoster(poster);

    if (userMovieRepository.findByUserIdAndMovieId(userId, movieId) != null) {
      movieView.setInUserLibrary(true);
    }

    return movieView;
  }

  public UserLibraryMovieView convertMovieDomainToUserLibraryMovieView(Movie movie, UserMovie userMovie) {

    UserLibraryMovieView userLibraryMovieView = new UserLibraryMovieView();
    userLibraryMovieView.setName(movie.getName());
    userLibraryMovieView.setDescription(movie.getDescription());

    Long movieId = movie.getId();
    String movieIdAsString = Long.toString(movieId);
    userLibraryMovieView.setId(movieIdAsString);

    String posterName = movie.getPosterName();
    byte[] poster = posterService.getPoster(posterName);
    userLibraryMovieView.setPoster(poster);

    UserMovieStatus status = userMovie.getStatus();

    if (status != null) {
      userLibraryMovieView.setStatus(status.toString());
    }

    return userLibraryMovieView;
  }
}
