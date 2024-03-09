package org.akov.filemanager.repository;

import org.akov.filemanager.model.Role;
import org.akov.filemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByNomLikeIgnoreCase(String nom);

    Role findByNomIgnoreCase(String nom);
}
