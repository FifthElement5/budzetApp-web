package org.budzet.service;

import org.budzet.model.Status;
import org.budzet.repository.StatusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository repo;

    public StatusService(StatusRepository repo) {
        this.repo = repo;
    }

    public List<Status> getAll() {
        return repo.findAll();
    }

    public Status getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public Status create(Status status) {
        return repo.save(status);
    }

    public Status update(Integer id, Status status) {
        status.setId(id);
        return repo.save(status);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public Page<Status> getAll(Pageable pageable, String nazwa) {
        if (nazwa == null || nazwa.isEmpty()) {
            return repo.findAll(pageable);
        } else {
            return repo.findByNazwaContainingIgnoreCase(nazwa, pageable);
        }
    }
}
