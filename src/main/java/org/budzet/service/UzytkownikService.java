package org.budzet.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.budzet.model.Uzytkownik;
import org.budzet.repository.UzytkownikRepository;

import java.util.List;

@Service
public class UzytkownikService {

    private final UzytkownikRepository repo; // repor to interfejs JPA, Spring sam tworzy implemnetacje

    public UzytkownikService(UzytkownikRepository repo) {
        this.repo = repo;
    }
    // findAll() wbudowana metoda z JpaRepository
    public List<Uzytkownik> getAll() {
        return repo.findAll();
    }
    // findByID(id) wbudowana metoda z JpaRepository/ zwraca optional
    public Uzytkownik getById(Integer id) {
        return repo.findById(id).orElse(null);
    }
    // save() wbudowane: zapisuje nowy lub aktualizucje
    public Uzytkownik create(Uzytkownik u) {
        return repo.save(u);
    }
    //  deleteById() – wbudowane kasowanie rekordu po ID
    public Uzytkownik update(Integer id, Uzytkownik u) {
        u.setId(id);
        return repo.save(u);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    // 🔥 Nowa metoda do wyszukiwania z paginacją
    public Page<Uzytkownik> search(String nazwisko, Integer id, Pageable pageable) {

        // szukanie po ID – Optional → PageImpl
        // PageImpl to klasa Springa pozwalająca stworzyć "ręczną" paginację
        if (id != null) {
            return repo.findById(id)
                    .map(u -> new PageImpl<>(List.of(u), pageable, 1))
                    .orElse(new PageImpl<>(List.of(), pageable, 0));
        }

        // szukanie po nazwisku
        // customowa metoda findByNazwiskoContainingIgnoreCase
        // 🔹 NIE wbudowana – Spring Data generuje ją automatycznie po nazwie
        if (nazwisko != null && !nazwisko.isEmpty()) {
            List<Uzytkownik> lista = repo.findByNazwiskoContainingIgnoreCase(nazwisko);
            return new PageImpl<>(lista, pageable, lista.size());
        }

        // domyślna paginacja
        return repo.findAll(pageable);
    }
}
