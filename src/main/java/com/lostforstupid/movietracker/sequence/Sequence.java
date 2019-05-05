package com.lostforstupid.movietracker.sequence;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "database_sequences")
public class Sequence {

  @Id
  private String id;

  private Long sequence;

}
