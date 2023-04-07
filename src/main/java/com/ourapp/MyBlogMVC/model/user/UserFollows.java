package com.ourapp.MyBlogMVC.model.user;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user_followers")
@Data
public class UserFollows {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


//    @Column(name = "distributor_id")
    @OneToOne
    @JoinColumn(name = "distributor_id")
    private User distributor;


//    @Column(name = "subscriber_id")
    @OneToOne
    @JoinColumn(name = "subscriber_id")
    private User subscriber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public UserFollows() {
    }

    public UserFollows(User distributor, User subscriber, Status status) {
        this.distributor = distributor;
        this.subscriber = subscriber;
        this.status = status;
    }

}
