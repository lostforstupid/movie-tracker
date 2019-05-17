package com.lostforstupid.movietracker.movie;

import com.lostforstupid.movietracker.exceptions.MovieTrackerException;
import com.lostforstupid.movietracker.movie.dto.MovieForm;
import com.lostforstupid.movietracker.movie.dto.MovieView;
import com.lostforstupid.movietracker.poster.PosterService;
import com.lostforstupid.movietracker.sequence.SequenceService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lostforstupid.movietracker.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
class MovieService {

  private final MovieRepository movieRepository;
  private final MovieUtils movieUtils;
  private final PosterService posterService;
  private final SequenceService sequenceService;
  private final Environment environment;

  List<MovieView> getAllMovies(String userId) {

    return movieRepository.findAll().stream()
        .map(movie -> movieUtils.convertMovieDomainToView(movie, userId))
        .collect(Collectors.toList());
  }

  Movie create(MovieForm movieForm) throws Exception {

    Movie movie = new Movie();
    movie.setId(sequenceService.generateSequence(Movie.SEQUENCE_NAME));
    return save(movieForm, movie);
  }

  Movie update(String idAsString, MovieForm movieForm) throws Exception {

    Long id = new Long(idAsString);

    Movie movie = movieRepository.findById(id)
        .orElseThrow(() -> new Exception("Can't update the movie. "
            + "No movie with id " + id + " found in the database."));

    return save(movieForm, movie);
  }

  Movie save(MovieForm movieForm, Movie movie) throws Exception {

    String name = movieForm.getName();

    if (name == null) {
      throw new MovieTrackerException(environment, ErrorMessage.MOVIE_NAME_IS_NULL);
    }

    movie.setName(name);
    movie.setDescription(movieForm.getDescription());

    MultipartFile poster = movieForm.getPoster();

    if (poster != null) {
      String posterName = poster.getOriginalFilename();
      posterService.savePoster(poster, posterName);
      movie.setPosterName(posterName);
    }

    return movieRepository.save(movie);
  }

  void delete(String idAsString) {

    Long id = new Long(idAsString);
    Optional<Movie> movie = movieRepository.findById(id);

    if (movie.isPresent()) {
      movieRepository.delete(movie.get());
    } else {
      throw new MovieTrackerException(environment, ErrorMessage.CAN_NOT_DELETE_MOVIE_NO_SUCH_ID);
    }
  }
}
