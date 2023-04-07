package com.ourapp.MyBlogMVC.controller;

import com.ourapp.MyBlogMVC.model.blog_part.Post;
import com.ourapp.MyBlogMVC.model.blog_part.СommentToPost;
import com.ourapp.MyBlogMVC.model.user.User;
import com.ourapp.MyBlogMVC.repository.CommentPostService;
import com.ourapp.MyBlogMVC.repository.PostRepository;
import com.ourapp.MyBlogMVC.service.PostService;
import com.ourapp.MyBlogMVC.service.UserFollowsService;
import com.ourapp.MyBlogMVC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class BlogController {

    private final PostService postService;
    private final CommentPostService commentPostService;
    private final UserService userService;
    private final UserFollowsService userFollowsService;
    private final PostRepository postRepository;

    @Autowired
    public BlogController(PostService postService, CommentPostService commentPostService, UserService userService, UserFollowsService userFollowsService,
                          PostRepository postRepository) {
        this.postService = postService;
        this.commentPostService = commentPostService;
        this.userService = userService;
        this.userFollowsService = userFollowsService;
        this.postRepository = postRepository;
    }

    @GetMapping("/blog")
    public String main(Model model) {
        List<Post> allPosts = postService.getAllPosts();
        model.addAttribute("allPosts", allPosts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String addNewPost(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String savePost(@AuthenticationPrincipal UserDetails userDetails,
                           @RequestParam String name,
                           @RequestParam String anons,
                           @RequestParam String fullText,
                           Model model) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        Post post = new Post(name, anons, fullText);
        user.addPostToUser(post);
        postService.savePost(post);
        return "redirect:/api/v1/blog";
    }

    @GetMapping("/blog/{id}")
    public String getPost(@AuthenticationPrincipal UserDetails userDetails,
                          @PathVariable(value = "id") long id,
                          Model model) {
        if (!postService.existsPostById(id)) {
            return "redirect:/api/v1/blog";//переписати на свою 404 сторінку
        }
        Post post = postService.getPost(id);
        User observer = userService.getUserByUsername(userDetails.getUsername());
        if (!userFollowsService.checkPermissionToPostByUser(post, observer)) {
            System.out.println("You don't have permissions");
            return "redirect:/api/v1/blog";
        }
        List<СommentToPost> comments = post.getComments();

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String getEditPost(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable(value = "id") long id, Model model) {
        if (!postService.existsPostById(id)) {
            return "redirect:/api/v1/blog";//переписати на свою 404 сторінку
        }
        Post post = postService.getPost(id);
        User user = post.getAuthor();
        if (!userDetails.getUsername().equals(user.getUsername())) {
            System.out.println("You are not author of this post");
            return "redirect:/api/v1/blog";
        }
        model.addAttribute("post", post);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String updatePost(@PathVariable(name = "id") long id,
                             @RequestParam String title,
                             @RequestParam String anons,
                             @RequestParam String fullText,
                             Model model) {

        Post post = postService.getPost(id);
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(fullText);
        postService.savePost(post);
        return "redirect:/api/v1/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String removePost(@PathVariable(name = "id") long id, Model model) {
        postService.deletePost(id);
        return "redirect:/api/v1/blog";
    }

    @PostMapping("/blog/addcomment")
    public String postAddComment(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam(name = "id_post") long id,
                                 @RequestParam String comment,
                                 Model model) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        Post post = postService.getPost(id);

        post.addCommentToPost(user, comment);
        postService.savePost(post);
        return String.format("redirect:/api/v1/blog/%d",id);
    }

    @PostMapping("/blog/delcomment")
    public String postDelComment(@RequestParam(name = "comment_id") long id,
                                 @RequestParam(name = "post_id") long id_post,
                                 Model model) {
        commentPostService.deleteById(id);
        return String.format("redirect:/api/v1/blog/%d",id_post);
    }
}
