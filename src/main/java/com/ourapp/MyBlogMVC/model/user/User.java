package com.ourapp.MyBlogMVC.model.user;

import com.ourapp.MyBlogMVC.model.BaseEntity;
import com.ourapp.MyBlogMVC.model.blog_part.Post;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "username")
    @NotEmpty(message = "User's name cannot be empty.")
    @Size(min = 3, max = 100,
            message = "length should be between 3 to 100")
    private String username;

    @Column(name = "first_name")
    @NotBlank(message = "User's first name cannot be empty.")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "User's last name cannot be empty.")
    private String lastName;

    @Column(name = "email")
    @NotBlank(message = "User's email cannot be empty.")
    private String email;

    @Column(name = "password")
    @NotBlank(message = "User's password cannot be empty.")
    @Size(min = 7, message = "Password should be min 7 symbols")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.REFRESH, CascadeType.MERGE},
            mappedBy = "author")
    private List<Post> posts;

    public User() {
    }

    public void addPostToUser(Post post) {
        if (posts == null) {
            posts = new ArrayList<>();
        }
        posts.add(post);
        post.setAuthor(this);
    }

    public void addRoleToUser(Role role){
        if(roles == null){
            roles = new ArrayList<>();
        }
        roles.add(role);
    }
    public void removeRoleFromUser(Role role){
        roles.remove(role);
    }

    public @NotEmpty(message = "User's name cannot be empty.") @Size(min = 3, max = 100,
            message = "length should be between 3 to 100") String getUsername() {
        return this.username;
    }

    public @NotBlank(message = "User's first name cannot be empty.") String getFirstName() {
        return this.firstName;
    }

    public @NotBlank(message = "User's last name cannot be empty.") String getLastName() {
        return this.lastName;
    }

    public @NotBlank(message = "User's email cannot be empty.") String getEmail() {
        return this.email;
    }

    public @NotBlank(message = "User's password cannot be empty.") @Size(min = 7, message = "Password should be min 7 symbols") String getPassword() {
        return this.password;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public List<Post> getPosts() {
        return this.posts;
    }

    public void setUsername(@NotEmpty(message = "User's name cannot be empty.") @Size(min = 3, max = 100,
            message = "length should be between 3 to 100") String username) {
        this.username = username;
    }

    public void setFirstName(@NotBlank(message = "User's first name cannot be empty.") String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(@NotBlank(message = "User's last name cannot be empty.") String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(@NotBlank(message = "User's email cannot be empty.") String email) {
        this.email = email;
    }

    public void setPassword(@NotBlank(message = "User's password cannot be empty.") @Size(min = 7, message = "Password should be min 7 symbols") String password) {
        this.password = password;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$roles = this.getRoles();
        final Object other$roles = other.getRoles();
        if (this$roles == null ? other$roles != null : !this$roles.equals(other$roles)) return false;
        final Object this$posts = this.getPosts();
        final Object other$posts = other.getPosts();
        if (this$posts == null ? other$posts != null : !this$posts.equals(other$posts)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $roles = this.getRoles();
        result = result * PRIME + ($roles == null ? 43 : $roles.hashCode());
        final Object $posts = this.getPosts();
        result = result * PRIME + ($posts == null ? 43 : $posts.hashCode());
        return result;
    }

    public String toString() {
        return "User(username=" + this.getUsername() +
                ", email=" + this.getEmail() +
                ")";
    }
}
