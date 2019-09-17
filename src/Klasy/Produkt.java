package Klasy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Produkt class
 * @see java.lang.Object
 */
@Entity(name = "Produkt")
public class Produkt {
    private long id;

    private String nazwa;
    private float cena;

    private List<IloscProduktuWZamowieniu> listaIloscProduktuWZamowieniu = new ArrayList<>();
    private List<ProduktCertyfikat> listaCertyfikatow = new ArrayList<>();

    private static List<Produkt> ekstensjaProduktow = new ArrayList<>();

    public Produkt(){}
    /**
     * Constructor
     * @param nazwa (required)
     * @param cena (required)
     */
    public Produkt(String nazwa, float cena) {
        setNazwa(nazwa);
        setCena(cena);
        dodajProdukt(this);
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
     * Sets cena
     * @param cena
     */
    @Basic
    public void setCena(float cena) {
        this.cena = cena;
    }

    /**
     * Gets cena
     * @return float
     */
    public float getCena() {
        return cena;
    }

    /**
     * Adds product to extention
     * @param produkt
     */
    private static void dodajProdukt(Produkt produkt) {
        ekstensjaProduktow.add(produkt);
    }

    /**
     * Removes product from extention
     * @param produkt
     */
    private static void usunProdukt(Produkt produkt) {
        ekstensjaProduktow.remove(produkt);
    }

    /**
     * Shows extention
     */
    public static void pokazEkstensje() {
        System.out.println("Ekstensja klasy Klasy.Produkt: ");
        for(Produkt produkt : ekstensjaProduktow){
            System.out.println(produkt);
            System.out.println();
        }

    }

    /**
     * Gets listaCertyfikatow
     * Association
     *
     * @return
     */
    @OneToMany(mappedBy = "produkt", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
    public List<ProduktCertyfikat> getListaCertyfikatow() {
        return listaCertyfikatow;
    }

    /**
     * Sets listaCertyfikatow
     * @param listaCertyfikatow
     */
    public void setListaCertyfikatow(List<ProduktCertyfikat> listaCertyfikatow) {
        this.listaCertyfikatow = listaCertyfikatow;
    }

    /**
     * Adds certyfikat to list
     * @param certyfikat
     */
    public void dodajCertyfikat(Certyfikat certyfikat){
        listaCertyfikatow.add(new ProduktCertyfikat(this, certyfikat));
    }

    /**
     * Removes certyficate from product
     * @param certyfikat
     */
    public void usunCertyfikat(ProduktCertyfikat certyfikat){
        listaCertyfikatow.remove(certyfikat);
    }

    /**
     * Gets nazwa
     * @return
     */
    @Basic
    public String getNazwa(){
        return nazwa;
    }

    /**
     * Sets nazwa
     * @param nazwa
     */
    public void setNazwa(String nazwa){
        this.nazwa = nazwa;
    }

    /**
     * Gets listaProduktowWZamowieniu
     * Association
     *
     * @return List of amount: Products in Zamowienie
     */
    @OneToMany(
            mappedBy = "produktWZamowieniu",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public List<IloscProduktuWZamowieniu> getListaIloscProduktuWZamowieniu(){
        return this.listaIloscProduktuWZamowieniu;
    }

    /**
     * Sets list of amount between Produkt and Zamowienie
     * @param listaIloscProduktuWZamowieniu
     */
    public void setListaIloscProduktuWZamowieniu(List<IloscProduktuWZamowieniu> listaIloscProduktuWZamowieniu) {
        this.listaIloscProduktuWZamowieniu = listaIloscProduktuWZamowieniu;
    }
    /**
     * Adds amount to list
     * @param iloscPWZ
     */
    public void dodajIloscProduktuWZamowieniu(IloscProduktuWZamowieniu iloscPWZ){
        if(!this.getListaIloscProduktuWZamowieniu().contains(iloscPWZ)) {
            getListaIloscProduktuWZamowieniu().add(iloscPWZ);
            iloscPWZ.setProduktWZamowieniu(this);
        }
    }

    /**
     * Removes amount from list
     * @param iloscPWZ
     */
    public void usunIloscProduktuWZamowieniu(IloscProduktuWZamowieniu iloscPWZ){
        if(getListaIloscProduktuWZamowieniu().contains(iloscPWZ)) {
            getListaIloscProduktuWZamowieniu().remove(iloscPWZ);
            iloscPWZ.setProduktWZamowieniu(null);
        }
    }

    /**
     * Shows amount of produuct in Zamowienie
     */
    public void pokazIloscWZamowieniu(){
        System.out.println("Ilosc produktu "+getNazwa()+" w zamowieniach: ");
        for (IloscProduktuWZamowieniu iloscPWZ: listaIloscProduktuWZamowieniu) {
            System.out.println("\t"+iloscPWZ.getZamowienieProduktu()
                    + " - "+iloscPWZ.getIloscProduktu() + " sztuk");
        }
    }

    /**
     * Gets ekstensjaProduktow
     * @return List of products
     */
    public static List<Produkt> getEkstensjaProduktow() {
        return ekstensjaProduktow;
    }

    /**
     * Sets ekstensjaProduktow
     * @param ekstensjaProduktow
     */
    public static void setEkstensjaProduktow(List<Produkt> ekstensjaProduktow) {
        Produkt.ekstensjaProduktow = ekstensjaProduktow;
    }
    /**
     * Overrides toString method
     */
    public String toString() {
        String result = "Nazwa produktu: " + nazwa + "\nCena: " + cena + "\nLista Certyfikatow: \n\t";
        if (listaCertyfikatow == null) {
            result += "Brak certyfikatow";
        } else {
            for (ProduktCertyfikat cert : listaCertyfikatow) {
                result += cert.getCertyfikat() + "\n\t";
            }
        }
        return result;
    }
}