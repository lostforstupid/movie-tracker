package com.lostforstupid.movietracker.user_movie;

import com.lostforstupid.movietracker.movie.Movie;
import com.lostforstupid.movietracker.movie.MovieRepository;
import com.lostforstupid.movietracker.movie.MovieUtils;
import com.lostforstupid.movietracker.movie.dto.UserLibraryMovieView;
import com.lostforstupid.movietracker.sequence.SequenceService;
import com.lostforstupid.movietracker.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

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

  UserMovie addMovieToUserLibrary(String userId, String movieId) throws Exception {

    Long movieIdAsLong = new Long(movieId);

    validateIds(userId, movieIdAsLong);

    if (userMovieRepository.findByUserIdAndMovieId(userId, movieIdAsLong) != null) {
      throw new Exception("This movie is already in this user's library");
    }

    UserMovie userMovie = new UserMovie();
    userMovie.setMovieId(movieIdAsLong);
    userMovie.setUserId(userId);
    userMovie.setStatus(UserMovieStatus.TO_WATCH);
    userMovie.setId(sequenceService.generateSequence(UserMovie.SEQUENCE_NAME));

    return userMovieRepository.save(userMovie);
  }

  void removeMovieFromUserLibrary(String userId, String movieId) throws Exception {

    Long movieIdAsLong = new Long(movieId);

    validateIds(userId, movieIdAsLong);

    UserMovie userMovie = userMovieRepository.findByUserIdAndMovieId(userId, movieIdAsLong);

    if (userMovie == null) {
      throw new Exception("No such movie in this user's library");
    }

    userMovieRepository.delete(userMovie);
  }

  void updateStatus(String userId, String movieIdAsString, String statusAsString) throws Exception {

    Long movieId = new Long(movieIdAsString);
    UserMovieStatus status = UserMovieStatus.valueOf(statusAsString);
    UserMovie userMovie = userMovieRepository.findByUserIdAndMovieId(userId, movieId);

    if (userMovie == null) {
        throw new Exception("Can't update movie status, movie wasn't found.");
    }

    userMovie.setStatus(status);
    userMovieRepository.save(userMovie);
  }

  private void validateIds(String userId, Long movieId) throws Exception {

    userRepository.findById(userId).orElseThrow(() -> new Exception("Can't add movie to library, user wasn't found."));

    movieRepository.findById(movieId)
        .orElseThrow(() -> new Exception("Can't add movie to library, movie wasn't found."));

  }
}
