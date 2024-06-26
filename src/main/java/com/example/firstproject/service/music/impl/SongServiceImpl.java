package com.example.firstproject.service.music.impl;

import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.entity.music.SongEntity;
import com.example.firstproject.repository.music.SongRepository;
import com.example.firstproject.service.music.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<SongEntity> getAllSongs() {
        return this.songRepository.findAll();
    }

    @Override
    public Page<SongEntity> getSongs(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageAble = PageRequest.of(page, size, sort);
        return this.songRepository.findAll(pageAble);
    }

    @Override
    public SongEntity createSong(MultipartFile file) {
        try {
            byte[] fileData = file.getBytes();
            SongEntity song = new SongEntity();
            song.setCreatedDate(new Date());
            song.setName(file.getOriginalFilename());
            song.setFileData(fileData);
            return this.songRepository.save(song);
        } catch (IOException e) {
            throw new IllegalArgumentException("Co loi khi upload file: " + e);
        }
    }

    @Override
    public SongEntity getSong(long id) {
        return this.songRepository.findById(id).orElse(null);
    }

    @Override
    public Page<SongDto> searchSongByName(int page, int size, String text) {
        Pageable pageable = PageRequest.of(page, size);
        return this.songRepository.findByName(text, pageable);
    }

    @Override
    public Page<SongDto> searchSongByIds(int page, int size, List<Long> ids) {
        Pageable pageable = PageRequest.of(page, size);
        return this.songRepository.findByIds(ids, pageable);
    }


    @Override
    public void deleteSong(long id) {
        SongEntity song = this.songRepository.findById(id).orElse(null);
        if (song != null) {
            this.songRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Bai hat khong ton tai");
        }
    }

    @Override
    public Long findNewestSong() {
        return this.songRepository.findNewestSong();
    }

    @Override
    public Long findNextSong(long id) {
        SongEntity nextSong = songRepository.findFirstByIdLessThanOrderByIdDesc(id);
        if (nextSong != null) {
            return nextSong.getId();
        }
        return this.songRepository.findNewestSong();
    }
}
