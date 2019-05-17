package com.lostforstupid.movietracker.exceptions;

import com.lostforstupid.movietracker.utils.ErrorMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.core.env.Environment;

@SuppressWarnings("FieldCanBeLocal")
@EqualsAndHashCode(callSuper = true)
@Data
public class MovieTrackerException extends RuntimeException {

    private String errorMessage;

    private final String PREFIX = "movieTracker.errors.";

    public MovieTrackerException(Environment environment, ErrorMessage errorCode) {
        errorMessage = environment.getProperty(PREFIX + errorCode);
    }
}
