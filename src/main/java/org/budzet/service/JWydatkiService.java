package org.budzet.service;

import org.budzet.model.JednorazoweWydatki;
import org.budzet.repository.JWydatkiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JWydatkiService {

    @Autowired
    private JWydatkiRepository repository;

    public Page<JednorazoweWydatki> getAll(String nazwa, Pageable pageable) {
        if (nazwa != null && !nazwa.isEmpty()) {
            return repository.findByNazwaContainingIgnoreCase(nazwa, pageable);
        }
        return repository.findAll(pageable);
    }

    public JednorazoweWydatki save(JednorazoweWydatki wydatek) {
        return repository.save(wydatek);
    }

    public Optional<JednorazoweWydatki> getById(Integer id) {
        return repository.findById(id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}