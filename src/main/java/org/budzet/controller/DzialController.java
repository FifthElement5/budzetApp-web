package org.budzet.controller;

import org.budzet.model.Dzial;
import org.budzet.service.DzialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dzial")
@CrossOrigin
public class DzialController {

    private final DzialService service;

    public DzialController(DzialService service) {
        this.service = service;
    }

//    @GetMapping
//    public List<Dzial> getAll() {
//        return service.getAll();
//    }

    @GetMapping
    public Page<Dzial> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String nazwa
    ) {
        return service.getAll(PageRequest.of(page, size), nazwa);
    }

    @GetMapping("/{id}")
    public Dzial getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Dzial create(@RequestBody Dzial dzial) {
        return service.create(dzial);
    }

    @PutMapping("/{id}")
    public Dzial update(@PathVariable Integer id, @RequestBody Dzial dzial) {
        return service.update(id, dzial);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
