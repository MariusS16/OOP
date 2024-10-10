package org.example;

import java.util.ArrayList;

public class Student {
    private String nume_student;
    private double nota;
    private ArrayList<String> preferinte = new ArrayList<String>();
    private Curs<?> curs;
    Student(String nume_student){
        this.nume_student = nume_student;
        this.nota = 0.0;
    }
    public String getNume_student(){
        return this.nume_student;
    }
    public void setNume_student(String nume_student){
        this.nume_student = nume_student;
    }
    public double getNota(){
        return this.nota;
    }
    public void setNota(double nota){
        this.nota = nota;
    }
    public ArrayList<String> getPreferinte(){
        return this.preferinte;
    }

    public void preferintaCurs(String nume) {
        preferinte.add(nume);
    }

    public void setCurs(Curs<?> curs) {
        this.curs = curs;
    }

    public Curs<?> getCurs() {
        return this.curs;
    }
}
