package com.lostforstupid.movietracker.movie;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lostforstupid.movietracker.exceptions.MovieTrackerException;
import com.lostforstupid.movietracker.movie.dto.MovieForm;
import com.lostforstupid.movietracker.movie.dto.MovieView;
import com.lostforstupid.movietracker.poster.PosterService;
import com.lostforstupid.movietracker.sequence.SequenceService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.lostforstupid.movietracker.utils.ErrorMessage;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.core.env.Environment;

@SuppressWarnings("FieldCanBeLocal")
@RunWith(PowerMockRunner.class)
@PrepareForTest(Movie.class)
public class MovieServiceTest {

  private final String PROPERTY_FILE_NAME_PREFIX = "movieTracker.errors.";

  private MovieService movieService;

  @Mock
  private MovieRepository movieRepository;

  @Mock
  private MovieUtils movieUtils;

  @Mock
  private PosterService posterService;

  @Mock
  private SequenceService sequenceService;

  @Mock
  private Environment environment;

  private List<Movie> movies;

  private Movie movie;

  private Movie movie1;
  private Movie movie2;

  private MovieView movieView1;
  private MovieView movieView2;

  private String userId;
  private String movieId;

  private Long id1;
  private Long id2;

  private String movieName1;
  private String movieName2;

  private Integer year1;
  private Integer year2;

  private String yearAsString1;
  private String yearAsString2;

  @Before
  public void setUp() {

    movieService = new MovieService(movieRepository, movieUtils, posterService, sequenceService, environment);
    movieService = spy(movieService);

    userId = "testUserId";
    movieId = "1";

    movieName1 = "name1";
    movieName2 = "name2";

    movie = new Movie(1L, "testName", "testDescription",
            "testPosterName", 1995, null);

    id1 = 1L;
    id2 = 2L;

    year1 = 2019;
    yearAsString1 = String.valueOf(year1);

    year2 = 1995;
    yearAsString2 = String.valueOf(year2);

    movies = new ArrayList<>();
    movie1 = new Movie(id1, movieName1, null, null, year1, null);
    movies.add(movie1);
    movie2 = new Movie(id2, movieName2, null, null, year2, null);
    movies.add(movie2);

    movieView1 = new MovieView(Long.toString(id1), movieName1, null,
            null, yearAsString1, null, false);
    movieView2 = new MovieView(Long.toString(id2), movieName2, null,
            null, yearAsString2, null, true);
  }

  @Test
  public void getAllMovies() {

    when(movieRepository.findAll()).thenReturn(movies);
    when(movieUtils.convertMovieDomainToView(movie1, userId)).thenReturn(movieView1);
    when(movieUtils.convertMovieDomainToView(movie2, userId)).thenReturn(movieView2);

    List<MovieView> result = movieService.getAllMovies(userId);

    verify(movieUtils).convertMovieDomainToView(movie1, userId);
    verify(movieUtils).convertMovieDomainToView(movie2, userId);
    assertEquals(result.get(0), movieView1);
    assertEquals(result.get(1), movieView2);
  }

  @SneakyThrows
  @Test
  public void save_success() {

    String newName = "newName";
    String newDescription = "newDescription";

    String yearAsString = String.valueOf(movie.getYear());

    MovieForm movieForm = new MovieForm(newName, newDescription, null,
            yearAsString,null);

    movieService.save(movieForm, movie);

    verify(movieRepository).save(movie);
  }

  @SneakyThrows
  @Test(expected = MovieTrackerException.class)
  public void save_nameIsNull_MovieTrackerException_expected() {
    movieService.save(new MovieForm(), movie);
  }

  @Test
  public void delete_success() {

    Long movieIdAsLong = Long.parseLong(movieId);
    Optional<Movie> queryResult = Optional.of(movie);

    when(movieRepository.findById(movieIdAsLong)).thenReturn(queryResult);

    movieService.delete(movieId);

    verify(movieRepository).delete(movie);
  }

   @Test(expected = MovieTrackerException.class)
   public void delete_MovieTrackerException_expected() {

     Long movieIdAsLong = Long.parseLong(movieId);
     Optional<Movie> queryResult = Optional.empty();

     when(movieRepository.findById(movieIdAsLong)).thenReturn(queryResult);
     when(environment.getProperty(PROPERTY_FILE_NAME_PREFIX + ErrorMessage.CAN_NOT_DELETE_MOVIE_NO_SUCH_ID))
             .thenReturn("Test error message");

     movieService.delete(movieId);
   }
}
