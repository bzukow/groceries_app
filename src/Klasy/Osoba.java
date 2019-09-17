package Klasy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Osoba class (abstract)
 * @see java.lang.Object
 */
@Entity(name = "Osoba")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Osoba {
    private long id;

    private String imiona;
    private String nazwisko;
    private String telefon;

    private static List<Osoba> ekstensjaOsob = new ArrayList<>();

    protected Osoba(){}
    /**
     * Constructor
     * @param imiona (required)
     * @param nazwisko (required)
     * @param telefon (required)
     * @throws Exception
     */
    public Osoba(String imiona, String nazwisko, String telefon) throws Exception {
        setImiona(imiona);
        setNazwisko(nazwisko);
        setTelefon(telefon);
        dodajOsobe(this);
    }
    /**
     * Adds person to extension
     * @param osoba
     */
    private static void dodajOsobe(Osoba osoba){
        ekstensjaOsob.add(osoba);
    }
    /**
     * Removes person from extension
     * @param osoba
     */
    private static void usunOsobe(Osoba osoba){
        ekstensjaOsob.remove(osoba);
    }
    /**
     * Shows extension
     */
    public static void pokazEkstensjeOsob() {
        System.out.println("Ekstensja klasy Osoba: ");
        for(Osoba osoba : ekstensjaOsob){
            System.out.println(osoba);
        }
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
    private void setId(long id) {
        this.id = id;
    }
    /**
     * Gets imiona
     * @return String
     */
    @Basic
    public String getImiona(){
        return this.imiona;
    }
    /**
     * Sets imiona
     * @param imiona
     */
    public void setImiona(String imiona) {
        this.imiona = imiona;
    }
    /**
     * Gets nazwisko
     * @return String
     */
    @Basic
    public String getNazwisko(){
        return this.nazwisko;
    }
    /**
     * Sets nazwisko
     * @param nazwisko
     */
    public void setNazwisko(String nazwisko){
        this.nazwisko = nazwisko;
    }
    /**
     * Gets telefon
     * @return String
     */
    @Basic
    public String getTelefon(){
        return this.telefon;
    }
    /**
     * Sets telefon
     * @param telefon
     * @throws Exception
     */
    public void setTelefon(String telefon) throws Exception {
        //sprawdzic czy dziala pozniej
        Pattern pattern = Pattern.compile("[0-9]{3}[-\\s]?[0-9]{3}[-\\s]?[0-9]{3}");
        Matcher matcher = pattern.matcher(telefon);
        if(matcher.matches()){
            this.telefon = telefon;
        } else {
            throw new Exception("Telefon ma zły format: " + telefon);
        }
    }
    /**
     * Gets ekstensjaOsob
     * @return List
     */
    public static List<Osoba> getEkstensjaOsob() {
        return ekstensjaOsob;
    }
    /**
     * Sets ekstensjaOsob
     * @param ekstensjaOsob
     */
    public static void setEkstensjaOsob(List<Osoba> ekstensjaOsob) {
        Osoba.ekstensjaOsob = ekstensjaOsob;
    }
    /**
     * Overrides toString method
     * @return String
     */
    @Override
    public String toString() {
        return "\nImie/imiona: "+getImiona()+"\nNazwisko: "+getNazwisko()+"\nTelefon: "+getTelefon();
    }
}
