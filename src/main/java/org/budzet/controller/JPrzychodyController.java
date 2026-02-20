package org.budzet.controller;

import org.budzet.model.JednorazowePrzychody;
import org.budzet.service.JPrzychodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jednorazowe-przychody")
public class JPrzychodyController {

    @Autowired
    private JPrzychodyService service;

    @GetMapping
    public Page<JednorazowePrzychody> getAll(
            @RequestParam(required = false) String nazwa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return service.getAll(nazwa, PageRequest.of(page, size));
    }

    @PostMapping
    public JednorazowePrzychody create(@RequestBody JednorazowePrzychody przychod) {
        return service.save(przychod);
    }

    @GetMapping("/{id}")
    public JednorazowePrzychody getById(@PathVariable Integer id) {
        return service.getById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public JednorazowePrzychody update(@PathVariable Integer id, @RequestBody JednorazowePrzychody przychod) {
        przychod.setId(id);
        return service.save(przychod);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}