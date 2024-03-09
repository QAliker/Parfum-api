package org.akov.filemanager.controller.api;

import org.akov.filemanager.exception.WsException;
import org.akov.filemanager.manager.JwtTokenManager;
import org.akov.filemanager.manager.Verification;
import org.akov.filemanager.model.Role;
import org.akov.filemanager.model.User;
import org.akov.filemanager.service.RoleService;
import org.akov.filemanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class IdentificationController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;


    @PostMapping("/register")
    public Map<String, String>  register(@RequestBody User userInfos) throws WsException {
        // vérifier si l'email existe
        if (!Verification.isValidEmailAddress(userInfos.getEmail())){
            throw new WsException(HttpStatus.BAD_REQUEST, "L'email est invalide");
        }
        if (this.userService.existsByEmail(userInfos.getEmail())){
            throw new WsException(HttpStatus.BAD_REQUEST, "L'email existe déja");
        }

        // vérifier mdp
        if (!Verification.isValidPassword(userInfos.getMdp())){
            throw new WsException(HttpStatus.BAD_REQUEST, "Le mot de passe est vide");
        }

        userInfos.setMdp(passwordEncoder.encode(userInfos.getMdp())); // crypter le mdp

        // ajouter un role
        Role role = this.roleService.findByNom("ADMIN");
        if (role == null){
            role = this.roleService.save("ADMIN");
        }

        userInfos.setRole(role);

        userInfos = this.userService.save(userInfos);

        Map<String, String> response = new HashMap<>();
        response.put("token", JwtTokenManager.generateToken(userInfos.getId()));
        return response;
    }

    @PostMapping("/identification")
    public Map<String, String>  login(@RequestBody User user) {
        User userFound = this.userService.findByEmail(user.getEmail());
        if (userFound == null){
            throw new WsException(HttpStatus.BAD_REQUEST, "L'email ou le mot de passe est incorrect");
        }

        if (!passwordEncoder.matches(user.getMdp(), userFound.getMdp())){
            throw new WsException(HttpStatus.BAD_REQUEST, "L'email ou le mot de passe est incorrect");
        }

        Map<String, String> response = new HashMap<>();
        response.put("token", JwtTokenManager.generateToken(userFound.getId()));
        response.put("role", userFound.getRole().getNom());
        response.put("id", String.valueOf(userFound.getId()));
        response.put("email", userFound.getEmail());
        response.put("nom", userFound.getNom());

        return response;
    }

}
