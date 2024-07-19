package com.example.firstproject.feignClients.test.responses;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {
    long userId;
    long id;
    String title;
    String body;
}
