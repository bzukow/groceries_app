package Klasy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
//import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Umowa class
 * @see java.lang.Object
 */
@Entity(name = "Umowa")
public class Umowa {
    private long id;
    private Date dataPodpisania;
    private Kierownik kierownik;
    private Firma firma;
    private static List<Umowa> ekstensjaUmow = new ArrayList<>();
    //private File plikUmowy;
    private Umowa(){}

    /**
     * Constructor
     * @param firma
     * @param kierownik
     */
    private Umowa(Firma firma, Kierownik kierownik) {
        this.firma = firma;
        this.kierownik = kierownik;
        dataPodpisania = new Date();
        dodajUmowe(this);
    }


//    private Umowa(File plikUmowy){
//        setPlikUmowy(plikUmowy);
//        dataPodpisania = new Date();
//    }

    /**
     * Create Umowe
     * @param firma
     * @param kierownik
     * @return
     * @throws Exception
     */
    public static Umowa stworzUmowe(Firma firma, Kierownik kierownik) throws Exception {
        if(firma == null) throw new Exception("Podana firma nie istnieje w systemie!");
        Umowa umowa = new Umowa(firma, kierownik);
        kierownik.dodajUmoweKierownika(umowa);
        firma.dodajUmowe(umowa);
        return umowa;
    }

    /**
     * Show extention
     */
    public static void pokazEkstensjeUmow() {
        System.out.println("Ekstensja klasy Umow: ");
        for(Umowa umowa : ekstensjaUmow){
            System.out.println(umowa);
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
     * Gets dataPodpisaniaUmowy
     * @return Date
     */
    @Basic
    public Date getDataPodpisania() {
        return this.dataPodpisania;
    }

    /**
     * Sets dataPodpisania
     * @param dataPodpisania
     */
    public void setDataPodpisania(Date dataPodpisania) {
        this.dataPodpisania = dataPodpisania;
    }

    /**
     * Gets Kierownik
     * Association
     *
     * @return Kierownik
     */
    @ManyToOne
    public Kierownik getKierownik() {
        return kierownik;
    }

    /**
     * Sets kierownik
     * @param kierownik
     */
    public void setKierownik(Kierownik kierownik) {
        if(this.kierownik != null){
            this.kierownik.usunUmoweKierownika(this);
        }
        this.kierownik = kierownik;
        this.kierownik.dodajUmoweKierownika(this);
    }

    /**
     * Sets extention
     * @param ekstensjaUmow
     */
    public static void setEkstensjaUmow(List<Umowa> ekstensjaUmow) {
        Umowa.ekstensjaUmow = ekstensjaUmow;
    }

    /**
     * Gets extention
     * @return List of Umowa
     */
    public static List<Umowa> getEkstensjaUmow() {
        return ekstensjaUmow;
    }

    /**
     * Adds umowa
     * @param umowa
     */
    private static void dodajUmowe(Umowa umowa) {
        ekstensjaUmow.add(umowa);
    }

    /**
     * Removes umowa
     * @param umowa
     */
    private static void usunUmowe(Umowa umowa) {
        ekstensjaUmow.remove(umowa);
    }

    //    public void setPlikUmowy(File plikUmowy) {
//        this.plikUmowy = plikUmowy;
//    }
//    public File getPlikUmowy() {
//        return this.plikUmowy;
//    }

    /**
     * Gets firma
     * Association
     *
     * @return Firma
     */
    @ManyToOne
    @JoinColumn(name="fk_firma")
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
     * Overrides toString method
     */
    public String toString(){
        return "Umowa nr. "+id+" podpisana przez kierownika: "+kierownik.getImiona()+" "+kierownik.getNazwisko()+" z firmą "+firma.getNazwaFirmy();
    }
}
