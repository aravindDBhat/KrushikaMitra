package com.krushikaMitra.Service;

import com.krushikaMitra.DTO.PostRequest;
import com.krushikaMitra.Entity.Post;
import com.krushikaMitra.Entity.User;
import com.krushikaMitra.Repository.PostRepository;
import com.krushikaMitra.Repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.krushikaMitra.Entity.Role;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostServiceImpl(UserRepository userRepository,
                           PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public String createPost(PostRequest postRequest) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();

        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setDistrict(postRequest.getDistrict());
        post.setImageUrl(postRequest.getImageUrl());

        post.setStatus("OPEN");
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

       postRepository.save(post);
        return"Post created successfully";
    }

    @Override
    public PostRequest getPostById(Long id) {

        Post post = postRepository.findById(id)
                .orElse(null);

        if (post == null) {
            return null;
        }

        return new PostRequest(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getDistrict(),
                post.getImageUrl(),
                post.getStatus()
        );
    }

    @Override
    public String updatePost(PostRequest postRequest) {
          String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        Post post = postRepository.findById(postRequest.getId())
                .orElse(null);
User loggedInUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
       

if (loggedInUser.getRole() != Role.ADMIN &&
            !post.getUser().getEmail().equals(email)) {

        throw new RuntimeException("You are not authorized to update this post");
    }

        Post existingPost = postRepository.findById(postRequest.getId())
                .orElse(null);

        if (existingPost != null) {
            existingPost.setTitle(postRequest.getTitle());
            existingPost.setDescription(postRequest.getDescription());
            existingPost.setDistrict(postRequest.getDistrict());
            existingPost.setImageUrl(postRequest.getImageUrl());

            postRepository.save(existingPost);
            return "Post updated successfully";
        }
        return "Post not found";
    }

    @Override
    public List<PostRequest> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            return List.of();
        }

        return posts.stream()
                .map(post -> new PostRequest(
                        post.getId(),
                        post.getTitle(),
                        post.getDescription(),
                        post.getDistrict(),
                        post.getImageUrl(),
                        post.getStatus()
                ))
                .toList();
    }

    @Override
    public List<PostRequest> getMyPosts() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        List<Post> posts = postRepository.findByUserEmail(email);
        if (posts.isEmpty()) {
            return List.of();
        }

        return posts.stream()
                .map(post -> new PostRequest(
                        post.getId(),
                        post.getTitle(),
                        post.getDescription(),
                        post.getDistrict(),
                        post.getImageUrl(),
                        post.getStatus()
                ))
                .toList();
    }

    @Override
    public String deletePost(Long id) {
          String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
          Post post = postRepository.findById(id)
                .orElse(null);
User loggedInUser = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
      

if (loggedInUser.getRole() != Role.ADMIN &&
            !post.getUser().getEmail().equals(email)) {

        return "You are not authorized to delete this post";
    }
        postRepository.deleteById(id);
        return "Post deleted successfully";
    }
}