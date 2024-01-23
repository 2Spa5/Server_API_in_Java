package com.example;

import java.sql.Date;

public class Alunno {
    
    String nome;
    String cognome;
    Date dataNascita = new Date(0);

    public Alunno(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
    }

    public Alunno() {
    }

    @Override
    public String toString() {
        return nome + cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    
}
