package Klasy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Adres class
 * @see java.lang.Object
 */
@Entity(name = "Adres")
public class Adres {
    private long id;

    private String ulica;
    private int numerBloku;
    private int numerMieszkania;
    private String kodPocztowy;
    private String miasto;
    private int numerDomu;

    private Klient klient;
    private List<Zamowienie> listaZamowien = new ArrayList<>();
    private Firma firma;
    private static List<Adres> ekstensjaAdresow = new ArrayList<>();
    private Adres(){}
    /**
     * Adres constructor
     * @param ulica (required)
     * @param numerBloku (required)
     * @param numerMieszkania (required)
     * @param kodPocztowy (required)
     * @param miasto (required)
     */
    public Adres(String ulica, int numerBloku, int numerMieszkania, String kodPocztowy, String miasto){
        setUlica(ulica);
        setNumerBloku(numerBloku);
        setNumerMieszkania(numerMieszkania);
        setKodPocztowy(kodPocztowy);
        setMiasto(miasto);
        dodajAdres(this);
    }
    /**
     * Adres constructor
     * @param ulica (required)
     * @param numerDomu (required)
     * @param kodPocztowy (required)
     * @param miasto (required)
     */
    public Adres(String ulica, int numerDomu, String kodPocztowy, String miasto){
        setUlica(ulica);
        setNumerDomu(numerDomu);
        setKodPocztowy(kodPocztowy);
        setMiasto(miasto);
        dodajAdres(this);
    }
    /**
     * Shows extension
     */
    public static void pokazEkstensjeAdresow() {
        System.out.println("Ekstensja klasy Adres: ");
        for(Adres adres : ekstensjaAdresow){
            System.out.println(adres);
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
    /**
     * Sets id
     * @param id
     */
    private void setId(long id) {
        this.id = id;
    }
    /**
     * Gets numerDomu
     * @return int
     */
    @Basic
    public int getNumerDomu() {
        return numerDomu;
    }
    /**
     * Sets numerDomu
     * @param numerDomu
     */
    public void setNumerDomu(int numerDomu) {
        this.numerDomu = numerDomu;
    }
    /**
     * Gets ulica
     * @return String
     */
    @Basic
    public String getUlica(){
        return ulica;
    }
    /**
     * Sets ulica
     * @param ulica
     */
    public void setUlica(String ulica){
        this.ulica = ulica;
    }
    /**
     * Gets numerBloku
     * @return int
     */
    @Basic
    public int getNumerBloku(){
        return numerBloku;
    }
    /**
     * Sets numerBloku
     * @param numerBloku
     */
    public void setNumerBloku(int numerBloku){
        this.numerBloku = numerBloku;
    }
    /**
     * Gets kodPocztowy
     * @return String
     */
    @Basic
    public String getKodPocztowy(){
        return kodPocztowy;
    }
    /**
     * Sets kodPocztowy
     * @param kodPocztowy
     */
    public void setKodPocztowy(String kodPocztowy){
        this.kodPocztowy = kodPocztowy;
    }

    /**
     * Gets miasto
     * @return String
     */
    @Basic
    public String getMiasto(){
        return miasto;
    }
    /**
     * Sets miasto
     * @param miasto
     */
    public void setMiasto(String miasto){
        this.miasto = miasto;
    }
    /**
     * Gets numerMieszkania
     * @return int
     */
    @Basic
    public int getNumerMieszkania() {
        return numerMieszkania;
    }
    /**
     * Sets numerMieszkania
     * @param numerMieszkania
     */
    public void setNumerMieszkania(int numerMieszkania) {
        this.numerMieszkania = numerMieszkania;
    }
    /**
     * Gets listaZamowien
     * Association
     *
     * @return List
     */
    @OneToMany(
            mappedBy = "adres",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public List<Zamowienie> getListaZamowien() {
        return listaZamowien;
    }
    /**
     * Sets listaZamowien
     * @param listaZamowien
     */
    public void setListaZamowien(List<Zamowienie> listaZamowien) {
        this.listaZamowien = listaZamowien;
    }
    /**
     * Gets klient
     * Association
     *
     * @return Klient
     */
    @ManyToOne
    public Klient getKlient() {
        return klient;
    }
    /**
     * Sets klient
     * @param klient
     */
    protected void setKlient(Klient klient){
        this.klient = klient;
    }
    /**
     * Gets firma
     * Association
     *
     * @return Firma
     */
    @OneToOne(mappedBy = "adresFirmy")
    public Firma getFirma() {
        return firma;
    }
    /**
     * Sets firma
     * @param firma
     */
    public void setFirma(Firma firma) {
        this.firma = firma;
    }
    /**
     * Gets ekstensjaAdresow     *
     * @return ekstensjaAdresow
     */
    public static List<Adres> getEkstensjaAdresow() {
        return ekstensjaAdresow;
    }
    /**
     * Sets ekstensjaAdresow1
     * @param ekstensjaAdresow1
     */
    public static void setEkstensjaAdresow(List<Adres> ekstensjaAdresow1) {
        ekstensjaAdresow = ekstensjaAdresow1;
    }

    /**
     * Adds address to extension
     * @param adres
     */
    private void dodajAdres(Adres adres) {
        ekstensjaAdresow.add(adres);
    }
    /**
     * Removes address from extension
     * @param adres
     */
    private void usunAdres(Adres adres) {
        ekstensjaAdresow.remove(adres);
    }
    /**
     * Overrides toString method
     * @return String
     */
    public String toString(){
        if(getNumerDomu() != 0){
            return "ul. "+getUlica()+" "+getNumerDomu()+", "+getKodPocztowy()+" "+getMiasto();
        } else {
            return "ul. "+getUlica()+" "+getNumerBloku()+"/"+getNumerMieszkania()+", "+getKodPocztowy()+" "+getMiasto();
        }
    }
}
