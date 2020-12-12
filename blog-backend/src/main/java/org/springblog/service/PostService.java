package org.springblog.service;

import org.springblog.dto.PostDto;
import org.springblog.exception.PostNotFoundException;
import org.springblog.model.Post;
import org.springblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    PostRepository postRepository;




    public Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User user = authService.getCurrentUser().orElseThrow(() ->
                new IllegalArgumentException("No user logged in"));
        post.setUsername(user.getUsername());
        post.setCreatedOn(Instant.now());
        post.setUpdatedOn(Instant.now());
        return post;
    }

    public void createPost(PostDto postDto){
        postRepository.save(mapFromDtoToPost(postDto));
    }

    public List<PostDto> showAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(Collectors.toList());
    }

    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }



    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id)) ;
        return mapFromPostToDto(post);
    }
}
