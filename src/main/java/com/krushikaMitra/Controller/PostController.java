package com.krushikaMitra.Controller;
import com.krushikaMitra.DTO.PostRequest;
import com.krushikaMitra.Service.PostService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping
    public String createPost(@RequestBody PostRequest postRequest) {
        postService.createPost(postRequest);
        return "Post created successfully";
    }
    @GetMapping
    public List<PostRequest> getAllPosts() {
        return postService.getAllPosts();
    }
    @GetMapping("/MyPosts")
    public List<PostRequest> getMyPosts() {
        return postService.getMyPosts();
    }
    @GetMapping("/{id}")
    public PostRequest getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }
    @PutMapping
    public String updatePost(@RequestBody PostRequest postRequest) {
        postService.updatePost(postRequest);
        return "Post updated successfully";
    }
    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}