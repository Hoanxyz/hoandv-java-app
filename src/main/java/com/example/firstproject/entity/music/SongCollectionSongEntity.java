package com.example.firstproject.entity.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "SONG_COLLECTION_SONG")
@Setter
@Getter
public class SongCollectionSongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long songId;

    private long collectionId;

    private Date createdDate;
}
