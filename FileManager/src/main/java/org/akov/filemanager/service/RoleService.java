package org.akov.filemanager.service;

import org.akov.filemanager.model.Role;
import org.akov.filemanager.model.User;
import org.akov.filemanager.repository.RoleRepository;
import org.akov.filemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role save(String nom) {
        Role role = new Role();
        role.setNom(nom);
        return repository.save(role);
    }

    public boolean existsByNom(String nom) {
        return repository.existsByNomLikeIgnoreCase(nom);
    }

    public Role findByNom(String nom) {
        return repository.findByNomIgnoreCase(nom);
    }
}
