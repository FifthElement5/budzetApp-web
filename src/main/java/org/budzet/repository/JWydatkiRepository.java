package org.budzet.repository;

import org.budzet.model.JednorazoweWydatki;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JWydatkiRepository extends JpaRepository<JednorazoweWydatki, Integer> {
    Page<JednorazoweWydatki> findByNazwaContainingIgnoreCase(String nazwa, Pageable pageable);
}