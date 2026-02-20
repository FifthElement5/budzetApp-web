package org.budzet.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.budzet.model.Dzial;
import org.budzet.repository.DzialRepository;

import java.util.List;

@Service
public class DzialService {

    private final DzialRepository repo;

    public DzialService(DzialRepository repo) {
        this.repo = repo;
    }

    public List<Dzial> getAll() {
        return repo.findAll();
    }

    public Dzial getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Dzial create(Dzial dzial) {
        return repo.save(dzial);
    }

    public Dzial update(Integer id, Dzial dzial) {
        dzial.setId(id);
        return repo.save(dzial);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    // Paginacja i opcjonalne filtrowanie po nazwie
    public Page<Dzial> getAll(Pageable pageable, String nazwa) {
        if (nazwa == null || nazwa.isEmpty()) {
            return repo.findAll(pageable);
        } else {
            return repo.findByNazwaContainingIgnoreCase(nazwa, pageable);
        }
    }
}
