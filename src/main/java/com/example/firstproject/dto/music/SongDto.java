package com.example.firstproject.dto.music;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDto {
    private Long id;
    private String name;

    public SongDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
