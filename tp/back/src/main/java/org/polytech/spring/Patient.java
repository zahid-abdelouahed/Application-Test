package org.polytech.spring;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity 
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prenom;
    private String nom;
    private String email;
    private LocalDate dateNaissance;
    @ManyToOne 
    private Adresse adresse;
    //@ManyToOne 
    //private Docteur medecinTraitant;
    public Patient() {
    }

    public Patient(String prenom, String nom) {
        this(prenom, nom, null);
    }

    public Patient(String prenom, String nom, String email) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "Patient[id=%s, prenom=%s, nom=%s, email=%s]".formatted(id, prenom, nom, email);
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    /*
    public Docteur getMedecinTraitant() {
        return medecinTraitant;
    }

    public void setMedecinTraitant(Docteur medecinTraitant) {
        this.medecinTraitant = medecinTraitant;
    }
    */
}
