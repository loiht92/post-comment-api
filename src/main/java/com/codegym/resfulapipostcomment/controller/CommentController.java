package com.codegym.resfulapipostcomment.controller;

import com.codegym.resfulapipostcomment.ResourceNotFoundException;
import com.codegym.resfulapipostcomment.model.AbcRequest;
import com.codegym.resfulapipostcomment.model.Comment;
import com.codegym.resfulapipostcomment.model.Post;
import com.codegym.resfulapipostcomment.service.CommentService;
import com.codegym.resfulapipostcomment.service.PostService;
import com.codegym.resfulapipostcomment.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @GetMapping("/comments")
    public ResponseEntity<Page<Comment>> getAllComment(Pageable pageable){
        Page<Comment> comments = commentService.findAll( pageable);
        if (comments.getTotalElements() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @RequestMapping(value = "/comments/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<Comment>> getCommentById(@PathVariable Long id ) {
        Optional<Comment> comment = commentService.findById(id);
        if (!comment.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<Page<Comment>> getAllCommentByPostId(@PathVariable(value = "postId") Long postId, Pageable pageable){
        Page<Comment> comments = commentService.findByPostId(postId, pageable);
        if (comments.getTotalElements() == 0){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Object> createComments(@PathVariable(value = "postId") Long postId, @RequestBody Comment comment){
        ResponseObject<Object> response = new ResponseObject<>();
        try {
            Post post = postService.findById(postId)
                    .orElseThrow(() -> new ResourceNotFoundException("POST NOT FOUND"));
            comment.setPost(post);
            commentService.save(comment);
            response.setData(post);
        } catch (ResourceNotFoundException e) {
            response.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.CREATED);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<Object> updateComment(
                                                @PathVariable(value = "commentId") Long commentId,
                                                @RequestBody AbcRequest request){
        ResponseObject<Object> response = new ResponseObject<>();
        try {
            Comment comment = commentService.findById(commentId)
                    .orElseThrow(() -> new ResourceNotFoundException("COMMENT NOT FOUND"));
            comment.setText(request.getText());
            commentService.save(comment);
            response.setData(comment);
        } catch (ResourceNotFoundException e) {
            response.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Object> deleteComment(@PathVariable(value = "commentId") Long commentId){
        ResponseObject<Object> response = new ResponseObject<>();
        try {
            Comment comment = commentService.findById(commentId)
                    .orElseThrow(() -> new ResourceNotFoundException("COMMENT NOT FOUND"));
            commentService.remove(commentId);
            response.setData(comment);
        } catch (ResourceNotFoundException e) {
            response.setMessage(e.getMessage());
        }

        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
