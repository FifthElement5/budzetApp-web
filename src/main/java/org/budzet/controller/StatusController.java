package org.budzet.controller;

import org.budzet.model.Status;
import org.budzet.service.StatusService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
@CrossOrigin
public class StatusController {

    private final StatusService service;

    public StatusController(StatusService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Status> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String nazwa
    ) {
        return service.getAll(PageRequest.of(page, size), nazwa);
    }

    @GetMapping("/{id}")
    public Status getById(@PathVariable Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public Status create(@RequestBody Status status) {
        return service.create(status);
    }

    @PutMapping("/{id}")
    public Status update(@PathVariable Integer id, @RequestBody Status status) {
        return service.update(id, status);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
