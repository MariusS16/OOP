package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Secretariat {
    private ArrayList<Student> Studenti;
    private ArrayList<Curs> Cursuri;
    public Secretariat(){
        this.Studenti = new ArrayList<Student>();
        this.Cursuri = new ArrayList<Curs>();
    }

    public void adaugaStudent(String programStudii, String nume) throws StudentAlreadyExistsException {
        for (Student student : Studenti)
            if (student.getNume_student().equals(nume))
                throw new StudentAlreadyExistsException("Student duplicat: " + nume);
        if (programStudii.equals("master")) {
            Studenti.add(new StudMaster(nume));
        } else if (programStudii.equals("licenta")) {
            Studenti.add(new StudLicenta(nume));
        }
    }

    public void adaugaCurs(String progtamStudii, String numeCurs, int capacitate) {
        if (progtamStudii.equals("master")) {
            Cursuri.add(new Curs<StudMaster>(numeCurs, capacitate));
        } else if (progtamStudii.equals("licenta")) {
            Cursuri.add(new Curs<StudLicenta>(numeCurs, capacitate));
        }
    }

    public void citesteMedie(String nume, double nota) {
        for (Student student : Studenti)
            if (student.getNume_student().equals(nume))
                student.setNota(nota);
    }

    public Student[] evidentaStudenti() {
        Student[] studenti = new Student[Studenti.size()];
        for (int i = 0; i < Studenti.size(); i++)
            studenti[i] = Studenti.get(i);
        return studenti;
    }

    public void citesteMediile(String path) {
        File note = new File(path);
        String line;
        int nr_fisiere = Objects.requireNonNull(note.listFiles()).length;
        for (int i = 1; i <= nr_fisiere - 2; i++) {
            String path_note = path + "/note_" + i + ".txt";
            try (BufferedReader bufferedReader2 = new BufferedReader(new FileReader(path_note))) {
                while ((line = bufferedReader2.readLine()) != null) {
                    String[] linenote2 = line.split("-");
                    for (int j = 0; j < linenote2.length; j++)
                        linenote2[j] = linenote2[j].trim();
                    citesteMedie(linenote2[0], Double.parseDouble(linenote2[1]));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void posteazaMediile(String path) {
        try (FileWriter f_writer = new FileWriter(path, true)) {
            f_writer.write("***" + "\n");
            for (Student student : Studenti) {
                f_writer.write(student.getNume_student() + " - " + student.getNota() + "\n");
            }
        } catch (IOException e) {
            System.out.println("File error");
        }
    }

    public void sorteazaStudenti(){
        Student[] studenti = evidentaStudenti();
        int n = studenti.length;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                if (studenti[i].getNota() < studenti[j].getNota()) {
                    Student aux = studenti[i];
                    studenti[i] = studenti[j];
                    studenti[j] = aux;
                } else if (studenti[i].getNota() == studenti[j].getNota()) {
                    if (studenti[i].getNume_student().compareTo(studenti[j].getNume_student()) > 0) {
                        Student aux = studenti[i];
                        studenti[i] = studenti[j];
                        studenti[j] = aux;
                    }
                }
        Studenti.clear();
        for (int i = 0; i < n; i++)
            Studenti.add(studenti[i]);
    }

    public void contestatieNota(String nume, double nota) {
        for (Student student : Studenti)
            if (student.getNume_student().equals(nume))
                student.setNota(nota);
    }

    public void adaugaPreferinte(String nume, String numeCurs) {
        for (Student student : Studenti)
            if (student.getNume_student().equals(nume))
                student.preferintaCurs(numeCurs);
    }
    public void repartizeaza() {
        for (Student student : Studenti) {
            for (String numeCurs : student.getPreferinte()) {
                Curs cursPreferat = null;
                for (Curs curs : Cursuri) {
                    if (curs.getNume_curs().equals(numeCurs)) {
                        cursPreferat = curs;
                        break;
                    }
                }
                if (cursPreferat != null) {
                    if (cursPreferat.getCapacitate() > cursPreferat.getStudenti().size()) {
                        student.setCurs(cursPreferat);
                        cursPreferat.adaugaStudent(student);
                        break;
                    } else {
                        for (Object studentCurs : cursPreferat.getStudenti()) {
                            if (((Student) studentCurs).getNota() == student.getNota()) {
                                student.setCurs(cursPreferat);
                                cursPreferat.adaugaStudent(student);
                                break;
                            }
                        }
                    }
                }
            }

            if (student.getCurs() == null) {
                for (int i = 0; i < Cursuri.size(); i++)
                    for (int j = i + 1; j < Cursuri.size(); j++)
                        if (Cursuri.get(i).getNume_curs().compareTo(Cursuri.get(j).getNume_curs()) > 0) {
                            Curs aux = Cursuri.get(i);
                            Cursuri.set(i, Cursuri.get(j));
                            Cursuri.set(j, aux);
                        }
                student.setCurs(Cursuri.get(0));
                Cursuri.get(0).adaugaStudent(student);
            }
        }
    }

    public void posteazaStudent(String nume, String path) {
        for (Student student : Studenti)
            if (student.getNume_student().equals(nume)) {
                try (FileWriter f_writer = new FileWriter(path, true)) {
                    f_writer.write("***" + "\n");
                    if (student instanceof StudMaster) {
                        f_writer.write("Student Master: " + student.getNume_student() + " - " + student.getNota() + " - " + student.getCurs().getNume_curs() + "\n");
                    } else if (student instanceof StudLicenta) {
                        f_writer.write("Student Licenta: " + student.getNume_student() + " - " + student.getNota() + " - " + student.getCurs().getNume_curs() + "\n");
                    }
                } catch (IOException e) {
                    System.out.println("File error");
                }
            }
    }

    public void posteazaCurs(String nume, String path) {
        for (Curs curs : Cursuri)
            if (curs.getNume_curs().equals(nume)) {
                try (FileWriter f_writer = new FileWriter(path, true)) {
                    f_writer.write("***" + "\n");
                    f_writer.write(curs.getNume_curs() + " (" + curs.getCapacitate() + ")\n");
                    ArrayList<Student> studenti = curs.getStudenti();
                    int n = studenti.size();
                    for (int j = 0; j < n; j++)
                        for (int k = j + 1; k < n; k++)
                            if (studenti.get(j).getNume_student().compareTo(studenti.get(k).getNume_student()) > 0) {
                                Student aux = studenti.get(j);
                                studenti.set(j, studenti.get(k));
                                studenti.set(k, aux);
                            }
                    for (Student student : studenti)
                        f_writer.write(student.getNume_student() + " - " + student.getNota() + "\n");
                } catch (IOException e) {
                    System.out.println("File error");
                }
            }
    }
}
