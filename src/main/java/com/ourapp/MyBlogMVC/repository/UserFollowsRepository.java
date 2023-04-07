package com.ourapp.MyBlogMVC.repository;

import com.ourapp.MyBlogMVC.model.user.User;
import com.ourapp.MyBlogMVC.model.user.UserFollows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserFollowsRepository extends JpaRepository<UserFollows, Long> {
    List<UserFollows> findAllByDistributor(User distributor);
    Set<Long> findBySubscriber(Long subscriber);
}
