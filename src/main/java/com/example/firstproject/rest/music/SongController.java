package com.example.firstproject.rest.music;

import com.example.firstproject.entity.music.SongEntity;
import com.example.firstproject.repository.music.SongRepository;
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
import java.util.Optional;

@RestController
@RequestMapping("/song")
public class SongController {
    private final SongServiceImpl songService;
    private final SongRepository songRepository;

    public SongController(
            SongServiceImpl songService,
            SongRepository songRepository
    ) {
        this.songService = songService;
        this.songRepository = songRepository;
    }

    @GetMapping("/all-songs")
    public ResponseEntity<List<SongEntity>> getAllSongs() {
        List<SongEntity> listSongs = this.songService.getAllSongs();
        return ResponseEntity.ok(listSongs);
    }


    @PostMapping("/create")
    public ResponseEntity<SongEntity> createSong(@RequestParam("file") MultipartFile file) {
        try {
            SongEntity newSong = this.songService.createSong(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(newSong);
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadSong(@PathVariable("id") int id) {
        Optional<SongEntity> optionalSong = this.songService.getSong(id);
        if (optionalSong.isPresent()) {
            SongEntity song = optionalSong.get();
            HttpHeaders header = new HttpHeaders();
            header.setContentDispositionFormData("attachment", song.getName());
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            return new ResponseEntity<>(song.getFileData(), header, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("get-song/{id}")
    public ResponseEntity<ByteArrayResource> getMp3Content(@PathVariable int id) {
        SongEntity song = this.songRepository.findById(id).orElse(null);
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

    @GetMapping("/get-songs")
    public ResponseEntity<Page<SongEntity>> getAllSongs(@RequestParam("page") int page,
                                                        @RequestParam("size") int size) {
        Page<SongEntity> listSongs = this.songService.getSongs(page, size);
        return ResponseEntity.ok(listSongs);
    }

    @GetMapping("/search-songs")
    public ResponseEntity<Page<SongEntity>> searchSongs(@RequestParam("page") int page,
                                                        @RequestParam("size") int size,
                                                        @RequestParam("text") String text) {
        Page<SongEntity> listSongs = this.songService.searchSong(page, size, text);
        return ResponseEntity.ok(listSongs);
    }
}
