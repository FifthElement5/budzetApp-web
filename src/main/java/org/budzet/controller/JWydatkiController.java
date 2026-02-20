package org.budzet.controller;

import org.budzet.model.JednorazoweWydatki;
import org.budzet.service.JWydatkiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jednorazowe-wydatki")
public class JWydatkiController {

    @Autowired
    private JWydatkiService service;

    @GetMapping
    public Page<JednorazoweWydatki> getAll(
            @RequestParam(required = false) String nazwa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return service.getAll(nazwa, PageRequest.of(page, size));
    }

    @PostMapping
    public JednorazoweWydatki create(@RequestBody JednorazoweWydatki wydatek) {
        return service.save(wydatek);
    }

    @GetMapping("/{id}")
    public JednorazoweWydatki getById(@PathVariable Integer id) {
        return service.getById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public JednorazoweWydatki update(@PathVariable Integer id, @RequestBody JednorazoweWydatki wydatek) {
        wydatek.setId(id);
        return service.save(wydatek);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}