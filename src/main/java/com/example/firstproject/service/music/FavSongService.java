package com.example.firstproject.service.music;

import com.example.firstproject.dto.music.FavSongDto;
import com.example.firstproject.entity.music.FavSongEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FavSongService {
    Page<FavSongDto> findFavSongsByUser(int page, int size, long userId);

    List<Long> findFavSongIdsByUserId(long id);

    void createFavSong(long userId, long songId);

    void deleteFavSong(FavSongEntity favSong);
}
