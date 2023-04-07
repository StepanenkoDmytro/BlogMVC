package com.ourapp.MyBlogMVC.controller;

import com.ourapp.MyBlogMVC.model.user.Status;
import com.ourapp.MyBlogMVC.model.user.UserFollows;
import com.ourapp.MyBlogMVC.model.user.User;
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
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserFollowsService userFollowsService;

    @Autowired
    public UserController(UserService userService, UserFollowsService userFollowsService) {
        this.userService = userService;
        this.userFollowsService = userFollowsService;
    }

    @GetMapping("")
    public String getProfileUserPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        List<User> askFollowsList = userFollowsService.getAskFollowListByUserId(user);
        List<User> followsList = userFollowsService.getFollowListByUserId(user);

        model.addAttribute("user", user);
        model.addAttribute("followList", followsList);
        model.addAttribute("askFollowList", askFollowsList);
        return "user-main";
    }

    @GetMapping("/{id}")
    public String getUserPage(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable(name = "id") long id,
                              Model model) {
        User user = userService.getUserById(id);
        User observer = userService.getUserByUsername(userDetails.getUsername());
        if (user.equals(observer)) {
            return "redirect:/api/v1/user";
        }
        model.addAttribute("user", user);
        model.addAttribute("observer", observer);
        return "user-get";
    }

    @GetMapping("/{id}/edit")
    public String userEditPage(@PathVariable(name = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PostMapping("/{id}/edit")
    public String userEdit(@PathVariable(name = "id") long id,
                           @RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String firstName,
                           @RequestParam String lastName) {
        User user = userService.getUserById(id);

        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userService.saveUser(user);
        return "redirect:/api/v1/user";
    }

    @PostMapping("/follow")
    public String addFriend(@RequestParam(name = "id_observer") long idObserver,
                            @RequestParam(name = "id") long id) {
        User user = userService.getUserById(id);
        User observer = userService.getUserById(idObserver);
        UserFollows userFollows = new UserFollows(user, observer, Status.NOT_ACTIVE);
        userFollowsService.saveFollow(userFollows);
        return String.format("redirect:/api/v1/user/%d",id);
    }

    @PostMapping("/follow/{id}/no")
    public String followAnswerNo(@PathVariable("id") long id,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        User observer = userService.getUserById(id);
        userFollowsService.setFollowStatusDeleted(user, observer);
        return "redirect:/api/v1/user/";
    }

    @PostMapping("/follow/{id}/yes")
    public String followAnswerYes(@PathVariable("id") long id,
                                  @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        User observer = userService.getUserById(id);
        userFollowsService.setFollowStatusActive(user, observer);
        return "redirect:/api/v1/user/";
    }
}
