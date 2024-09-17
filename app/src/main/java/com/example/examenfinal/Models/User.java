package com.example.examenfinal.Models;

public class User {
    public Name name;
    public Location location;
    public String email;
    public Picture picture;
    public Dob dob; // Fecha de nacimiento para calcular la edad
    public String phone;
    public String cell;
    public Id id;
    public String nat; // Nacionalidad

    public class Name {
        public String first;
        public String last;
    }

    public class Location {
        public String country;
    }

    public class Picture {
        public String large; // Foto más grande para la nueva actividad
        public String thumbnail;
    }

    public class Dob {
        public String date;
        public int age; // Edad
    }

    public class Id {
        public String name;
        public String value;
    }
}
