package com.mygame.config.security;

import com.mygame.entity.authentication.User;
import com.mygame.repository.authentication.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    //Load ttin ng dùng
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        // Status 1: Activated/Verified
        if (user.getStatus() != 1) {
            throw new RuntimeException("[Error] Account has not been activated");
        }

        return UserDetailsImpl.build(user);
    }

}
