package com.example.firstproject.service.music.impl;

import com.example.firstproject.dto.music.FavSongDto;
import com.example.firstproject.entity.music.FavSongEntity;
import com.example.firstproject.repository.music.FavSongRepository;
import com.example.firstproject.service.music.FavSongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FavSongServiceImpl implements FavSongService {
    private final FavSongRepository favSongRepository;

    public FavSongServiceImpl(FavSongRepository favSongRepository) {
        this.favSongRepository = favSongRepository;
    }

    @Override
    public Page<FavSongDto> findFavSongsByUser(int page, int size, long userId) {
        Pageable pageable = PageRequest.of(page, size);
        return this.favSongRepository.findFavSongsByUserId(userId, pageable);
    }

    @Override
    public List<Long> findFavSongIdsByUserId(long id) {
        try {
            return this.favSongRepository.findFavSongIdsByUserId(id);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void createFavSong(long userId, long songId) {
        FavSongEntity newFavSong = new FavSongEntity();
        newFavSong.setUserId(userId);
        newFavSong.setSongId(songId);
        newFavSong.setCreatedDate(new Date());
        this.favSongRepository.save(newFavSong);
    }

    @Override
    public void deleteFavSong(FavSongEntity favSong) {
        Long favSongId = this.favSongRepository.findFavSongEntityId(favSong.getUserId(), favSong.getSongId());
        this.favSongRepository.deleteById(favSongId);
    }
}
