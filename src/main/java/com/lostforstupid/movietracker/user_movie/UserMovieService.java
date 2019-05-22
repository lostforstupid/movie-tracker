package com.lostforstupid.movietracker.user_movie;

import com.lostforstupid.movietracker.exceptions.MovieTrackerException;
import com.lostforstupid.movietracker.movie.Movie;
import com.lostforstupid.movietracker.movie.MovieRepository;
import com.lostforstupid.movietracker.movie.MovieUtils;
import com.lostforstupid.movietracker.movie.dto.UserLibraryMovieView;
import com.lostforstupid.movietracker.sequence.SequenceService;
import com.lostforstupid.movietracker.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.lostforstupid.movietracker.utils.ErrorMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static com.lostforstupid.movietracker.utils.ErrorMessage.CAN_NOT_ADD_MOVIE_TO_LIBRARY_MOVIE_NOT_FOUND;

@SuppressWarnings("FieldCanBeLocal")
@AllArgsConstructor
@Service
class UserMovieService {

  private final UserMovieRepository userMovieRepository;
  private final MovieRepository movieRepository;
  private final MovieUtils movieUtils;
  private final UserRepository userRepository;
  private final SequenceService sequenceService;
  private final MongoTemplate mongoTemplate;
  private final Environment environment;

  private final String USER_ID_CRITERIA = "userId";

  List<UserLibraryMovieView> getUserMovies(String userId) {

    List<UserMovie> userMovies = mongoTemplate
        .find(new Query(Criteria.where(USER_ID_CRITERIA).is(userId)), UserMovie.class);

    List<UserLibraryMovieView> movies = new ArrayList<>();

    for (UserMovie userMovie : userMovies) {
      Optional<Movie> result = movieRepository.findById(userMovie.getMovieId());

      result.ifPresent(movie ->
              movies.add(movieUtils.convertMovieDomainToUserLibraryMovieView(movie, userMovie)));
    }

    return movies;
  }

  List<UserLibraryMovieView> getUserMoviesMarkedWatched(String userId) {
      return getUserMoviesByStatus(UserMovieStatus.WATCHED, userId);
  }

  List<UserLibraryMovieView> getUserMoviesMarkedToWatch(String userId) {
      return getUserMoviesByStatus(UserMovieStatus.TO_WATCH, userId);
  }

  List<UserLibraryMovieView> getUserMoviesByStatus(UserMovieStatus movieStatus, String userId) {

    List<UserLibraryMovieView> allUserMovies = getUserMovies(userId);

    return allUserMovies.stream()
            .filter(userLibraryMovieView -> movieStatus.toString().equals(userLibraryMovieView.getStatus()))
            .collect(Collectors.toList());
  }

  UserMovie addMovieToUserLibrary(String userId, String movieId) {

    Long movieIdAsLong = new Long(movieId);

    validateIds(userId, movieIdAsLong);

    if (userMovieRepository.findByUserIdAndMovieId(userId, movieIdAsLong) != null) {
      throw new MovieTrackerException(environment, ErrorMessage.MOVIE_ALREADY_IN_USER_LIBRARY);
    }

    UserMovie userMovie = new UserMovie();
    userMovie.setMovieId(movieIdAsLong);
    userMovie.setUserId(userId);
    userMovie.setStatus(UserMovieStatus.TO_WATCH);
    userMovie.setId(sequenceService.generateSequence(UserMovie.SEQUENCE_NAME));

    return userMovieRepository.save(userMovie);
  }

  void removeMovieFromUserLibrary(String userId, String movieId) {

    Long movieIdAsLong = new Long(movieId);

    validateIds(userId, movieIdAsLong);

    UserMovie userMovie = userMovieRepository.findByUserIdAndMovieId(userId, movieIdAsLong);

    if (userMovie == null) {
      throw new MovieTrackerException(environment, ErrorMessage.NO_SUCH_MOVIE_IN_USER_LIBRARY);
    }

    userMovieRepository.delete(userMovie);
  }

  void updateStatus(String userId, String movieIdAsString, String statusAsString) {

    Long movieId = new Long(movieIdAsString);
    UserMovieStatus status = UserMovieStatus.valueOf(statusAsString);
    UserMovie userMovie = userMovieRepository.findByUserIdAndMovieId(userId, movieId);

    if (userMovie == null) {
        throw new MovieTrackerException(environment, ErrorMessage.CAN_NOT_UPDATE_STATUS_MOVIE_NOT_FOUND);
    }

    userMovie.setStatus(status);
    userMovieRepository.save(userMovie);
  }

  private void validateIds(String userId, Long movieId) {

    userRepository.findById(userId).orElseThrow(() ->
            new MovieTrackerException(environment, ErrorMessage.CAN_NOT_ADD_MOVIE_TO_LIBRARY_USER_NOT_FOUND));

    movieRepository.findById(movieId)
        .orElseThrow(() ->
                new MovieTrackerException(environment, CAN_NOT_ADD_MOVIE_TO_LIBRARY_MOVIE_NOT_FOUND));

  }
}
