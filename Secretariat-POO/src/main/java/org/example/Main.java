package org.example;

import java.io.*;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws IOException {
        Secretariat secretariat = new Secretariat();
        String path_dir = "src/main/resources/" + args[0];
        String path_input = "src/main/resources/" + args[0] + "/" + args[0] + ".in";
        String path_output = "src/main/resources/" + args[0] + "/" + args[0] + ".out";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path_input))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    String[] line_com = line.split("-");
                    for (int i = 0; i < line_com.length; i++) {
                        line_com[i] = line_com[i].trim();
                    }
                    if (line_com[0].equals("adauga_student")) {
                        secretariat.adaugaStudent(line_com[1], line_com[2]);
                    }
                    if (line_com[0].equals("adauga_curs")) {
                        secretariat.adaugaCurs(line_com[1], line_com[2], Integer.parseInt(line_com[3]));
                    }
                    if (line_com[0].equals("citeste_mediile")) {
                        secretariat.citesteMediile(path_dir);
                    }
                    if (line_com[0].equals("posteaza_mediile")) {
                        secretariat.sorteazaStudenti();
                        secretariat.posteazaMediile(path_output);
                    }
                    if (line_com[0].equals("contestatie")) {
                        secretariat.contestatieNota(line_com[1], Double.parseDouble(line_com[2]));
                    }
                    if (line_com[0].equals("adauga_preferinte")) {
                        for (int i = 1; i < line_com.length; i++)
                            secretariat.adaugaPreferinte(line_com[1], line_com[i]);
                    }
                    if (line_com[0].equals("repartizeaza")) {
                        secretariat.repartizeaza();
                    }
                    if (line_com[0].equals("posteaza_curs")) {
                        secretariat.posteazaCurs(line_com[1], path_output);
                    }
                    if (line_com[0].equals("posteaza_student")) {
                        secretariat.posteazaStudent(line_com[1], path_output);
                    }
                } catch (StudentAlreadyExistsException e) {
                    try (FileWriter f_writer = new FileWriter(path_output, true)) {
                        f_writer.write("***" + "\n");
                        f_writer.write(e.getMessage() + "\n");
                    } catch (IOException exception) {
                        System.out.println(exception.getMessage());
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            }
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
