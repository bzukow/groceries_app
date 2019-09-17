package Klasy;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
/**
 * Pracownik class
 * @see java.lang.Object
 */
@Entity(name = "Pracownik")
public class Pracownik extends Osoba {
    private double wynagrodzenie;
    private Date dataZatrudnienia;

    protected Pracownik(){}
    /**
     * Constructor
     * @param imiona (required)
     * @param nazwisko (required)
     * @param telefon (required)
     * @param wynagrodzenie (required)
     * @throws Exception
     */
    public Pracownik(String imiona, String nazwisko, String telefon, double wynagrodzenie) throws Exception {
        super(imiona, nazwisko, telefon);
        setWynagrodzenie(wynagrodzenie);
        dataZatrudnienia = new Date();
    }
    /**
     * Constructor
     * @param kierownik (required)
     * @param wynagrodzenie (required)
     * @throws Exception
     */
    public Pracownik(Kierownik kierownik, double wynagrodzenie) throws Exception {
        super(kierownik.getImiona(), kierownik.getNazwisko(), kierownik.getTelefon());
        setWynagrodzenie(wynagrodzenie);
        dataZatrudnienia = kierownik.getDataZatrudnienia();
    }
    /**
     * Sets default salary
     */
    private void setDefaultWynagrodzenie() {
        this.wynagrodzenie = 2500.0;
    }
    /**
     * Sets salary
     * @param wynagrodzenie
     */
    @Basic
    public void setWynagrodzenie(double wynagrodzenie) {
        this.wynagrodzenie = wynagrodzenie;
    }

    /**
     * Gets salary
     * @return double
     */
    public double getWynagrodzenie(){
        return this.wynagrodzenie;
    }
    /**
     * Gets dataZatrudnienia
     * @return Date
     */
    @Basic
    public Date getDataZatrudnienia(){
        return this.dataZatrudnienia;
    }
    /**
     * Sets dataZatrudnienia
     * @param dataZatrudnienia
     */
    public void setDataZatrudnienia(Date dataZatrudnienia){
        this.dataZatrudnienia = dataZatrudnienia;
    }
    /**
     * Calculaing how many years of work has a worker
     * @return int years of work
     */
    @Transient
    public int getLataPracy(){
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        calendar.setTime(dataZatrudnienia);
        return year - calendar.get(Calendar.YEAR);
    }
    /**
     * Overrides toString method
     */
    @Override
    public String toString() {
        return super.toString()+"\nPensja: "+wynagrodzenie;
    }
}
