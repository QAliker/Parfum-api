package org.akov.filemanager.controller.api;

import org.akov.filemanager.model.Parfum;
import org.akov.filemanager.service.ParfumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/parfum")
@CrossOrigin(origins="*")
public class ParfumController {
    @Autowired
    private ParfumService ParfumService;

    @PostMapping
    public Parfum SaveParfum(@RequestBody Parfum parfum) {
        return ParfumService.saveParfum(parfum);
    }

    @GetMapping
    public List<Parfum> getAllParfum() {
        return ParfumService.getAllParfum();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parfum> updateParfum(@PathVariable Integer id, @RequestBody Parfum parfum) {
          ParfumService.updateParfum(id, parfum);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
