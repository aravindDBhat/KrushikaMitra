package com.krushikaMitra.Repository;
import com.krushikaMitra.Entity.User;
import com.krushikaMitra.Entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserEmail(String email);
}