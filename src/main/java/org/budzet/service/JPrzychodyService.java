package org.budzet.service;

import org.budzet.model.JednorazowePrzychody;
import org.budzet.repository.JPrzychodyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JPrzychodyService {

    @Autowired
    private JPrzychodyRepository repository;

    public Page<JednorazowePrzychody> getAll(String nazwa, Pageable pageable) {
        if (nazwa != null && !nazwa.isEmpty()) {
            return repository.findByNazwaContainingIgnoreCase(nazwa, pageable);
        }
        return repository.findAll(pageable);
    }

    public JednorazowePrzychody save(JednorazowePrzychody przychod) {
        return repository.save(przychod);
    }

    public Optional<JednorazowePrzychody> getById(Integer id) {
        return repository.findById(id);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}