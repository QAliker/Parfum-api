package org.akov.filemanager.manager;

import org.akov.filemanager.exception.WsException;
import org.springframework.http.HttpStatus;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Verification {

    /**
     * cette méthode elle permet de vérifier si l'email est valide
     * @param email
     * @return boolean true si l'email est valide
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    /**
     * Méthode qui permet de verifier le mot de passe<br>
     * <ul>
     *     <li>Le mot de passe doit contenir au moins 6 caractères</li>
     *     <li>Le mot de passe ne doit pas contenir d'espace</li>
     *     <li>Le mot de passe doit contenir au moins une majuscule</li>
     *     <li>Le mot de passe doit contenir au moins une minuscule</li>
     *     <li>Le mot de passe doit contenir au moins un chiffre</li>
     * </ul>
     * @param password
     * @return true si le mdp est valide
     */
    public static boolean isValidPassword(String password){
        if (password == null || password.isEmpty()){
            throw new WsException(HttpStatus.BAD_REQUEST, "Le mot de passe doit contenir au moins 6 caractères");
        }

        if (password.length() < 6){
            throw new WsException(HttpStatus.BAD_REQUEST, "Le mot de passe doit contenir au moins 6 caractères");
        }

        int nbrMaj = 0;
        int nbrMin = 0;
        int nbrNum = 0;
        for (int i = 0; i < password.length(); i++) {
            char caractere = password.charAt(i);
            if (caractere == ' '){
                throw new WsException(HttpStatus.BAD_REQUEST, "Le mot de passe ne doit pas contenir d'espace");
            }
            if (caractere >= 'A' && caractere <= 'Z'){
                nbrMaj++;
            }
            if (caractere >= 'a' && caractere <= 'z'){
                nbrMin++;
            }
            if (caractere >= '0' && caractere <= '9'){
                nbrNum++;
            }
        }

        if (nbrMaj < 2){
            throw new WsException(HttpStatus.BAD_REQUEST, "Le mot de passe doit contenir au moins 2 majuscules");
        }
        if (nbrMin < 2){
            throw new WsException(HttpStatus.BAD_REQUEST, "Le mot de passe doit contenir au moins 2 minuscules");
        }
        if (nbrNum < 2){
            throw new WsException(HttpStatus.BAD_REQUEST, "Le mot de passe doit contenir au moins 2 chiffres");
        }
        return true;
    }

}
