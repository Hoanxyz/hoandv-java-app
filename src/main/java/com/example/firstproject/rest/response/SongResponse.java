package com.example.firstproject.rest.response;
import org.springframework.core.io.ByteArrayResource;

public class SongResponse {
    private String name;

    private ByteArrayResource fileData;

    public SongResponse(String name, ByteArrayResource fileData) {
        this.name = name;
        this.fileData = fileData;
    }
}
