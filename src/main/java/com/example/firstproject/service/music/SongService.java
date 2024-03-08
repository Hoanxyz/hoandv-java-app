package com.example.firstproject.service.music;

import com.example.firstproject.entity.music.SongEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<SongEntity> getAllSongs();

    Page<SongEntity> getSongs(int page, int size);


    SongEntity createSong(MultipartFile file);

    Optional<SongEntity> getSong(int id);

    Page<SongEntity> searchSong(int page, int size, String text);
}
