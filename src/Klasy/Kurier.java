package Klasy;

import Klasy.Osoba;
import Klasy.Zamowienie;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
/**
 * Kurier class
 * @see java.lang.Object
 */
@Entity(name = "Kurier")
public class Kurier extends Osoba {
    String numerLicencji;
    private List<Zamowienie> listaZamowien = new ArrayList<>();

    private Kurier(){}
    /**
     * Constructor
     * @param name (required)
     * @param nazwisko (required)
     * @param telefon (required)
     * @param numerLicencji (required)
     * @throws Exception
     */
    public Kurier(String name, String nazwisko, String telefon, String numerLicencji) throws Exception {
        super(name, nazwisko, telefon);
        this.numerLicencji = numerLicencji;
    }
    /**
     * Gets listaZamowien
     * Associaion
     *
     * @return list of Zamowienie
     */
    @OneToMany(
            mappedBy = "kurier",
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
     * Adds zamowienie to listaZamowien
     * @param zamowienie
     */
    public void dodajZamowienie(Zamowienie zamowienie){
        listaZamowien.add(zamowienie);
    }

    /**
     * Removes zamowienie from listaZamowien
     * @param zamowienie
     */
    public void usunZamowienie(Zamowienie zamowienie){
        listaZamowien.remove(zamowienie);
    }
    /**
     * Gets numerLicencji
     * @return String
     */
    @Basic
    public String getNumerLicencji() {
        return numerLicencji;
    }
    /**
     * Sets numerLicencji
     * @param numerLicencji
     */
    public void setNumerLicencji(String numerLicencji) {
        this.numerLicencji = numerLicencji;
    }
    /**
     * Sets status Zamowienia as Odebrane
     * @param zamowienie
     */
    public void potwierdzDostarczenieDostawy(Zamowienie zamowienie) throws Exception {
        zamowienie.zamowienieOdebrane();
    }
}
