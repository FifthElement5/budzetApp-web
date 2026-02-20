package org.budzet.controller;

import org.budzet.model.StalePrzychody;
import org.budzet.service.SPrzychodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stale-przychody")
public class SPrzychodyController {

    @Autowired
    private SPrzychodyService service;

    @GetMapping
    public Page<StalePrzychody> getAll(
            @RequestParam(required = false) String nazwa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return service.getAll(nazwa, PageRequest.of(page, size));
    }

    @PostMapping
    public StalePrzychody create(@RequestBody StalePrzychody przychod) {
        return service.save(przychod);
    }

    @GetMapping("/{id}")
    public StalePrzychody getById(@PathVariable Integer id) {
        return service.getById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public StalePrzychody update(@PathVariable Integer id, @RequestBody StalePrzychody przychod) {
        przychod.setId(id);
        return service.save(przychod);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}