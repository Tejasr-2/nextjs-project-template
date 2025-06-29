package com.example.apiclient.service;

import com.example.apiclient.model.Collection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CollectionsService {

    private static final String COLLECTIONS_DIR = "backend/data/collections";

    private final ObjectMapper mapper;

    public CollectionsService() {
        this.mapper = new ObjectMapper();
        File dir = new File(COLLECTIONS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public List<Collection> getAllCollections() throws Exception {
        List<Collection> collections = new ArrayList<>();
        File dir = new File(COLLECTIONS_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                Collection collection = mapper.readValue(file, Collection.class);
                collections.add(collection);
            }
        }
        return collections;
    }

    public Collection getCollectionById(String id) throws Exception {
        File file = new File(COLLECTIONS_DIR, id + ".json");
        if (!file.exists()) {
            return null;
        }
        return mapper.readValue(file, Collection.class);
    }

    public Collection saveCollection(Collection collection) throws Exception {
        if (collection.getId() == null || collection.getId().isEmpty()) {
            collection.setId(UUID.randomUUID().toString());
        }
        File file = new File(COLLECTIONS_DIR, collection.getId() + ".json");
        mapper.writeValue(file, collection);
        return collection;
    }

    public boolean deleteCollection(String id) throws Exception {
        File file = new File(COLLECTIONS_DIR, id + ".json");
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
