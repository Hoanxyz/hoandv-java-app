package com.example.firstproject.rest.music;

import com.example.firstproject.dto.music.SongDto;
import com.example.firstproject.dto.music.SongDataDto;
import com.example.firstproject.entity.music.FavSongEntity;
import com.example.firstproject.entity.music.SongEntity;
import com.example.firstproject.exceptionHandler.ErrorMessage;
import com.example.firstproject.rest.request.SearchSongRequest;
import com.example.firstproject.service.music.FavSongService;
import com.example.firstproject.service.music.impl.SongCollectionServiceImpl;
import com.example.firstproject.service.music.impl.SongServiceImpl;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {
    private final SongServiceImpl songService;
    private final FavSongService favSongService;

    private final SongCollectionServiceImpl songCollectionService;

    public SongController(
            SongServiceImpl songService,
            FavSongService favSongService,
            SongCollectionServiceImpl songCollectionService
    ) {
        this.songService = songService;
        this.favSongService = favSongService;
        this.songCollectionService = songCollectionService;
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

    @GetMapping("get-song-base-64/{id}")
    public ResponseEntity<SongDataDto> songDataBase64(@PathVariable long id) {
        SongEntity song = this.songService.getSong(id);
        if (song != null) {
            SongDataDto songRes = new SongDataDto(song.getName(), Base64.getEncoder().encodeToString(song.getFileData()));
            return new ResponseEntity<>(songRes, HttpStatus.OK);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/search-songs")
    public ResponseEntity<Page<SongDto>> searchSongs(@RequestBody SearchSongRequest request) {
        String text = request.getTextSearch();
        int page = request.getPage();
        int size = request.getSize();
        Page<SongDto> listSongs;
        switch(request.getSearchType()) {
            case "FAV":
                listSongs = this.favSongService.findFavSongsByUser(request.getPage(), request.getSize(), request.getUserId());
                break;
            case "COLLECTION":
                Pageable pageable = PageRequest.of(page, size);
                listSongs = this.songCollectionService.getSongsInCollectionPage(request.getCollectionId(), pageable);
                break;
            case "SEARCH":
                listSongs = this.songService.searchSongByName(page, size, text);
                if (listSongs.isEmpty()) throw new ErrorMessage(1234, "Không có bài hát nào");
                break;
            default:
                listSongs = this.songService.searchSongByName(page, size, "");
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
    public ResponseEntity<Page<SongDto>> getFavSongs(@RequestBody SearchSongRequest request) {
        Page<SongDto> favSongs = this.favSongService.findFavSongsByUser(
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

    @GetMapping("/get-pre-song/{id}")
    public ResponseEntity<Long> findPreSong(@PathVariable("id") long id) {
        try {
            Long idNext = this.songService.findPreSong(id);
            return ResponseEntity.ok(idNext);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
