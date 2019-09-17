package Klasy;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Certyfikat class
 * @see java.lang.Object
 */
@Entity(name = "Kierownik")
public class Kierownik extends Pracownik{
    private Date dataAwansu;
    private List<Umowa> listaPodpisanychUmow = new ArrayList<>();

    protected Kierownik(){}
    /**
     * Constructor
     * @param pracownikDoAwansu (required)
     * @throws Exception
     */
    public Kierownik(Pracownik pracownikDoAwansu) throws Exception {
        super(pracownikDoAwansu.getImiona(), pracownikDoAwansu.getNazwisko(), pracownikDoAwansu.getTelefon(), pracownikDoAwansu.getWynagrodzenie());
        dataAwansu = new Date();
    }

    /**
     * Gets dataAwansu
     * @return Data
     */
    @Basic
    public Date getDataAwansu() {
        return dataAwansu;
    }
    /**
     * Sets dataAwansu
     * @param dataAwansu
     */
    public void setDataAwansu(Date dataAwansu) {
        this.dataAwansu = dataAwansu;
    }

    /**
     * Gets listaPodpisanychUmow
     * Association
     *
     * @return List
     */
    @OneToMany(
            mappedBy = "kierownik",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public List<Umowa> getListaPodpisanychUmow() {
        return listaPodpisanychUmow;
    }
    /**
     * Sets listaPodpisanychUmow
     * @param listaPodpisanychUmow
     */
    public void setListaPodpisanychUmow(List<Umowa> listaPodpisanychUmow) {
        this.listaPodpisanychUmow = listaPodpisanychUmow;
    }
    /**
     * Removes umowa from kierownik
     * @param umowa
     */
    public void usunUmoweKierownika(Umowa umowa){
        listaPodpisanychUmow.remove(umowa);
    }
    /**
     * Adds umowa to kierownik
     * @param umowa
     */
    protected void dodajUmoweKierownika(Umowa umowa){
        listaPodpisanychUmow.add(umowa);
    }
    /**
     * Overrides toString method
     */
    @Override
    public String toString() {
        return super.toString() + "\nData awansu: "+dataAwansu;
    }
}
