package Klasy;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
/**
 * Klient class
 * @see java.lang.Object
 */
@Entity(name = "Klient")
public class Klient extends Osoba{
    private List<Adres> listaAdresow = new ArrayList<>();
    private List<Zamowienie> listaZamowien = new ArrayList<>();

    private Klient(){}
    /**
     * Constructor
     * @param imiona
     * @param nazwisko
     * @param telefon
     * @throws Exception
     */
    public Klient(String imiona, String nazwisko, String telefon) throws Exception {
        super(imiona, nazwisko, telefon);
    }
    /**
     * Gets listaAdresow
     * Association
     *
     * @return List
     */
    @OneToMany(
            mappedBy = "klient",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch= FetchType.EAGER)
    public List<Adres> getListaAdresow(){
        return listaAdresow;
    }
    public void setListaAdresow(List<Adres> listaAdresow) {
        this.listaAdresow = listaAdresow;
    }

    /**
     * Adds adres to listaAdresow
     * @param adres
     */
    public void dodajAdres(Adres adres) {
        if(!listaAdresow.contains(adres)){
            listaAdresow.add(adres);
            adres.setKlient(this);
        }
    }
    /**
     * Removes adres from listaAdresow
     * @param adres
     */
    public void usunAdres(Adres adres) {
        listaAdresow.remove(adres);
    }
    /**
     * Place recent adres as the newest in list
     * @param adres
     */
    public void ustawAdres(Adres adres){
        //jezeli nie jest ostatnim dodanym adresem to przestawamy go jako ostatni
        if(!listaAdresow.get(listaAdresow.size()-1).equals(adres)) {
            listaAdresow.remove(adres);
            listaAdresow.add(adres);
        }
    }
    /**
     * Gets listaZamowien
     * Association
     *
     * @return List
     */
    @OneToMany(
            mappedBy = "klient",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public List<Zamowienie> getListaZamowien(){
        return this.listaZamowien;
    }
    /**
     * Sets listaZamowien
     * @param listaZamowien
     */
    public void setListaZamowien(List<Zamowienie> listaZamowien){
        this.listaZamowien = listaZamowien;
    }
    /**
     * Adds zamowienie to listaZamowien
     * @param zamowienie
     */
    public void dodajZamowienie(Zamowienie zamowienie){
        this.listaZamowien.add(zamowienie);
    }
    /**
     * Removes zamowienie from listaZamowien
     * @param zamowienie
     */
    public void usunZamowienie(Zamowienie zamowienie){
        this.listaZamowien.remove(zamowienie);
    }
    /**
     * Shows zamowienia
     */
    public void pokazZamowienia(){
        System.out.println("Zamowienie klienta "+getImiona().toString()+": ");
        for (Zamowienie zamowienie: listaZamowien) {
            System.out.println("\t"+zamowienie);
        }
    }
    /**
     * Overrides toString method
     */
    public String toString(){
        return super.toString()+"\nAdres: "+getListaAdresow();
    }

}
