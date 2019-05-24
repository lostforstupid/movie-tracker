package com.lostforstupid.movietracker.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Genre {

    SCIFI("Sci-Fi"),
    FANTASY("Fantasy"),
    DRAMA("Drama"),
    ADVENTURE("Adventure");

    private String displayName;

}
