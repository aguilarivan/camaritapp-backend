package com.gaulab.weshoot.service;

import com.gaulab.weshoot.model.Box;
import com.gaulab.weshoot.repository.BoxRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoxService {

    private final BoxRepository repository;

    public BoxService(BoxRepository repository) {
        this.repository = repository;
    }

    public List<Box> findAll() {
        return repository.findAll();
    }

    public Box save(Box box) {
        return repository.save(box);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
