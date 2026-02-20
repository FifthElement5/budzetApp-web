package org.budzet.service;

import org.budzet.model.Kategoria;
import org.budzet.repository.KategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KategoriaService {

    private final KategoriaRepository repo;

    public KategoriaService(KategoriaRepository repo) {
        this.repo = repo;
    }

    public List<Kategoria> getAll() {
        return repo.findAll();
    }

    public Kategoria getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Kategoria create(Kategoria kategoria) {
        return repo.save(kategoria);
    }

    public Kategoria update(Integer id, Kategoria kategoria) {
        kategoria.setId(id);
        return repo.save(kategoria);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public Page<Kategoria> getAll(Pageable pageable, String nazwa) {
        if (nazwa == null || nazwa.isEmpty()) {
            return repo.findAll(pageable);
        } else {
            return repo.findByNazwaContainingIgnoreCase(nazwa, pageable);
        }
    }
}
