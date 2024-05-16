package com.example.iwardrobefx;


public class Company {
    public static int capacity;
    public static String name; // Ikke statisk, unik for hver instans

    public int currentCapacity;

    public Company(int capacity, String name) {
        Company.capacity = capacity;
        Company.name = name; // Korrekt adgang til en instansvariabel
        this.currentCapacity = 0;
    }

    public static int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity + 1;
    }

    public static String getName() {  // Gør denne metode ikke-statisk
        return name;
    }
}
