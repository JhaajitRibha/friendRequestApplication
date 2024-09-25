package com.ajit.spring.security.FriendsRequestApplication.repository;

import com.ajit.spring.security.FriendsRequestApplication.model.Friend;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FriendRepository extends CrudRepository<Friend, Long> {
    Optional<Friend> findByEmail(String email);
}
