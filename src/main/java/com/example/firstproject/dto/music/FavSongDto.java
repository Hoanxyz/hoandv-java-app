package com.example.firstproject.dto.music;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavSongDto {
    private Long id;
    private String name;
    private Long favId;

    public FavSongDto(long id, String name, long favId) {
        this.id = id;
        this.name = name;
        this.favId = favId;
    }
}
