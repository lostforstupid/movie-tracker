package com.lostforstupid.movietracker;

import com.lostforstupid.movietracker.user.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("FieldCanBeLocal")
@RequestMapping("/")
@Controller
public class MainController {

  private final String DATA = "data";
  private final String USER = "user";

  @GetMapping
  public String main(Model model, @AuthenticationPrincipal User user) {

    Map<Object, Object> data = new HashMap<>();
    data.put(USER, user);
    model.addAttribute(DATA, data);
    return "index";
  }

  @GetMapping("/edit")
  public String editMovie(Model model, @AuthenticationPrincipal User user) {

    Map<Object, Object> data = new HashMap<>();
    data.put(USER, user);
    model.addAttribute(DATA, data);
    return "edit-movie";
  }

  @GetMapping("/library")
  public String userMovies(Model model, @AuthenticationPrincipal User user) {

    Map<Object, Object> data = new HashMap<>();
    data.put(USER, user);
    model.addAttribute(DATA, data);
    return "user-movies";
  }
}
