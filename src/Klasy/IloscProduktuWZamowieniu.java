package Klasy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * IloscProduktuWZamowieniu class
 * @see java.lang.Object
 */
@Entity(name = "IloscProduktuWZamowieniu")
public class IloscProduktuWZamowieniu {
    private long id;

    private int iloscProduktu;

    private Produkt produktWZamowieniu;
    private Zamowienie zamowienieProduktu;

    private static List<IloscProduktuWZamowieniu> ekstensjaIlosciProduktuWZamowieniu = new ArrayList<>();

    private IloscProduktuWZamowieniu(){}
    /**
     * Constructor
     * @param produktWZamowieniu (required)
     * @param zamowienieProduktu (required)
     * @param iloscProduktu (required)
     */
    public IloscProduktuWZamowieniu(Produkt produktWZamowieniu, Zamowienie zamowienieProduktu, int iloscProduktu){
        setIloscProduktu(iloscProduktu);
        setProduktWZamowieniu(produktWZamowieniu);
        setZamowienieProduktu(zamowienieProduktu);
        dodajIloscProduktuWZamowieniu(this);
    }
    /**
     * Shows extention
     */
    public static void pokazEkstensjeIlosci() {
        System.out.println("Ekstensja klasy IloscProduktuWZamowieniu: ");
        for(IloscProduktuWZamowieniu ilosc : ekstensjaIlosciProduktuWZamowieniu){
            System.out.println(ilosc);
        }
    }
    /**
     * Adds iloscProduktuWZamowieniu to exention
     * @param iloscProduktuWZamowieniu
     */
    private void dodajIloscProduktuWZamowieniu(IloscProduktuWZamowieniu iloscProduktuWZamowieniu) {
        ekstensjaIlosciProduktuWZamowieniu.add(iloscProduktuWZamowieniu);
    }
    /**
     * Removes iloscProduktuWZamowieniu from exention
     * @param iloscProduktuWZamowieniu
     */
    private void usunIloscProduktuWZamowieniu(IloscProduktuWZamowieniu iloscProduktuWZamowieniu) {
        ekstensjaIlosciProduktuWZamowieniu.remove(iloscProduktuWZamowieniu);
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
     * Gets produkt
     * Asociation
     *
     * @return Produkt
     */
    @ManyToOne
    public Produkt getProduktWZamowieniu(){
        return this.produktWZamowieniu;
    }
    /**
     * Sets produktWZamowieniu
     * @param produktWZamowieniu
     */
    public void setProduktWZamowieniu(Produkt produktWZamowieniu) {
        this.produktWZamowieniu = produktWZamowieniu;
    }
    /**
     * Gets Zamowienie
     * Asociation
     *
     * @return Zamowienie
     */
    @ManyToOne
    public Zamowienie getZamowienieProduktu(){
        return this.zamowienieProduktu;
    }
    /**
     * Sets zamowienieProduktu
     * @param zamowienieProduktu
     */
    public void setZamowienieProduktu(Zamowienie zamowienieProduktu){
        this.zamowienieProduktu = zamowienieProduktu;
    }
    /**
     * Gets iloscProduktu
     * @return iloscProduktu
     */
    @Basic
    public int getIloscProduktu(){
        return this.iloscProduktu;
    }
    /**
     * Sets iloscProduktu
     * @param iloscProduktu
     */
    public void setIloscProduktu(int iloscProduktu){
        this.iloscProduktu = iloscProduktu;
    }

    /**
     * Gets extention
     * @return List;
     */
    public static List<IloscProduktuWZamowieniu> getEkstensjaIlosciProduktuWZamowieniu() {
        return ekstensjaIlosciProduktuWZamowieniu;
    }
    /**
     * Sets extention
     * @param ekstensjaIlosciProduktuWZamowieniu
     */
    public static void setEkstensjaIlosciProduktuWZamowieniu(List<IloscProduktuWZamowieniu> ekstensjaIlosciProduktuWZamowieniu) {
        IloscProduktuWZamowieniu.ekstensjaIlosciProduktuWZamowieniu = ekstensjaIlosciProduktuWZamowieniu;
    }
    /**
     * Overrides toString method
     * @return String;
     */
    @Override
    public String toString() {
        return "Produkt: "+produktWZamowieniu.getNazwa() + " w zamowieniu nr "+zamowienieProduktu.getId()+" x"+iloscProduktu;
    }
}
