package com.lostforstupid.movietracker.movie;

import com.lostforstupid.movietracker.movie.dto.MovieForm;
import com.lostforstupid.movietracker.movie.dto.MovieView;
import com.lostforstupid.movietracker.poster.PosterService;
import com.lostforstupid.movietracker.sequence.SequenceService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class MovieService {

  private final MovieRepository movieRepository;
  private final MovieUtils movieUtils;
  private final PosterService posterService;
  private final SequenceService sequenceService;

  List<MovieView> getAllMovies() {

    return movieRepository.findAll().stream()
        .map(movieUtils::convertMovieDomainToView)
        .collect(Collectors.toList());
  }

  Movie save(MovieForm movieForm) throws Exception {

    String name = movieForm.getName();

    if (name == null) {
      throw new Exception("Movie name can't be null");
    }

    Movie movie = new Movie();
    movie.setName(name);
    movie.setDescription(movieForm.getDescription());
    movie.setId(sequenceService.generateSequence(Movie.SEQUENCE_NAME));

    MultipartFile poster = movieForm.getPoster();

    if (poster != null) {
      String posterName = poster.getOriginalFilename();
      posterService.savePoster(poster, posterName);
      movie.setPosterName(posterName);
    }

    return movieRepository.save(movie);
  }

  void delete(String idAsString) throws Exception {

    Long id = new Long(idAsString);
    Optional<Movie> movie = movieRepository.findById(id);

    if (movie.isPresent()) {
      movieRepository.delete(movie.get());
    } else {
      throw new Exception("Can't delete movie with id " + id + ", no such id in the database");
    }
  }
}
