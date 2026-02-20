package org.budzet.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.budzet.model.Dzial;

public interface DzialRepository extends JpaRepository<Dzial, Integer> {
    Page<Dzial> findByNazwaContainingIgnoreCase(String nazwa, Pageable pageable);
}
