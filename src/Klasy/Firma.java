package Klasy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
//import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Firma class
 * @see java.lang.Object
 */
@Entity(name = "Firma")
public class Firma {
    private long id;

    private String nazwaFirmy;
    private String telefonFirmy;
    private Adres adresFirmy;

    private List<Umowa> listaUmowFirmy = new ArrayList<>();

    private static List<Firma> ekstensjaFirm = new ArrayList<>();

    private Firma(){}
    /**
     * Firma constructor
     * @param nazwaFirmy (required)
     * @param telefonFirmy (required)
     * @param adresFirmy (required)
     * @throws Exception
     */
    public Firma(String nazwaFirmy, String telefonFirmy, Adres adresFirmy) throws Exception {
        setNazwaFirmy(nazwaFirmy);
        setTelefonFirmy(telefonFirmy);
        setAdresFirmy(adresFirmy);
        dodajFirme(this);
    }
    /**
     * Shows extention
     */
    public static void pokazEkstensjeFirm() {
        System.out.println("Ekstensja klasy Firm: ");
        for(Firma firma : ekstensjaFirm){
            System.out.println(firma);
        }
    }
    /**
     * Adds firma to extention
     */
    private void dodajFirme(Firma firma) {
        ekstensjaFirm.add(firma);
    }
    /**
     * Removes firma from extention
     */
    private void usunFirme(Firma firma) {
        for(Umowa umowa : listaUmowFirmy){
            if(firma.equals(umowa.getFirma())){
                Umowa.getEkstensjaUmow().remove(umowa);
            }
        }
        ekstensjaFirm.remove(firma);
    }
    /**
     * Gets id
     * @return long
     */
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy =
            "increment")
    public long getId() {
        return id;
    }
    /**
     * Sets id
     * @param id
     */
    private void setId(long id) {
        this.id = id;
    }
    /**
     * Gets nazwaFirmy
     * @return String
     */
    @Basic
    public String getNazwaFirmy(){
        return this.nazwaFirmy;
    }
    /**
     * Sets nazwaFirmy
     * @param nazwaFirmy
     */
    public void setNazwaFirmy(String nazwaFirmy){
        this.nazwaFirmy = nazwaFirmy;
    }
    /**
     * Gets telefonFirmy
     * @return String
     */
    @Basic
    public String getTelefonFirmy(){
        return this.telefonFirmy;
    }
    /**
     * Sets telefonFirmy
     * @param telefonFirmy
     * @throws Exception
     */
    public void setTelefonFirmy(String telefonFirmy) throws Exception {
        Pattern pattern = Pattern.compile("[0-9]{3}[-\\s]?[0-9]{3}[-\\s]?[0-9]{3}");
        Matcher matcher = pattern.matcher(telefonFirmy);
        if(matcher.matches()){
            this.telefonFirmy = telefonFirmy;
        } else {
            throw new Exception("Telefon ma zły format: " + telefonFirmy);
        }
    }
    /**
     * Gets adresFirmy
     * Asociation
     *
     * @return Adres
     */
    @OneToOne
    @JoinColumn(name = "fk_Adres")
    public Adres getAdresFirmy(){
        return this.adresFirmy;
    }

    /**
     * Sets adresFirmy
     * @param adresFirmy
     */
    public void setAdresFirmy(Adres adresFirmy){
        this.adresFirmy = adresFirmy;
    }

    /**
     * Overrides toString method
     */
    public String toString() {
        String result = "";
        result += getNazwaFirmy() + "\n";
        result += getTelefonFirmy() + "\n";
        result += getAdresFirmy() + "\n";
        result += "Lista umów firmy: \n\t";
        for (Umowa umowa : listaUmowFirmy) {
            result += umowa + "\n\t";
        }
        return result;
    }
    /**
     * Adds new umowa
     * @param kierownik
     * @throws Exception
     */
    public void dodajNowaUmowe(Kierownik kierownik) throws Exception {
        Umowa.stworzUmowe(this, kierownik);
    }

    /**
     * Adds umowa to list
     * @param umowa
     */
    public void dodajUmowe(Umowa umowa){
        if(!this.listaUmowFirmy.contains(umowa))
            this.listaUmowFirmy.add(umowa);
    }
    /**
     * Gets ekstensjaFirm
     * @return List of Firma
     */
    public static List<Firma> getEkstensjaFirm() {
        return ekstensjaFirm;
    }
    /**
     * Adds umowa to list
     * @param ekstensjaFirm
     */
    public static void setEkstensjaFirm(List<Firma> ekstensjaFirm) {
        Firma.ekstensjaFirm = ekstensjaFirm;
    }

    /**
     * Gets listaUmowFirmy
     * Asociation
     *
     * @return List of Umowa
     */
    @OneToMany(mappedBy="firma")
    public List<Umowa> getListaUmowFirmy() {
        return listaUmowFirmy;
    }
    /**
     * Sets listaUmowFirmy
     * @param listaUmowFirmy
     */
    public void setListaUmowFirmy(List<Umowa> listaUmowFirmy) {
        this.listaUmowFirmy = listaUmowFirmy;
    }
}
