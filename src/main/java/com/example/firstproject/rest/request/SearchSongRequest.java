package com.example.firstproject.rest.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchSongRequest {
    private int page;

    private int size;

    private String textSearch;

    private List<Long> ids;

    private long userId;
}
