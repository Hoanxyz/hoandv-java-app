package com.example.firstproject.service.music;

import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.entity.music.SongEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SongService {
    List<SongEntity> getAllSongs();

    Page<SongEntity> getSongs(int page, int size);


    SongEntity createSong(MultipartFile file);

    SongEntity getSong(long id);

    Page<SongDto> searchSongByName(int page, int size, String text);

    Page<SongDto> searchSongByIds(int page, int size, List<Long> ids);

    void deleteSong(long id);

    Long findNewestSong();

    Long findNextSong(long id);
}
