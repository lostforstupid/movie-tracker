package com.lostforstupid.movietracker.user_movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_movies")
public class UserMovie {

  @Transient
  public static final String SEQUENCE_NAME = "user_movie_sequence";

  @Id
  private Long id;

  private Long movieId;

  private String userId;

  private UserMovieStatus status;

}
