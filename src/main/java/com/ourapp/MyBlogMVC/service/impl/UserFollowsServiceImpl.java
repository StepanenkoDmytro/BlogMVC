package com.ourapp.MyBlogMVC.service.impl;

import com.ourapp.MyBlogMVC.model.blog_part.Post;
import com.ourapp.MyBlogMVC.model.user.Status;
import com.ourapp.MyBlogMVC.model.user.User;
import com.ourapp.MyBlogMVC.model.user.UserFollows;
import com.ourapp.MyBlogMVC.repository.UserFollowsRepository;
import com.ourapp.MyBlogMVC.service.UserFollowsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserFollowsServiceImpl implements UserFollowsService {
    private final UserFollowsRepository userFollowsRepository;

    public UserFollowsServiceImpl(UserFollowsRepository userFollowsRepository) {
        this.userFollowsRepository = userFollowsRepository;
    }

    @Override
    public List<UserFollows> getSubscribersByDistributor(User distributor) {
        return userFollowsRepository.findAllByDistributor(distributor);
    }

    @Override
    public void saveFollow(UserFollows follow) {
        userFollowsRepository.save(follow);
    }

    @Override
    public void setFollowStatusActive(User distributor, User observer) {
        List<UserFollows> userFollows = getSubscribersByDistributor(distributor);
        for (UserFollows u : userFollows) {
            if (Objects.equals(u.getSubscriber().getId(), observer.getId())) {
                u.setStatus(Status.ACTIVE);
                saveFollow(u);
            }
        }
    }

    @Override
    public void setFollowStatusDeleted(User distributor, User observer) {
        List<UserFollows> userFollows = getSubscribersByDistributor(distributor);
        for (UserFollows u : userFollows) {
            if (Objects.equals(u.getSubscriber().getId(), observer.getId())) {
                u.setStatus(Status.DELETED);
                saveFollow(u);
            }
        }
    }

    @Override
    public boolean checkPermissionToPostByUser(Post post, User user) {
        User author = post.getAuthor();
        List<User> followAuthor = getFollowListByUserId(author);
        followAuthor.add(author);
        return followAuthor.contains(user);
    }

    @Override
    public List<User> getFollowListByUserId(User distributor) {
        List<UserFollows> list = userFollowsRepository.findAllByDistributor(distributor);

        List<User> followList = new ArrayList<>();
        for (UserFollows u : list) {
            if (u.getStatus() == Status.ACTIVE)
                followList.add(u.getSubscriber());
        }
        return followList;
    }
    @Override
    public List<User> getAskFollowListByUserId(User distributor) {
        List<UserFollows> list = userFollowsRepository.findAllByDistributor(distributor);

        List<User> askFollowList = new ArrayList<>();
        for (UserFollows uf : list) {
            if (uf.getStatus() == Status.NOT_ACTIVE)
                askFollowList.add(uf.getSubscriber());
        }
        return askFollowList;
    }
}
