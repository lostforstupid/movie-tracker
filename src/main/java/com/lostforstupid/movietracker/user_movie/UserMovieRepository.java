package com.lostforstupid.movietracker.user_movie;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMovieRepository extends MongoRepository<UserMovie, Long> {

  UserMovie findByUserIdAndMovieId(String userId, Long movieId);

}
