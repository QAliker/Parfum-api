package org.akov.filemanager.repository;

import org.akov.filemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmailLikeIgnoreCase(String email);

    User findByEmailAndMdp(String email, String mdp);

    User findByEmail(String email);
}
