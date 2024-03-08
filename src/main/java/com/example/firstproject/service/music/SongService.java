package com.example.firstproject.service.music;

import com.example.firstproject.entity.music.SongEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<SongEntity> getAllSongs();

    SongEntity createSong(MultipartFile file);

    Optional<SongEntity> getSong(int id);
}
