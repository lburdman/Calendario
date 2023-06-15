package org.example.model;

public interface PersistenceHandler {
    boolean serialize(Calendar calendar);
    Calendar deserialize();
}
