package com.lostforstupid.movietracker.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovieForm {

  private String name;
  private String description;
  private MultipartFile poster;
  private String year;
  private List<String> genres;
}
