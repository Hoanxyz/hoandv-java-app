package com.example.firstproject.repository.music;

import com.example.firstproject.entity.music.SongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Integer> {
    Page<SongEntity> findByNameContainingIgnoreCase(String searchTerm, Pageable pageable);
}
