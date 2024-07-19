package com.example.firstproject.repository.music;

import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.entity.music.FavSongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavSongRepository extends JpaRepository<FavSongEntity, Long> {

    @Query("select new com.example.firstproject.dto.music.SongDto(s.id, s.name)" +
            "from FavSongEntity fs " +
            "left join SongEntity s on s.id = fs.songId " +
            "where fs.userId = :id ORDER BY fs.createdDate DESC")
    Page<SongDto> findFavSongsByUserId(long id, Pageable pageable);

    @Query("select fs.songId from FavSongEntity fs where fs.userId = :id ORDER BY fs.createdDate DESC")
    List<Long> findFavSongIdsByUserId(long id);

    @Query("select fs.id from FavSongEntity fs where fs.userId = :userId and fs.songId = :songId")
    Long findFavSongEntityId(long userId, long songId);

    void deleteAllBySongId(Long songId);
}
