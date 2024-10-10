package TemaTest;

import java.io.*;
import java.util.ArrayList;

public class Post implements Likeable{
    private String text = "";
    private String username = "";
    private int id = 1;
    private String date = "";
    public Post(String text, String username, int id, String date) {
        this.text = text;
        this.username = username;
        this.id = id;
        this.date = date;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public int getId() {
        return id;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }


    public void like(String[] username, String[] id) {
        int ok2 = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("posts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[0].equals(id[1])) {
                    try (BufferedReader br2 = new BufferedReader(new FileReader("likepost.txt"))) {
                        String line2;
                        while ((line2 = br2.readLine()) != null) {
                            String[] parts2 = line2.split(",");
                            if(parts2[0].equals(username[1]) && parts2[1].equals(id[1])) {
                                System.out.println("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
                                return;
                            }
                        }
                    } catch (IOException e) {
                    }
                    ok2 = 1;
                    try (FileWriter fw = new FileWriter("likepost.txt", true);
                         BufferedWriter bw = new BufferedWriter(fw);
                         PrintWriter out = new PrintWriter(bw)) {
                        out.println(username[1] + "," + id[1]);
                    } catch (IOException e) {
                    }
                    System.out.println("{'status':'ok','message':'Operation executed successfully'}");
                    return;
                }
            }
        } catch (IOException e) {
        }
        if(ok2 == 0)
            System.out.println("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
    }
    public void unlike(String[] username, String[] id) {
        int ok2 = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("posts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if(parts[0].equals(id[1])) {
                    try (BufferedReader br2 = new BufferedReader(new FileReader("likepost.txt"))) {
                        String line2;
                        while ((line2 = br2.readLine()) != null) {
                            String[] parts2 = line2.split(",");
                            if(parts2[0].equals(username[1]) && parts2[1].equals(id[1])) {
                                ok2 = 1;
                                ArrayList<String> vector = new ArrayList<>();
                                try (BufferedReader br3 = new BufferedReader(new FileReader("likepost.txt"))) {
                                    String line3;
                                    while ((line3 = br3.readLine()) != null) {
                                        String[] parts3 = line3.split(",");
                                        if(!parts3[0].equals(username[1]) || !parts3[1].equals(id[1])) {
                                            vector.add(line3);
                                        }
                                    }
                                } catch (IOException e) {
                                }

                                try (FileWriter fw = new FileWriter("likepost.txt", false);
                                     BufferedWriter bw = new BufferedWriter(fw);
                                     PrintWriter out = new PrintWriter(bw)) {
                                    out.println("");
                                } catch (IOException e) {
                                }

                                for (int i = 0; i < vector.size(); i++) {
                                    try (FileWriter fw = new FileWriter("likepost.txt", true);
                                         BufferedWriter bw = new BufferedWriter(fw);
                                         PrintWriter out = new PrintWriter(bw)) {
                                        out.println(vector.get(i));
                                    } catch (IOException e) {
                                    }
                                }
                                System.out.println("{'status':'ok','message':'Operation executed successfully'}");
                                return;
                            }
                        }
                    } catch (IOException e) {
                    }
                }
            }
        } catch (IOException e) {
        }
        if(ok2 == 0)
            System.out.println("{ 'status' : 'error', 'message' : 'The post identifier to unlike was not valid'}");
    }
}
