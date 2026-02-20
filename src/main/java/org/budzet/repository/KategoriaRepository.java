package org.budzet.repository;

import org.budzet.model.Kategoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategoriaRepository extends JpaRepository<Kategoria, Integer> {
    Page<Kategoria> findByNazwaContainingIgnoreCase(String nazwa, Pageable pageable);
}
