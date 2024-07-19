package com.example.firstproject.feignClients.test.services;

import com.example.firstproject.feignClients.test.responses.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test-fiegn")
@RequiredArgsConstructor
public class TestController {
    private final JSONPlaceHolderClient jsonPlaceHolderClient;

    @GetMapping("/get-posts")
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = jsonPlaceHolderClient.getPosts();
        return ResponseEntity.ok(posts);
    }
}
