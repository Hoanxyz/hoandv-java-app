package com.example.firstproject.service.music;

import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.entity.music.FavSongEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FavSongService {
    Page<SongDto> findFavSongsByUser(int page, int size, long userId);

    List<Long> findFavSongIdsByUserId(long id);

    void createFavSong(long userId, long songId);

    void deleteFavSong(FavSongEntity favSong);
}
