package com.lostforstupid.movietracker.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieView {

  private String id;
  private String name;
  private String description;
  private byte[] poster;
}
