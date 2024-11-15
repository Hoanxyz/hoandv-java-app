package com.example.firstproject.entity.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "SONG_COLLECTION")
@Setter
@Getter
public class SongCollectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private long userId;

    private Date createdDate;
}
