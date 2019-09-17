package Klasy;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.List;
/**
 * ProduktCertyfikat class
 * @see java.lang.Object
 */
@Entity(name="ProduktCertyfikat")
public class ProduktCertyfikat {
    private long id;
    //dataNadaniaCertyfikatu
    private Produkt produkt;
    private Certyfikat certyfikat;

    private static List<ProduktCertyfikat> ekstensjaProduktCertyfikat = new ArrayList<>();
    public ProduktCertyfikat(){}

    /**
     * Constructor
     * @param produkt
     * @param certyfikat
     */
    public ProduktCertyfikat(Produkt produkt, Certyfikat certyfikat){
        setProdukt(produkt);
        setCertyfikat(certyfikat);
        dodajEkstensjaProduktCertyfikat(this);
    }

    /**
     * Adds to extention
     * @param produktCertyfikat
     */
    private void dodajEkstensjaProduktCertyfikat(ProduktCertyfikat produktCertyfikat) {
        ekstensjaProduktCertyfikat.add(produktCertyfikat);
    }

    /**
     * Removes from extention
     * @param produktCertyfikat
     */
    private void usunkstensjaProduktCertyfikat(ProduktCertyfikat produktCertyfikat) {
        ekstensjaProduktCertyfikat.remove(produktCertyfikat);
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
     * Gets certyfiat
     * Association
     *
     * @return Certyfikat
     */
    @ManyToOne
    public Certyfikat getCertyfikat() {
        return certyfikat;
    }

    /**
     * Sets certyfikat
     * @param certyfikat
     */
    public void setCertyfikat(Certyfikat certyfikat) {
        this.certyfikat = certyfikat;
    }

    /**
     * Gets produkt
     * @return Produkt
     */
    @ManyToOne
    public Produkt getProdukt() {
        return produkt;
    }

    /**
     * Sets Produkt
     * @param produkt
     */
    public void setProdukt(Produkt produkt) {
        this.produkt = produkt;
    }
    /**
     * Overrides toString method
     */
    @Override
    public String toString() {
        return "Produkt: "+getProdukt().getNazwa()+" posiada certyfikat: "+getCertyfikat().getNazwaCertyfikatu();
    }

    /**
     * Gets extention
     * @return extention
     */
    public static List<ProduktCertyfikat> getEkstensjaProduktCertyfikat() {
        return ekstensjaProduktCertyfikat;
    }

    /**
     * Sets extention
     * @param ekstensjaProduktCertyfikat
     */
    public static void setEkstensjaProduktCertyfikat(List<ProduktCertyfikat> ekstensjaProduktCertyfikat) {
        ProduktCertyfikat.ekstensjaProduktCertyfikat = ekstensjaProduktCertyfikat;
    }

    /**
     * Shows extention
     */
    public static void pokazEkstensjeProduktCertyfikat() {
        System.out.println("Ekstensja klasy ProduktCertyfikat: ");
        for(ProduktCertyfikat produktCertyfikat : ekstensjaProduktCertyfikat){
            System.out.println(produktCertyfikat);
        }
    }
}
