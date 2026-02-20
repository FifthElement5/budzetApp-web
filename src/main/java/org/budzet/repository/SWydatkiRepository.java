package org.budzet.repository;

import org.budzet.model.StaleWydatki;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SWydatkiRepository extends JpaRepository<StaleWydatki, Integer> {
    Page<StaleWydatki> findByNazwaContainingIgnoreCase(String nazwa, Pageable pageable);
}