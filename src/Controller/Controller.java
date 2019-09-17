package Controller;

import Klasy.*;
import Klasy.Firma;
import Klasy.Kierownik;
import Klasy.Pracownik;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.HashMap;
import java.util.Map;

public class Controller {

    private StandardServiceRegistry registry = null;
    private static SessionFactory sessionFactory = null;
    private static Session session = null;

    public Controller() {
        try {
            registry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                    .build();
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        }catch(Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public static Session getSession() {
        return session = sessionFactory.getCurrentSession();
    }

    public void uzupelnijDane() throws Exception {
        getSession();
        session.beginTransaction();

        Adres adres1 = new Adres("RandomStreet1", 1, "00-001", "RandomTown1");
        Adres adres2 = new Adres("RandomStreet2", 2, "00-002", "RandomTown2");
        Adres adres3 = new Adres("RandomStreet3", 2, "00-003", "RandomTown3");
        Adres adres4 = new Adres("RandomStreet4", 4, "00-004", "RandomTown4");

        Klient klient = new Klient("Jane", "Doe", "000-000-000");
        klient.dodajAdres(adres1);
        klient.dodajAdres(adres4);
        setKlient(klient);
        Pracownik pracownik1 = new Pracownik("John", "Doe", "000-000-011", 2500);
        Kierownik kierownik = new Kierownik(pracownik1);

        Certyfikat certyfikat1 = new Certyfikat("BIO1");
        Certyfikat certyfikat2 = new Certyfikat("BIO2");
        Certyfikat certyfikat3 = new Certyfikat("BIO3");
        Certyfikat certyfikat4 = new Certyfikat("BIO4");
        Certyfikat certyfikat5 = new Certyfikat("BIO5");
        Certyfikat certyfikat6 = new Certyfikat("BIO6");
        Certyfikat certyfikat7 = new Certyfikat("BIO7");
        Certyfikat certyfikat8 = new Certyfikat("BIO8");
        Certyfikat certyfikat9 = new Certyfikat("BIO9");
        Certyfikat certyfikat10 = new Certyfikat("BIO10");
        Certyfikat certyfikat11 = new Certyfikat("BIO11");

        Produkt produkt1 = new Produkt("Naturfrut Bób w strączkach (EKO)", 14.99f);
        Produkt produkt2 = new Produkt("Botwina firmy Tradycje Natury", 8.29f);
        Produkt produkt3 = new Produkt("Naturfrut Brokuł (EKO)",9.99f);
        Produkt produkt4 = new Produkt("Naturfrut Fasolka Szparagowa (EKO)", 12.99f);
        Produkt produkt5 = new Produkt("Marchew firmy Tradycje Natury", 7.29f);
        Produkt produkt6 = new Produkt("Naturfrut Buraki (EKO)",6.99f);

        produkt1.dodajCertyfikat(certyfikat1);
        produkt1.dodajCertyfikat(certyfikat8);
        produkt1.dodajCertyfikat(certyfikat9);
        produkt1.dodajCertyfikat(certyfikat10);
        produkt1.dodajCertyfikat(certyfikat11);
        produkt2.dodajCertyfikat(certyfikat1);
        produkt1.dodajCertyfikat(certyfikat7);
        produkt2.dodajCertyfikat(certyfikat7);
        produkt1.dodajCertyfikat(certyfikat3);
        produkt2.dodajCertyfikat(certyfikat2);
        produkt4.dodajCertyfikat(certyfikat6);
        produkt4.dodajCertyfikat(certyfikat9);
        produkt5.dodajCertyfikat(certyfikat1);
        produkt5.dodajCertyfikat(certyfikat11);
        produkt5.dodajCertyfikat(certyfikat10);
        produkt6.dodajCertyfikat(certyfikat5);
        produkt6.dodajCertyfikat(certyfikat8);
        produkt3.dodajCertyfikat(certyfikat4);

        Produkt.pokazEkstensje();
        certyfikat1.pokazProdukty();

        Firma firma1 = new Firma("Random firm", "000-000-001",adres2);
        firma1.dodajNowaUmowe(kierownik);
        Umowa.pokazEkstensjeUmow();
        Firma.pokazEkstensjeFirm();

        Map<Produkt, Integer> koszykProduktow = new HashMap<>();
        koszykProduktow.put(produkt1, 10);
        koszykProduktow.put(produkt2, 2);
        koszykProduktow.put(produkt3, 15);


        Zamowienie zamowienie1 = new Zamowienie(klient, klient.getListaAdresow().get(0), koszykProduktow);

        for(Adres adres : Adres.getEkstensjaAdresow()) {
            session.saveOrUpdate(adres);
        }
        Adres.pokazEkstensjeAdresow();
        for(Osoba osoba : Osoba.getEkstensjaOsob()) {
            session.saveOrUpdate(osoba);
        }
        for(Certyfikat certyfikat : Certyfikat.getEkstensjaCertyfikatow()) {
            session.saveOrUpdate(certyfikat);
        }
        for(Produkt produkt : Produkt.getEkstensjaProduktow()) {
            session.saveOrUpdate(produkt);
        }

        for(Zamowienie zamowienie : Zamowienie.getEkstensjaZamowien()) {
            session.saveOrUpdate(zamowienie);
        }

        for(IloscProduktuWZamowieniu iloscProduktuWZamowieniu : IloscProduktuWZamowieniu.getEkstensjaIlosciProduktuWZamowieniu()) {
            session.saveOrUpdate(iloscProduktuWZamowieniu);
        }

        for(Firma firma : Firma.getEkstensjaFirm()) {
            session.saveOrUpdate(firma);
        }
        for(Umowa umowa : Umowa.getEkstensjaUmow()){
            session.saveOrUpdate(umowa);
        }

        for(ProduktCertyfikat pc : ProduktCertyfikat.getEkstensjaProduktCertyfikat()){
            session.saveOrUpdate(pc);
        }

        session.getTransaction().commit();
        //session.close();
    }

    public void pokażDaneZBazy() {
        System.out.println("______________________");
        System.out.println("\nZ BAZY DANYCH");
        getSession();
        session.beginTransaction();

        Adres.setEkstensjaAdresow(session.createQuery("from Adres").list());
        Adres.pokazEkstensjeAdresow();

        Osoba.setEkstensjaOsob(session.createQuery("from Osoba").list());
        Osoba.pokazEkstensjeOsob();

        IloscProduktuWZamowieniu.setEkstensjaIlosciProduktuWZamowieniu(session.createQuery("from IloscProduktuWZamowieniu").list());
        IloscProduktuWZamowieniu.pokazEkstensjeIlosci();

        Zamowienie.setEkstensjaZamowien(session.createQuery("from Zamowienie").list());
        Zamowienie.pokazEkstensjeZamowienia();

        session.getTransaction().commit();
    }
    private Klient klient;

    public Klient getKlient() {
        getSession();
        session.beginTransaction();
        klient = (Klient) session.createQuery( "from Klient" ).list().get(0);
        session.getTransaction().commit();
        return klient;
    }

    private void setKlient(Klient klient) {
        this.klient = klient;
    }
}
