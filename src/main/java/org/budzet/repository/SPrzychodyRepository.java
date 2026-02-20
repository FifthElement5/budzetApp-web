package org.budzet.repository;

import org.budzet.model.StalePrzychody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SPrzychodyRepository extends JpaRepository<StalePrzychody, Integer> {
    Page<StalePrzychody> findByNazwaContainingIgnoreCase(String nazwa, Pageable pageable);
}