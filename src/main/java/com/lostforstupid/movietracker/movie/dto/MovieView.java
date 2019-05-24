package com.lostforstupid.movietracker.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieView {

  private String id;
  private String name;
  private String description;
  private byte[] poster;
  private String year;
  private List<String> genres;
  private boolean isInUserLibrary;
}
