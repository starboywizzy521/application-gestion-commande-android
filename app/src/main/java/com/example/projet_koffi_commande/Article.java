package com.example.projet_koffi_commande;

import java.io.Serializable;

public class Article implements Serializable {

    private int code;
    private String libelle;
    private double pu;
    private int categorie;

    public Article(int code, String libelle,double pu) {
        this.code = code;
        this.libelle = libelle;
        this.pu = pu;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getPu(){return pu;}

    public void setPu(double pu){this.pu=pu;}

}
