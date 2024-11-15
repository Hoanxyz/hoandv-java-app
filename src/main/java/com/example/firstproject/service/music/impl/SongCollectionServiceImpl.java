package com.example.firstproject.service.music.impl;

import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.entity.music.SongCollectionEntity;
import com.example.firstproject.entity.music.SongCollectionSongEntity;
import com.example.firstproject.repository.music.SongCollectionRepository;
import com.example.firstproject.repository.music.SongCollectionSongRepository;
import com.example.firstproject.service.music.SongCollectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SongCollectionServiceImpl implements SongCollectionService {
    private final SongCollectionRepository songCollectionRepository;
    private final SongCollectionSongRepository songCollectionSongRepository;

    public SongCollectionServiceImpl(
            SongCollectionRepository songCollectionRepository,
            SongCollectionSongRepository songCollectionSongRepository
            ) {
        this.songCollectionRepository = songCollectionRepository;
        this.songCollectionSongRepository = songCollectionSongRepository;
    }

    @Override
    public SongCollectionEntity createCollection(String name, Long userId) {
        SongCollectionEntity newItem = new SongCollectionEntity();
        newItem.setName(name);
        newItem.setUserId(userId);
        newItem.setCreatedDate(new Date());
        this.songCollectionRepository.save(newItem);
        return newItem;
    }

    @Override
    public List<SongCollectionEntity> getListCollectionByUser(Long userId) {
        return this.songCollectionRepository.findByUserIdOrderByCreatedDateDesc(userId);
    }

    @Override
    public SongCollectionSongEntity handleAddSongToCollection(Long collectionId, Long songId) {
        SongCollectionSongEntity oldItem = this.songCollectionSongRepository.findByCollectionIdAndSongId(collectionId, songId);
        if (oldItem != null) {
            this.songCollectionSongRepository.deleteById(oldItem.getId());
            return oldItem;
        } else {
            SongCollectionSongEntity newItem = new SongCollectionSongEntity();
            newItem.setCollectionId(collectionId);
            newItem.setSongId(songId);
            newItem.setCreatedDate(new Date());
            this.songCollectionSongRepository.save(newItem);
            return newItem;
        }
    }

    @Override
    public List<SongCollectionSongEntity> getSongsInCollection(Long collectionId) {
        return this.songCollectionSongRepository.findByCollectionIdOrderByCreatedDateDesc(collectionId);
    }

    @Override
    public Page<SongDto> getSongsInCollectionPage(Long collectionId, Pageable pageable) {
        return this.songCollectionSongRepository.findPageByCollectionId(collectionId, pageable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            this.songCollectionSongRepository.deleteByCollectionId(id);
            this.songCollectionRepository.deleteById(id);
            // If everything is successful, commit the transaction
        } catch (Exception e) {
            // Catch any exceptions and rethrow to trigger rollback
            throw new RuntimeException("Error occurred, rolling back transaction.", e);
        }
    }
}
