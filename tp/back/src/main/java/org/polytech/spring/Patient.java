package org.polytech.spring;

public class Patient {
  private final String name;
  private final int id;

    public Patient(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
}
