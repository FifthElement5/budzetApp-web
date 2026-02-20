package org.budzet.service;

import org.budzet.model.StaleWydatki;
import org.budzet.repository.SWydatkiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SWydatkiService {

    @Autowired
    private SWydatkiRepository repository;

    public Page<StaleWydatki> getAll(String nazwa, Pageable pageable) {
        if (nazwa != null && !nazwa.isEmpty()) {
            return repository.findByNazwaContainingIgnoreCase(nazwa, pageable);
        }
        return repository.findAll(pageable);
    }

    public StaleWydatki save(StaleWydatki wydatek) {
        return repository.save(wydatek);
    }

    public Optional<StaleWydatki> getById(Integer id) {
        return repository.findById(id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}