package com.lostforstupid.movietracker.config;

import com.lostforstupid.movietracker.movie.Movie;
import com.lostforstupid.movietracker.user.User;
import com.lostforstupid.movietracker.user_movie.UserMovie;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@AllArgsConstructor
@EnableMongoRepositories(basePackageClasses = {Movie.class, User.class, UserMovie.class})
public class MongoDBConfig {

  private final MongoDbFactory factory;
  private final MappingMongoConverter converter;

  @Bean
  public GridFsTemplate gridFsTemplate() {
    return new GridFsTemplate(factory, converter);
  }
}
