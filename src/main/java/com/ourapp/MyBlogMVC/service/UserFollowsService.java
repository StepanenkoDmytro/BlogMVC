package com.ourapp.MyBlogMVC.service;

import com.ourapp.MyBlogMVC.model.blog_part.Post;
import com.ourapp.MyBlogMVC.model.user.User;
import com.ourapp.MyBlogMVC.model.user.UserFollows;

import java.util.List;

public interface UserFollowsService {
    List<User> getFollowListByUserId(User distributor);
    List<User> getAskFollowListByUserId(User distributor);
    List<UserFollows> getSubscribersByDistributor(User distributor);
    void saveFollow(UserFollows follow);
    void setFollowStatusActive(User distributor, User observer);
    void setFollowStatusDeleted(User distributor, User observer);
    boolean checkPermissionToPostByUser(Post post, User user);

}
