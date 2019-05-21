package com.lostforstupid.movietracker.sequence;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@SuppressWarnings("FieldCanBeLocal")
@AllArgsConstructor
@Service
public class SequenceService {

  private final MongoOperations mongoOperations;

  private final String SEQUENCE_KEY = "sequence";
  private final String ID_SUFFIX = "_id";

  public long generateSequence(String sequenceName) {

    Sequence counter = mongoOperations
        .findAndModify(query(where(ID_SUFFIX).is(sequenceName)), new Update().inc(SEQUENCE_KEY, 1),
            options().returnNew(true).upsert(true), Sequence.class);

    return counter.getSequence();
  }
}
