package com.ajit.spring.security.FriendsRequestApplication.config;

import com.ajit.spring.security.FriendsRequestApplication.model.Friend;
import com.ajit.spring.security.FriendsRequestApplication.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsRequestUserDetailsService implements UserDetailsService {

    private final FriendRepository friendsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Friend friend = friendsRepository.findByEmail(username).orElseThrow(
                ()-> new UsernameNotFoundException("user details not found for user : "  +username)
        );

        List<GrantedAuthority> authorities = friend.getAuthorities().stream().map(authority->new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList());
//        List<GrantedAuthority> authorities= List.of(new SimpleGrantedAuthority(friend.getRole()));
        return new User(friend.getEmail(),friend.getPwd(),authorities);

    }
}
