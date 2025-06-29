package com.example.apiclient.controller;

import com.example.apiclient.model.Collection;
import com.example.apiclient.service.CollectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
public class CollectionsController {

    private final CollectionsService collectionsService;

    @Autowired
    public CollectionsController(CollectionsService collectionsService) {
        this.collectionsService = collectionsService;
    }

    @GetMapping
    public ResponseEntity<List<Collection>> getAllCollections() {
        try {
            List<Collection> collections = collectionsService.getAllCollections();
            return ResponseEntity.ok(collections);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable String id) {
        try {
            Collection collection = collectionsService.getCollectionById(id);
            if (collection == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(collection);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<Collection> saveCollection(@RequestBody Collection collection) {
        try {
            Collection saved = collectionsService.saveCollection(collection);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollection(@PathVariable String id) {
        try {
            boolean deleted = collectionsService.deleteCollection(id);
            if (deleted) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
