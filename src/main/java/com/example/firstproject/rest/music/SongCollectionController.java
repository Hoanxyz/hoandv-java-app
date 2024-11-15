package com.example.firstproject.rest.music;

import com.example.firstproject.dto.music.SongCollectionDto;
import com.example.firstproject.entity.music.SongCollectionEntity;
import com.example.firstproject.entity.music.SongCollectionSongEntity;
import com.example.firstproject.service.music.impl.SongCollectionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("song-collection")
public class SongCollectionController {
    private final SongCollectionServiceImpl songCollectionService;
    public SongCollectionController(
            SongCollectionServiceImpl songCollectionService
    ) {
        this.songCollectionService = songCollectionService;
    }

    @PostMapping("create")
    public ResponseEntity<SongCollectionEntity> create(@RequestBody SongCollectionEntity item) {
        try {
            SongCollectionEntity newItem = this.songCollectionService.createCollection(item.getName(), item.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @PostMapping("handle-add-to-collection")
    public ResponseEntity<SongCollectionSongEntity> addToCollection(@RequestBody SongCollectionSongEntity item) {
        try {
            SongCollectionSongEntity newItem = this.songCollectionService.handleAddSongToCollection(item.getCollectionId(), item.getSongId());
            return ResponseEntity.status(HttpStatus.OK).body(newItem);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @GetMapping("list-of-user/{id}")
    public ResponseEntity<List<SongCollectionDto>> listOfUser(@PathVariable Long id) {
        try {
            List<SongCollectionEntity> listCollections = this.songCollectionService.getListCollectionByUser(id);
            List<SongCollectionDto> listCollectionsWithSongs = new ArrayList<>();
            listCollections.forEach((i) -> {
                SongCollectionDto item = new SongCollectionDto(i.getId(), i.getName(), this.songCollectionService.getSongsInCollection(i.getId()));
                listCollectionsWithSongs.add(item);
            });
            return ResponseEntity.status(HttpStatus.OK).body(listCollectionsWithSongs);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable("id") long id) {
        this.songCollectionService.delete(id);
        return ResponseEntity.ok(id);
    }
}
