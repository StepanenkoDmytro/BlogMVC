package com.ourapp.MyBlogMVC.service.impl;

import com.ourapp.MyBlogMVC.model.blog_part.СommentToPost;
import com.ourapp.MyBlogMVC.repository.CommentPostService;
import com.ourapp.MyBlogMVC.service.СommentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class СommentPostServiceImpl implements СommentPostService {

    private final CommentPostService commentPostRepository;

    @Autowired
    public СommentPostServiceImpl(CommentPostService commentPostRepository) {
        this.commentPostRepository = commentPostRepository;
    }

    @Override
    public List<СommentToPost> getAllComments() {
        return commentPostRepository.findAll();
    }

    @Override
    public void saveСomment(СommentToPost comment) {
        commentPostRepository.save(comment);
    }

    @Override
    public СommentToPost getСommentById(long id) {
        return commentPostRepository.findById(id).get();
    }

    @Override
    public void deleteСomment(long id) {
        commentPostRepository.deleteById(id);
    }
}
