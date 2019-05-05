package com.lostforstupid.movietracker.movie;

import com.lostforstupid.movietracker.movie.dto.MovieView;
import com.lostforstupid.movietracker.poster.PosterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MovieUtils {

  private final PosterService posterService;

  public MovieView convertMovieDomainToView(Movie movie) {

    MovieView movieView = new MovieView();
    movieView.setName(movie.getName());
    movieView.setDescription(movie.getDescription());

    Long id = movie.getId();
    movieView.setId(Long.toString(id));

    String posterName = movie.getPosterName();
    byte[] poster = posterService.getPoster(posterName);
    movieView.setPoster(poster);

    return movieView;
  }
}
