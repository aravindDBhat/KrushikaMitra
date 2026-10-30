package com.krushikaMitra.Service;
import java.util.List;

import com.krushikaMitra.DTO.PostRequest;
public interface PostService {
    String createPost(PostRequest post);
    PostRequest getPostById(Long id);
    String updatePost(PostRequest post);
    List<PostRequest> getAllPosts();
    List<PostRequest> getMyPosts();
    String deletePost(Long id);
} 