package org.akov.filemanager.service;

import  org.akov.filemanager.model.Parfum;
import org.akov.filemanager.repository.ParfumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParfumService {

    @Autowired
    private ParfumRepository Parfumrepository;

    public Parfum saveParfum(Parfum parfum) {return Parfumrepository.save(parfum);}

    public List<Parfum> getAllParfum() { return Parfumrepository.findAll();}

    public Parfum updateParfum(Integer id, Parfum parfum) {
        Parfum existingParfum = Parfumrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parfum not found with id: " + id));

        existingParfum.setNom(parfum.getNom());
        existingParfum.setDescription(parfum.getDescription());
        existingParfum.setPhoto(parfum.getPhoto());
        existingParfum.setIsShowed(parfum.getIsShowed());

        return Parfumrepository.save(existingParfum);
    }
}
