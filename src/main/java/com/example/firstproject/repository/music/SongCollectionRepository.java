package com.example.firstproject.repository.music;

import com.example.firstproject.entity.music.SongCollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongCollectionRepository extends JpaRepository<SongCollectionEntity, Long> {
    List<SongCollectionEntity> findByUserIdOrderByCreatedDateDesc(Long id);
}
