package org.akov.filemanager.service;

import org.akov.filemanager.model.User;
import org.akov.filemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailLikeIgnoreCase(email);
    }

    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndMdp(email, password);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String idUser) throws UsernameNotFoundException {
        User user =  userRepository.findById(Integer.parseInt(idUser)).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
