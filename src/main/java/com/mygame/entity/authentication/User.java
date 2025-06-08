package com.mygame.entity.authentication;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mygame.entity.BaseEntity;
import com.mygame.entity.address.Address;
import com.mygame.entity.cart.Cart;
import com.mygame.entity.chat.Message;
import com.mygame.entity.chat.Room;
import com.mygame.entity.client.Preorder;
import com.mygame.entity.client.Wish;
import com.mygame.entity.general.Notification;
import com.mygame.entity.order.Order;
import com.mygame.entity.review.Review;
import com.mygame.entity.reward.RewardLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "gender", nullable = false, columnDefinition = "CHAR")
    private String gender;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false, unique = true)
    private Address address;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status", nullable = false, columnDefinition = "TINYINT")
    private Integer status;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false)
    )
    private Set<Role> roles = new HashSet<>();

//    @OneToOne(mappedBy = "user")
//    private Employee employee;
//
//    @OneToOne(mappedBy = "user")
//    private Customer customer;

    @OneToMany(mappedBy = "user")
    private List<Wish> wishes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Preorder> preorders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Message> messages = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private Room room;

    @OneToOne(mappedBy = "user")
    private Verification verification;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToMany(mappedBy = "user")
    private List<RewardLog> rewardLogs = new ArrayList<>();
}
