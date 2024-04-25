package com.example.firstproject.repository.music;

import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.entity.music.SongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<SongEntity, Long> {

    @Query("select new com.example.firstproject.dto.music.SongDto(s.id, s.name) from SongEntity s where lower(s.name) like %:searchTerm% ORDER BY s.createdDate DESC")
    Page<SongDto> findByName(String searchTerm, Pageable pageable);

    @Query("select new com.example.firstproject.dto.music.SongDto(s.id, s.name) from SongEntity s where s.id in :ids ORDER BY s.createdDate DESC")
    Page<SongDto> findByIds(List<Long> ids, Pageable pageable);

    @Query("SELECT s.id FROM SongEntity s WHERE s.id = (SELECT MAX(id) FROM SongEntity)")
    Long findNewestSong();

    @Query("SELECT s.id FROM SongEntity s WHERE s.id = (SELECT MIN(id) FROM SongEntity)")
    Long findLastSong();

    SongEntity findFirstByIdLessThanOrderByIdDesc(Long id);

    SongEntity findFirstByIdGreaterThanOrderByIdAsc(Long id);
}
