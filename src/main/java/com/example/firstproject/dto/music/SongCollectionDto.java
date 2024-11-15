package com.example.firstproject.dto.music;

import com.example.firstproject.entity.music.SongCollectionSongEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SongCollectionDto {
    private Long id;
    private String name;
    private List<SongCollectionSongEntity> songs;

    public SongCollectionDto(
        Long id,
        String name,
        List<SongCollectionSongEntity> songs
    ) {
        this.id = id;
        this.name = name;
        this.songs = songs;
    }
}
