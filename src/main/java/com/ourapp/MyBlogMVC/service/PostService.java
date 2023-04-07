package com.ourapp.MyBlogMVC.service;

import com.ourapp.MyBlogMVC.model.blog_part.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    void savePost(Post post);
    Post getPost(Long id);
    void deletePost(long id);
    boolean existsPostById(long id);
}
