package com.example.firstproject.repository.music;

import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.entity.music.SongCollectionSongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongCollectionSongRepository extends JpaRepository<SongCollectionSongEntity, Long> {
    List<SongCollectionSongEntity> findByCollectionIdOrderByCreatedDateDesc(Long id);

    @Query("select new com.example.firstproject.dto.music.SongDto(s.id, s.name)" +
            "from SongEntity s join SongCollectionSongEntity c on s.id = c.songId where c.collectionId = :collectionId ORDER BY c.createdDate DESC")
    Page<SongDto> findPageByCollectionId(Long collectionId, Pageable pageable);

    SongCollectionSongEntity findByCollectionIdAndSongId(Long collectionId, Long songId);

    void deleteByCollectionId(Long id);
}
