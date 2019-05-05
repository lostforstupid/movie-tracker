package com.lostforstupid.movietracker.movie;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {

  @Transient
  public static final String SEQUENCE_NAME = "movie_sequence";

  @Id
  private Long id;

  @NotNull
  private String name;

  private String description;

  private String posterName;

}
