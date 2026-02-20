package org.budzet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.budzet.model.Uzytkownik;

import java.util.List;

public interface UzytkownikRepository extends JpaRepository<Uzytkownik, Integer> {
    List<Uzytkownik> findByNazwiskoContainingIgnoreCase(String nazwisko);

}

