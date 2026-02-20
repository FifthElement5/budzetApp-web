package org.budzet.controller;

import org.budzet.model.StaleWydatki;
import org.budzet.service.SWydatkiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stale-wydatki")
public class SWydatkiController {

    @Autowired
    private SWydatkiService service;

    @GetMapping
    public Page<StaleWydatki> getAll(
            @RequestParam(required = false) String nazwa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return service.getAll(nazwa, PageRequest.of(page, size));
    }

    @PostMapping
    public StaleWydatki create(@RequestBody StaleWydatki wydatek) {
        return service.save(wydatek);
    }

    @GetMapping("/{id}")
    public StaleWydatki getById(@PathVariable Integer id) {
        return service.getById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public StaleWydatki update(@PathVariable Integer id, @RequestBody StaleWydatki wydatek) {
        wydatek.setId(id);
        return service.save(wydatek);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}