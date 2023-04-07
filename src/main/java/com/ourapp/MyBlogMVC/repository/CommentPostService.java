package com.ourapp.MyBlogMVC.repository;

import com.ourapp.MyBlogMVC.model.blog_part.СommentToPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPostService extends JpaRepository<СommentToPost,Long> {
}
