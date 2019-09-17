package Klasy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
//import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Certyfikat class
 * @see java.lang.Object
 */
@Entity(name = "Certyfikat")
public class Certyfikat {
    private long id;

    private String nazwaCertyfikatu;
    //private File plikCertyfikatu;

    public List<ProduktCertyfikat> produkty = new ArrayList<>();

    private static List<Certyfikat> ekstensjaCertyfikatow = new ArrayList<>();

    private Certyfikat(){}
    /**
     * Constructor
     * @param nazwaCertyfikatu (required)
     */
    public Certyfikat(String nazwaCertyfikatu){
        setNazwaCertyfikatu(nazwaCertyfikatu);
        dodajCertyfikat(this);
    }
    /**
     * Adds ceryfikat to extension
     * @param certyfikat
     */
    private void dodajCertyfikat(Certyfikat certyfikat) {
        ekstensjaCertyfikatow.add(certyfikat);
    }
    /**
     * Removes ceryfikat from extension
     * @param certyfikat
     */
    private void usunCertyfikat(Certyfikat certyfikat) {
        ekstensjaCertyfikatow.remove(certyfikat);
    }

    @Basic
    public String getNazwaCertyfikatu(){
        return this.nazwaCertyfikatu;
    }
    /**
     * Sets nazwaCertyfikatu
     * @param nazwaCertyfikatu
     */
    public void setNazwaCertyfikatu(String nazwaCertyfikatu){
        this.nazwaCertyfikatu = nazwaCertyfikatu;
    }
//    @Basic
//    public File getPlikCertyfikatu() {
//        return plikCertyfikatu;
//    }
//
//    public void setPlikCertyfikatu(File plikCertyfikatu) {
//        this.plikCertyfikatu = plikCertyfikatu;
//    }
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
     * Gets produkty
     * Association
     *
     * @return List
     */
    @OneToMany(mappedBy = "certyfikat", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    public List<ProduktCertyfikat> getProdukty() {
        return produkty;
    }
    /**
     * Sets produkty
     * @param produkty
     */
    public void setProdukty(List<ProduktCertyfikat> produkty) {
        this.produkty = produkty;
    }
    /**
     * Gets ekstensjaCertyfikatow
     * @return List
     */
    public static List<Certyfikat> getEkstensjaCertyfikatow() {
        return ekstensjaCertyfikatow;
    }
    /**
     * Sets ekstensjaCertyfikatow
     * @param ekstensjaCertyfikatow
     */
    public static void setEkstensjaCertyfikatow(List<Certyfikat> ekstensjaCertyfikatow) {
        Certyfikat.ekstensjaCertyfikatow = ekstensjaCertyfikatow;
    }
    /**
     * Overrides toString method
     * @return String
     */
    public String toString(){
        return nazwaCertyfikatu;
    }
    /**
     * Removes product from list
     * @param produktCertyfikat
     */
    public void usunProdukt(ProduktCertyfikat produktCertyfikat) {
        produkty.remove(produktCertyfikat);
    }
    /**
     * Adds product to list
     * @param produktCertyfikat
     */
    public void dodajProdukt(ProduktCertyfikat produktCertyfikat) {
        produkty.add(produktCertyfikat);
    }
    /**
     * Shows products that has this certificate
     */
    public void pokazProdukty(){
        System.out.println(zwrocProdukty());
    }
    /**
     * Returns products that has this certificate
     * @return String
     */
    public String zwrocProdukty(){
        String result = "";
        result += "Produkty z tym certyfikatem:";
        for(ProduktCertyfikat produktCertyfikat : produkty){
            result += "\n\t"+produktCertyfikat.getProdukt().getNazwa();
        }
        return result;
    }
}
