package com.ajit.spring.security.FriendsRequestApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="friend")
@Getter
@Setter
@RequiredArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="friend_id")
    private long id;

    private String name;

    private String email;

    @Column(name="mobile_number")
    private String mobileNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String pwd;

    private String role;

    @Column(name="create_dt")
    @JsonIgnore
    private Date createDt;

    @OneToMany(mappedBy = "friend", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Authority> authorities;

}
