package Klasy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Zamowienie class
 * @see java.lang.Object
 */
@Entity(name = "Zamowienie")
public class Zamowienie{
    private long id;
    public enum StatusZamowienia{ZAMOWIENIE_ZLOZONE, ZAMOWIENIE_OPLACONE, ZAMOWIENIE_WYSLANE, ZAMOWIENIE_ODEBRANE, ZAMOWIENIE_ODRZUCONE, ZAMOWIENIE_PRZYGOTOWANE};

    private LocalDate dataZamowienia;
    private StatusZamowienia statusZamowienia;
    private Integer ocenaZamowienia;
    private float cenaZamowienia;

    private Klient klient;

    private Adres adres;
    private List<IloscProduktuWZamowieniu> listaIloscProduktuWZamowieniu = new ArrayList<>();
    private Kurier kurier;

    private static List<Zamowienie> ekstensjaZamowien = new ArrayList<>();

    public Zamowienie(){}

    /**
     * Constructor
     * @param klient
     * @param adres
     * @param listaProduktow
     * @throws Exception
     */
    public Zamowienie(Klient klient, Adres adres, Map<Produkt, Integer> listaProduktow) throws Exception {
        this.klient = klient;
        this.adres = adres;
        setListaIloscProduktuWZamowieniu(listaProduktow);
        setDataZamowienia(LocalDate.now());
        zamowienieZlozone();
        dodajZamowienie(this);
    }

    /**
     * Sets List of products in Zamowienie
     * @param listaProduktow
     */
    private void setListaIloscProduktuWZamowieniu(Map<Produkt, Integer> listaProduktow) {
        float cena = 0.0f;
        for (Map.Entry<Produkt, Integer> entry : listaProduktow.entrySet()) {
            new IloscProduktuWZamowieniu(entry.getKey(), this, entry.getValue());
            cena += entry.getKey().getCena()*entry.getValue();
        }
        this.cenaZamowienia = cena;
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
     * Gets Adres
     * Association
     *
     * @return Adres
     */
    @ManyToOne
    public Adres getAdres() {
        return adres;
    }

    /**
     * Sets Adress
     * @param adres
     */
    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    /**
     * Adds Zamowienie to extention
     * @param zamowienie
     */
    public static void dodajZamowienie(Zamowienie zamowienie) {
        ekstensjaZamowien.add(zamowienie);
    }

    /**
     * Removes Zamowienie from extention
     * @param zamowienie
     */
    public static void usunZamowienie(Zamowienie zamowienie) {
        ekstensjaZamowien.remove(zamowienie);
    }

    /**
     * Show extention
     */
    public static void pokazEkstensjeZamowienia() {
        System.out.println("Ekstensja klasy Zamowien: ");
        for(Zamowienie zamowienie : ekstensjaZamowien){
            System.out.println(zamowienie);
        }

    }

    /**
     * Gets dataZamowienia
     * @return LocalDate
     */
    @Basic
    public LocalDate getDataZamowienia() {
        return dataZamowienia;
    }

    /**
     * Sets dataZamowienia
     * @param dataZamowienia
     */
    public void setDataZamowienia(LocalDate dataZamowienia) {
        this.dataZamowienia = dataZamowienia;
    }

    /**
     * Gets OcenaZamowienia
     * @return
     */
    @Basic
    public Integer getOcenaZamowienia(){
        return ocenaZamowienia;
    }

    /**
     * Sets ocenaZamowienia
     * @param ocena
     */
    public void setOcenaZamowienia(Integer ocena) {
//        if(ocena != null){
//            throw new Exception("Zamówienie zostało już ocenione!");
//        } else {
//            if(this.statusZamowienia == StatusZamowienia.ZAMOWIENIE_ODEBRANE){
//                ocenaZamowienia = ocena;
//            } else {
//                throw new Exception("Nie można ocenić zamówienia, które nie zostało jeszcze odebrane przez klienta!");
//            }
//        }
        this.ocenaZamowienia = ocena;
    }

    /**
     * Gets klient
     * Association
     *
     * @return Klient
     */
    @ManyToOne
    public Klient getKlient(){
        return klient;
    }

    /**
     * Sets klient
     * @param klient
     */
    public void setKlient(Klient klient){
        this.klient = klient;
    }

    /**
     * Gets CenaZamowienia
     * @return float
     */
    @Basic
    public float getCenaZamowienia(){
        return this.cenaZamowienia;
    }

    /**
     * Sets cenaZamowienia
     * @param cenaZamowienia
     */
    private void setCenaZamowienia(float cenaZamowienia) {
        this.cenaZamowienia = cenaZamowienia;
    }

    /**
     * Gets ListaProduktuWZamowieniu
     * @return list of amount
     */
    @OneToMany(
            mappedBy = "zamowienieProduktu",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public List<IloscProduktuWZamowieniu> getListaIloscProduktuWZamowieniu(){
        return this.listaIloscProduktuWZamowieniu;
    }

    /**
     * Sets iloscProduktuWZamowieniu
     * @param iloscProduktuWZamowieniu
     */
    public void setListaIloscProduktuWZamowieniu(List<IloscProduktuWZamowieniu> iloscProduktuWZamowieniu){
        this.listaIloscProduktuWZamowieniu = iloscProduktuWZamowieniu;
    }

    /**
     * Gets status zamowienia
     * @return Status as Enum
     */
    @Enumerated
    public StatusZamowienia getStatusZmowienia() {
        return statusZamowienia;
    }

    /**
     * Sets statusZamowienia
     * @param statusZmowienia
     * @throws Exception
     */
    public void setStatusZmowienia(StatusZamowienia statusZmowienia) throws Exception {
        if(this.statusZamowienia == StatusZamowienia.ZAMOWIENIE_ODRZUCONE){
            throw new Exception("Zamówienie zostało już odrzucone!");
        }
        this.statusZamowienia = statusZmowienia;
    }

    /**
     * Gets Kurier
     * Association
     *
     * @return
     */
    @ManyToOne
    public Kurier getKurier() {
        return kurier;
    }

    /**
     * Sets Kurier
     * @param kurier
     */
    public void setKurier(Kurier kurier) {
        this.kurier = kurier;
    }

    /**
     * Sets statusZamowienia as Zlozone
     * @throws Exception
     */
    public void zamowienieZlozone() throws Exception{
        setStatusZmowienia(StatusZamowienia.ZAMOWIENIE_ZLOZONE);
    }

    /**
     * Sets statusZamowienia as Wyslane
     * @throws Exception
     */
    public void zamowienieWyslane() throws Exception{
        setStatusZmowienia(StatusZamowienia.ZAMOWIENIE_WYSLANE);
    }

    /**
     * Sets statusZamowienia as Odebrane
     * @throws Exception
     */
    public void zamowienieOdebrane() throws Exception{
        setStatusZmowienia(StatusZamowienia.ZAMOWIENIE_ODEBRANE);
    }

    /**
     * Sets statusZamowienia as Odrzucone
     * @throws Exception
     */
    public void zamowienieOdrzucone() throws Exception{
        setStatusZmowienia(StatusZamowienia.ZAMOWIENIE_ODRZUCONE);
    }

    /**
     * Sets statusZamowienia as Przygotowane
     * @throws Exception
     */
    public void zamowieniePrzygotowane() throws Exception{
        setStatusZmowienia(StatusZamowienia.ZAMOWIENIE_PRZYGOTOWANE);
    }

    /**
     * Sets extention
     * @param ekstensjaZamowien
     */
    public static void setEkstensjaZamowien(List<Zamowienie> ekstensjaZamowien) {
        Zamowienie.ekstensjaZamowien = ekstensjaZamowien;
    }

    /**
     * Gets extention
     * @return List of Zamowienie
     */
    public static List<Zamowienie> getEkstensjaZamowien() {
        return ekstensjaZamowien;
    }
    /**
     * Overrides toString method
     */
    public String toString(){
        String result = "Zamowienie [ID: "+id+"] klienta: "+getKlient().getImiona();
        result += "\nLista produktow: ";
        for (IloscProduktuWZamowieniu iloscPWZ: listaIloscProduktuWZamowieniu) {
            result+="\n\t"+iloscPWZ.getProduktWZamowieniu().getNazwa()
                    + " x"+iloscPWZ.getIloscProduktu();
        }
        result+="\nZamówione na adres: "+adres;
        return result;
    }
}
