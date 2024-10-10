package org.example;

import java.util.ArrayList;

public class Curs<T extends Student> {
    private String nume_curs;
    private int capacitate;
    private ArrayList<T> studenti = new ArrayList<>();

    Curs(String nume_curs, int capacitate){
        this.nume_curs = nume_curs;
        this.capacitate = capacitate;
    }
    public String getNume_curs(){
        return this.nume_curs;
    }
    public void setNume_curs(String nume_curs){
        this.nume_curs = nume_curs;
    }
    public int getCapacitate(){
        return this.capacitate;
    }
    public void setCapacitate(int capacitate){
        this.capacitate = capacitate;
    }

    public ArrayList<T> getStudenti() {
        return studenti;
    }

    public void adaugaStudent(T student) {
        studenti.add(student);
    }
}
