package com.example;

import java.util.ArrayList;

public class Classe {
    
    int anno;
    String sezione;
    String aula;
    ArrayList<Alunno> alunni = new ArrayList<Alunno>();

    public Classe(int anno, String sezione, String aula){
        this.anno = anno;
        this.sezione = sezione;
        this.aula = aula;
        alunni = new ArrayList<Alunno>();
    }

    public Classe() {
    }

    public void insertNewAlunno(Alunno a){
        try {
            alunni.add(a);
        } catch (Exception e) {
            System.out.println("Errore: INSERIMENTO ALUNNO ERRATO - " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        String s = anno + sezione + "\n";
        for(int i = 0; i < alunni.size(); i++)
            s += "Alunno - " + alunni.get(i).nome + " " + alunni.get(i).cognome + "\n";
        return s;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getSezione() {
        return sezione;
    }

    public void setSezione(String sezione) {
        this.sezione = sezione;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public ArrayList<Alunno> getAlunni() {
        return alunni;
    }

    public void setAlunni(ArrayList<Alunno> alunni) {
        this.alunni = alunni;
    }
    
}
