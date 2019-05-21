package com.lostforstupid.movietracker;

import com.lostforstupid.movietracker.movie.MovieService;
import com.lostforstupid.movietracker.movie.dto.MovieView;
import com.lostforstupid.movietracker.user.User;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("FieldCanBeLocal")
@AllArgsConstructor
@RequestMapping("/")
@Controller
public class MainController {

  private final MovieService movieService;

  private final String DATA = "data";
  private final String USER = "user";
  private final String ID = "id";
  private final String POSTER = "poster";
  private final String NAME = "name";
  private final String DESCRIPTION = "description";

  @GetMapping
  public String main(Model model, @AuthenticationPrincipal User user) {

    model.addAttribute(DATA, getData(user));
    return "index";
  }

  @GetMapping("/{movieId}")
  public String viewMovie(Model model, @PathVariable String movieId,
                          @AuthenticationPrincipal User user) {

    Map <Object, Object> data = getData(user);
    data.put(ID, movieId);
    model.addAttribute(DATA, data);

    return "movie";
  }

  @GetMapping("/add")
  public String addMovie(Model model, @AuthenticationPrincipal User user) {

    model.addAttribute(DATA, getData(user));
    return "add-movie";
  }

  @GetMapping("/edit/{id}")
  public String editMovie(Model model, @PathVariable String id,
                          @AuthenticationPrincipal User user) {

    Map <Object, Object> data = getData(user);
    data.put(ID, id);

    MovieView movie = movieService.getMovie(id, user.getId());

    data.put(NAME, movie.getName());
    data.put(DESCRIPTION, movie.getDescription());
    data.put(POSTER, movie.getPoster());

    model.addAttribute(DATA, data);

    return "edit-movie";
  }

  @GetMapping("/library")
  public String userMovies(Model model, @AuthenticationPrincipal User user) {

    model.addAttribute(DATA, getData(user));
    return "user-movies";
  }

  private Map<Object, Object> getData(User user) {
    Map<Object, Object> data = new HashMap<>();
    data.put(USER, user);
    return data;
  }
}
