package com.example.iwardrobefx;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class Company {
    FileIO io = new FileIO();
    Organizer organizer = new Organizer();
    int capacity;
    String name;

    public Company(int capacity, String name) {
        this.capacity = capacity;
        this.name = name;

    }


}