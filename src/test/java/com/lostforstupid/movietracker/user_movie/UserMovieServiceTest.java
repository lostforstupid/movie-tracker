package com.lostforstupid.movietracker.user_movie;

import com.lostforstupid.movietracker.movie.MovieRepository;
import com.lostforstupid.movietracker.movie.MovieUtils;
import com.lostforstupid.movietracker.sequence.SequenceService;
import com.lostforstupid.movietracker.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class UserMovieServiceTest {

    private UserMovieService userMovieService;

    @Mock
    private UserMovieRepository userMovieRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieUtils movieUtils;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SequenceService sequenceService;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private Environment environment;

    private String userId;

    @Before
    public void setUp() {

        userMovieService = new UserMovieService(userMovieRepository, movieRepository, movieUtils,
                userRepository, sequenceService, mongoTemplate, environment);
        userMovieService = spy(userMovieService);

        userId = "userId";
    }

    @Test
    public void getUserMoviesMarkedWatched() {

        userMovieService.getUserMoviesMarkedWatched(userId);

        verify(userMovieService).getUserMoviesByStatus(UserMovieStatus.WATCHED, userId);
    }

    @Test
    public void getUserMoviesMarkedToWatch() {

        userMovieService.getUserMoviesMarkedToWatch(userId);

        verify(userMovieService).getUserMoviesByStatus(UserMovieStatus.TO_WATCH, userId);
    }
}
