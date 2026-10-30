package com.krushikaMitra.DTO;
import com.krushikaMitra.Entity.Post;
import lombok.*;
@Builder
@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long id;
    private String title;
    private String description;
    private String district;
    private String imageUrl;
    private String status;
    public Long getId() {
    return id;
}

}