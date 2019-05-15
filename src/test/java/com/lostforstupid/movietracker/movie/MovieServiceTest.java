package com.lostforstupid.movietracker.movie;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lostforstupid.movietracker.movie.dto.MovieView;
import com.lostforstupid.movietracker.poster.PosterService;
import com.lostforstupid.movietracker.sequence.SequenceService;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Movie.class)
public class MovieServiceTest {

  private MovieService movieService;

  @Mock
  private MovieRepository movieRepository;

  @Mock
  private MovieUtils movieUtils;

  @Mock
  private PosterService posterService;

  @Mock
  private SequenceService sequenceService;

  private String userId;

  private String movieName1;
  private String movieName2;

  @Before
  public void setUp() {

    movieService = new MovieService(movieRepository, movieUtils, posterService, sequenceService);
    movieService = spy(movieService);

    userId = "testUserId";

    movieName1 = "name1";
    movieName2 = "name2";
  }

  @Test
  public void getAllMovies() {

    Long id1 = 1L;
    Long id2 = 2L;

    List<Movie> movies = new ArrayList<>();
    Movie movie1 = new Movie(id1, movieName1, null, null);
    movies.add(movie1);
    Movie movie2 = new Movie(id2, movieName2, null, null);
    movies.add(movie2);

    MovieView movieView1 = new MovieView(Long.toString(id1), movieName1, null, null, false);
    MovieView movieView2 = new MovieView(Long.toString(id2), movieName2, null, null, true);

    when(movieRepository.findAll()).thenReturn(movies);
    when(movieUtils.convertMovieDomainToView(movie1, userId)).thenReturn(movieView1);
    when(movieUtils.convertMovieDomainToView(movie2, userId)).thenReturn(movieView2);

    List<MovieView> result = movieService.getAllMovies(userId);

    verify(movieUtils).convertMovieDomainToView(movie1, userId);
    verify(movieUtils).convertMovieDomainToView(movie2, userId);
    assertEquals(result.get(0), movieView1);
    assertEquals(result.get(1), movieView2);
  }
}
