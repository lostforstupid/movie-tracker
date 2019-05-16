package com.lostforstupid.movietracker.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLibraryMovieView {

    private String id;
    private String name;
    private String description;
    private byte[] poster;
    private String status;
}
