package com.example.firstproject.service.music;

import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.entity.music.SongCollectionEntity;
import com.example.firstproject.entity.music.SongCollectionSongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongCollectionService {
    /**
     *
     * @param name String
     * @param userId Long
     * @return SongCollectionEntity
     */
    SongCollectionEntity createCollection(String name, Long userId);

    /**
     *
     * @param userId Long
     * @return List<SongCollectionEntity>
     */
    List<SongCollectionEntity> getListCollectionByUser(Long userId);

    SongCollectionSongEntity handleAddSongToCollection(Long collectionId, Long songId);

    List<SongCollectionSongEntity> getSongsInCollection(Long collectionId);


    Page<SongDto> getSongsInCollectionPage(Long collectionId, Pageable pageable);

    /**
     *
     * @param id Long
     */
    void delete(Long id);
}
