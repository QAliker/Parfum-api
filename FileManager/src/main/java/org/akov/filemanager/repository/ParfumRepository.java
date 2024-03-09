package org.akov.filemanager.repository;

import org.akov.filemanager.model.Parfum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParfumRepository extends JpaRepository<Parfum, Integer> {
}
