package com.example.firstproject.entity.music;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "fav_song")
@Setter
@Getter
public class FavSongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private long songId;

    private Date createdDate;
}
