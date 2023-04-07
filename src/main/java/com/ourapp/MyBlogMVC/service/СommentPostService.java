package com.ourapp.MyBlogMVC.service;

import com.ourapp.MyBlogMVC.model.blog_part.СommentToPost;

import java.util.List;

public interface СommentPostService {
    List<СommentToPost> getAllComments();

    void saveСomment(СommentToPost comment);
    СommentToPost getСommentById(long id);
    void deleteСomment(long id);
}
