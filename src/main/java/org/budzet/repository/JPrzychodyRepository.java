package org.budzet.repository;

import org.budzet.model.JednorazowePrzychody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPrzychodyRepository extends JpaRepository<JednorazowePrzychody, Integer> {
    Page<JednorazowePrzychody> findByNazwaContainingIgnoreCase(String nazwa, Pageable pageable);
}