package Model;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;


public class Soiree implements Serializable {

    private Integer idSoiree;
    private String nom;
    private Date date;
    private String ville;
    private Integer codePostal;
    private String rue;
    private Double pPrev;
    private Double pSurPlace;
    private String description;
    private Integer fk_utilisateur;

    public Soiree(){}

    public Soiree(Integer idSoiree, String nom, Date date, String ville, Integer codePostal, String rue, Double pPrev, Double pSurPlace, String description, Integer fk_utilisateur)
            throws NomException, VilleException, RueException, CPException  {
        this.idSoiree = idSoiree;
        this.nom = nom;
        this.date = date;
        this.ville = ville;
        this.codePostal = codePostal;
        this.rue = rue;
        this.pPrev = pPrev;
        this.pSurPlace = pSurPlace;
        this.description = description;
        this.fk_utilisateur = fk_utilisateur;
    }

    public Integer getIdSoiree(){
        return idSoiree;
    }

    public void setIdSoiree(Integer idSoiree){
        this.idSoiree = idSoiree;
    }

    public String getNom(){

        return nom;
    }

    public void setNom(String nom) throws NomException {
        boolean b = Pattern.matches("[a-zA-Z0-9][a-zA-Z0-9 -]*", nom);
        if(b != true){
            throw new NomException();
        } else {
            this.nom = nom.substring(0,1).toUpperCase() + nom.substring(1).toLowerCase();
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) throws VilleException {
        boolean b = Pattern.matches("[a-zA-Z]*", ville);
        if(b != true){
            throw new VilleException();
        } else {
            this.ville = ville;
        }
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) throws CPException {
        if(String.valueOf(codePostal).isEmpty() || String.valueOf(codePostal).length() != 4){
            throw new CPException();
        } else {
            this.codePostal = codePostal;
        }
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) throws RueException {
        boolean b = Pattern.matches("[a-zA-Z -]*", rue);
        if(!b){
            throw new RueException();
        } else {
            this.rue = rue;
        }
    }

    public Double getPrixPrevente() {
        return pPrev;
    }

    public void setPrixPrevente(Double prixPrevente) {
        this.pPrev = prixPrevente;
    }

    public Double getPrixSurPlace() {
        return pSurPlace;
    }

    public void setPrixSurPlace(Double prixSurPlace) {
        this.pSurPlace = prixSurPlace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getFk_utilisateur(){return fk_utilisateur;}

    public void setFk_utilisateur(Integer fk_utilisateur){this.fk_utilisateur = fk_utilisateur;}
}