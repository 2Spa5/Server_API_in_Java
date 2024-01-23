package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        try {

            Alunno a1 = new Alunno("Mario", "Rossi");
            Alunno a2 = new Alunno("Paolo", "Bianchi");
            Alunno a3 = new Alunno("Laura", "Verdi");
            Alunno a4 = new Alunno("Patrizia", "Blu");

            Classe c1 = new Classe(5, "AIA", "04-TC");

            c1.insertNewAlunno(a1);
            c1.insertNewAlunno(a2);
            c1.insertNewAlunno(a3);
            c1.insertNewAlunno(a4);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("htdocs/classe.json"), c1);

            System.out.println("Server in Avvio");
            ServerSocket server = new ServerSocket(8080);
            while (true) {

                Socket s = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream());
                DataOutputStream out2 = new DataOutputStream(s.getOutputStream());

                String richiesta = in.readLine();
                String riga[] = richiesta.split(" ");
                String path = riga[1];
                System.out.println(path);
                path = path.substring(1);
                System.out.println("---- " + path + " ----");

                if (path.equals(""))
                    path = "index.html";

                if (path.endsWith("/"))
                    path += "index.html";
                
                do {
                    String line = in.readLine();
                    System.out.println(line);
                    if (line == null || line.isEmpty())
                        break;
                } while (true);

                sendBinaryFile(s, path, findExt(path));

                out.flush();
                s.close();

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static String findExt(String path) {
        try {
            String a[] = path.split("\\.");
            path = a[1];
            return path;
        } catch (Exception e) {
            System.out.println("ERROR findExt - 1: " + e.getMessage() + "\n");
        }
        return "ERROR findExt - 2 \n";
    }

    private static void sendBinaryFile(Socket socket, String name, String ext) throws IOException {

        String filename = "htdocs/" + name;
        File file = new File(filename);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        if (file.exists()) {

                out.writeBytes("HTTP/1.1 200 OK\n");
                out.writeBytes("Content-Length: " + file.length() + "\n");
                out.writeBytes("Server: Java HTTP Server from Spagni: 1.0\n");
                out.writeBytes("Date: " + new Date() + "\n");
                checkExt(socket, ext, out);

                InputStream in = new FileInputStream(file);
                byte b[] = new byte[8192];
                int n;
                while ((n = in.read(b)) != -1) {
                    out.write(b, 0, n);
                }
                in.close();
        } else 
            write404(socket, file, out);
    }

    private static void write404(Socket socket, File file, DataOutputStream out) {

        try {
            out.writeBytes("HTTP/1.1 404 Not Found\n");
            out.writeBytes("Content-Lenght " + file.length() + "\n");
            out.writeBytes("Server: Java HTTP Server from Spagni: 1.0\n");
            out.writeBytes("Date: " + new Date() + "\n");
            out.writeBytes("\n");
        } catch (Exception e) {
            System.out.println("ERROR: write404");
        }

    }

    private static void checkExt(Socket socket, String ext, DataOutputStream out) {

        try {
            if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png"))
                out.writeBytes("Content-Type: image/" + ext + "\n");
            if (ext.equals("html"))
                out.writeBytes("Content-Type: text/html; charset=utf-8\n");
            if (ext.equals("css"))
                out.writeBytes("Content-Type: text/css; charset=utf-8\n");
            if (ext.equals("js"))
                out.writeBytes("Content-Type: application/js\n");
            if (ext.equals("json"))
                out.writeBytes("Content-Type: application/json\n");
            out.writeBytes("\n");
        } catch (Exception e) {
            System.out.println("ERROR: checkExt");
        }

    }
}
