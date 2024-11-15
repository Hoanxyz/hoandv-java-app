package com.example.firstproject.entity.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "SONG_COLLECTION_SONG_DATA_VIEW")
@Setter
@Getter
public class SongCollectionSongView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long songId;

    private String songName;

    private long collectionId;
}
