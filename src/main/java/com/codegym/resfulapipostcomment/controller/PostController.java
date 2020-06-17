package com.codegym.resfulapipostcomment.controller;

import com.codegym.resfulapipostcomment.model.Post;
import com.codegym.resfulapipostcomment.repository.PostRepository;
import com.codegym.resfulapipostcomment.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

// @RestController: Trả về dữ liệu dạng JSON
@RestController
@CrossOrigin("http://localhost:4200")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<Page<Post>> getAllPosts(Pageable pageable){
        Page<Post> posts = postService.findAll(pageable);
        if (posts.getTotalElements() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable Long id ) {
        Optional<Post> post = postService.findById(id);
        if (!post.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    // Khi xây dựng API , các thông tin từ phía client gửi lên server
    //sẽ nằm trong body và cũng ở dạng JSON.
    //@RequestBody:  Tương ứng với phần body của Request.
    //Cần chuyển JSON thành object nào bằng cách dung @RequestBody.
    @PostMapping("/posts")
    public ResponseEntity<Void> createPosts(@RequestBody Post post, UriComponentsBuilder builder){
        postService.save(post);
        HttpHeaders headers = new HttpHeaders();
        //Chuyển huướng đến 1 URL bên ngoài .
        headers.setLocation(builder.path("/post/{id}").buildAndExpand(post.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> updatePost(@PathVariable Long id, @RequestBody Post post){
        Optional<Post> postOptional = postService.findById(id);
        if (!postOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postOptional.get().setTitle(post.getTitle());
        postOptional.get().setDescription(post.getDescription());
        postOptional.get().setContent(post.getContent());

        postService.save(post);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> deletePost(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        if (!post.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postService.remove(id);
        //NO_CONTENT: Đã thành công nhưng k có nội dung nào để phản hồi về.
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
