package com.example.firstproject.rest.music;

import com.example.firstproject.dto.music.FavSongDto;
import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.entity.music.FavSongEntity;
import com.example.firstproject.entity.music.SongEntity;
import com.example.firstproject.rest.request.SearchSongRequest;
import com.example.firstproject.service.music.FavSongService;
import com.example.firstproject.service.music.impl.SongServiceImpl;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {
    private final SongServiceImpl songService;
    private final FavSongService favSongService;

    public SongController(
            SongServiceImpl songService,
            FavSongService favSongService
    ) {
        this.songService = songService;
        this.favSongService = favSongService;
    }


    @PostMapping("/create")
    public ResponseEntity<SongEntity> createSong(@RequestParam("file") MultipartFile file) {
        try {
            SongEntity newSong = this.songService.createSong(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSong);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @GetMapping("get-song/{id}")
    public ResponseEntity<ByteArrayResource> songData(@PathVariable long id) {
        SongEntity song = this.songService.getSong(id);
        if (song != null) {
            ByteArrayResource resource = new ByteArrayResource(song.getFileData());
            HttpHeaders header = new HttpHeaders();
            header.setContentDispositionFormData("attachment", song.getName());
            header.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(resource, header, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/search-songs")
    public ResponseEntity<Page<SongDto>> searchSongs(@RequestBody SearchSongRequest searchSongRequest) {
        String text = searchSongRequest.getTextSearch();
        int page = searchSongRequest.getPage();
        int size = searchSongRequest.getSize();
        List<Long> ids = searchSongRequest.getIds();
        Page<SongDto> listSongs;
        if (ids != null && !ids.isEmpty()) {
            listSongs = this.songService.searchSongByIds(page, size, ids);
        } else {
            if (text != null && !text.trim().isEmpty()) {
                listSongs = this.songService.searchSongByName(page, size, text);
            } else {
                listSongs = this.songService.searchSongByName(page, size, "");
            }
        }
        return ResponseEntity.ok(listSongs);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Long> deleteProduct(@PathVariable("id") long id) {
        this.songService.deleteSong(id);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/add-fav")
    public ResponseEntity<FavSongEntity> addFavSong(@RequestBody FavSongEntity favSong) {
        try {
            this.favSongService.createFavSong(favSong.getUserId(), favSong.getSongId());
            return ResponseEntity.status(HttpStatus.CREATED).body(favSong);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @PostMapping("/get-fav")
    public ResponseEntity<Page<FavSongDto>> getFavSongs(@RequestBody SearchSongRequest request) {
        Page<FavSongDto> favSongs = this.favSongService.findFavSongsByUser(
                request.getPage(), request.getSize(), request.getUserId()
        );
        return ResponseEntity.ok(favSongs);
    }

    @PostMapping("/delete-fav")
    public ResponseEntity<FavSongEntity> deleteFavSong(@RequestBody FavSongEntity favSong) {
        try {
            this.favSongService.deleteFavSong(favSong);
            return ResponseEntity.ok(favSong);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @GetMapping("/get-fav-ids")
    public ResponseEntity<List<Long>> findFavSongIdsByUserId(@RequestParam("id") long id) {
        try {
            List<Long> listId = this.favSongService.findFavSongIdsByUserId(id);
            return ResponseEntity.ok(listId);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @GetMapping("/get-newest-song")
    public ResponseEntity<Long> findNewestSong() {
        try {
            Long id = this.songService.findNewestSong();
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @GetMapping("/get-next-song/{id}")
    public ResponseEntity<Long> findNextSong(@PathVariable("id") long id) {
        try {
            Long idNext = this.songService.findNextSong(id);
            return ResponseEntity.ok(idNext);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
