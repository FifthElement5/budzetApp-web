package org.budzet.repository;

import org.budzet.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Page<Status> findByNazwaContainingIgnoreCase(String nazwa, Pageable pageable);
}
