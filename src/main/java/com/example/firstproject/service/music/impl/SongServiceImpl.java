package com.example.firstproject.service.music.impl;

import com.example.firstproject.entity.music.SongEntity;
import com.example.firstproject.repository.music.SongRepository;
import com.example.firstproject.service.music.SongService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        Pageable pageAble = PageRequest.of(page, size);
        return this.songRepository.findAll(pageAble);
    }

    @Override
    public SongEntity createSong(MultipartFile file) {
        try {
            byte[] fileData = file.getBytes();
            SongEntity song = new SongEntity();
            song.setName(file.getOriginalFilename());
            song.setFileData(fileData);
            return this.songRepository.save(song);
        } catch (IOException e) {
            throw new IllegalArgumentException("Co loi khi upload file: " + e);
        }
    }

    @Override
    public Optional<SongEntity> getSong(int id) {
        return this.songRepository.findById(id);
    }

    @Override
    public Page<SongEntity> searchSong(int page, int size, String text) {
        Pageable pageable = PageRequest.of(page, size);
        return this.songRepository.findByNameContainingIgnoreCase(text, pageable);
    }
}
