package com.example.firstproject.dto.music;

import lombok.Data;

@Data
public class SongDataDto {
    private String name;

    private String dataBase64;

    public SongDataDto(
            String name,
            String dataBase64
    ) {
        this.name = name;
        this.dataBase64 = dataBase64;
    }
}
