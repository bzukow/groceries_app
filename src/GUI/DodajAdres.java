package GUI;
import Klasy.*;
import Controller.Controller;
import org.hibernate.Session;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Font;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Barbara
 */
class DodajAdres {
    private JFrame frmDodajAdres;
    private JTextField txtUlicaINumer;
    private JTextField txtNumerMieszkania;
    private JTextField txtMiasto;
    private JTextField txtKodPocztowy;
    private JTextField txtNumerDomuBloku;
    private ZamowienieFrame zamowienieFrame;
    private ZmienAdres zmienAdres;

    /**
     * Create the application.
     * @param zamowienieFrame dostęp do frame'u z zamowieniem
     * @param zmienAdres dostęp do frame'u z listą adresów
     */

    DodajAdres(ZamowienieFrame zamowienieFrame, ZmienAdres zmienAdres) {
        this.zmienAdres = zmienAdres;
        this.zamowienieFrame = zamowienieFrame;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frmDodajAdres = new JFrame();
        frmDodajAdres.setTitle("Dodaj adres");
        frmDodajAdres.getContentPane().setBackground(new Color(102, 102, 102));
        frmDodajAdres.setBounds(100, 100, 394, 324);
        frmDodajAdres.setLocationRelativeTo(null);

        txtUlicaINumer = new JTextField();
        txtUlicaINumer.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        txtUlicaINumer.setForeground(new Color(204, 204, 204));
        txtUlicaINumer.setBackground(new Color(153, 153, 153));
        txtUlicaINumer.setToolTipText("");
        txtUlicaINumer.setColumns(10);

        txtNumerMieszkania = new JTextField();
        txtNumerMieszkania.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        txtNumerMieszkania.setForeground(new Color(204, 204, 204));
        txtNumerMieszkania.setBackground(new Color(153, 153, 153));
        txtNumerMieszkania.setColumns(10);

        txtMiasto = new JTextField();
        txtMiasto.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        txtMiasto.setForeground(new Color(204, 204, 204));
        txtMiasto.setBackground(new Color(153, 153, 153));
        txtMiasto.setColumns(10);

        txtKodPocztowy = new JTextField();
        txtKodPocztowy.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        txtKodPocztowy.setForeground(new Color(204, 204, 204));
        txtKodPocztowy.setBackground(new Color(153, 153, 153));
        txtKodPocztowy.setColumns(10);

        JButton btnAnuluj = new JButton("Anuluj");
        btnAnuluj.setBackground(new Color(197, 56, 71));
        btnAnuluj.setForeground(new Color(204, 204, 204));
        btnAnuluj.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        btnAnuluj.addActionListener(e-> frmDodajAdres.dispose());

        JButton btnDodaj = new JButton("Dodaj");
        btnDodaj.setBackground(new Color(197, 56, 71));
        btnDodaj.setForeground(new Color(204, 204, 204));
        btnDodaj.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        btnDodaj.addActionListener(e->{
            //WALIDACJA
            Pattern pattern = Pattern.compile("[a-zA-Z\\s]*");

            Matcher matcher = pattern.matcher(txtMiasto.getText());
            boolean matchesMiasto = matcher.matches();
            matcher = pattern.matcher(txtUlicaINumer.getText());
            boolean matchesUlica = matcher.matches();

            pattern = Pattern.compile("\\d");
            matcher = pattern.matcher(txtNumerDomuBloku.getText());
            boolean matchesNumerDomu = matcher.matches();

            matcher = pattern.matcher(txtNumerMieszkania.getText());
            boolean matchesNumerMieszkania = matcher.matches();

            pattern = Pattern.compile("[\\s]*");
            matcher = pattern.matcher(txtNumerMieszkania.getText());
            boolean matchesSpacerNumerMieszkania = matcher.matches();

            pattern = Pattern.compile("[0-9\\-\\s]*");
            matcher = pattern.matcher(txtKodPocztowy.getText());
            boolean matchesKodPocztowy = matcher.matches();

            if(        matchesMiasto
                    && matchesUlica
                    && matchesNumerDomu
                    &&(matchesNumerMieszkania || txtNumerMieszkania.getText().equals("") || matchesSpacerNumerMieszkania)
                    && matchesKodPocztowy){
                //zakladamy ze nie dom/blok jest tylko liczbą
                Adres adres;
                if(txtNumerMieszkania.getText().equals("")){
                    adres = new Adres(txtUlicaINumer.getText(), Integer.parseInt(txtNumerDomuBloku.getText()), txtKodPocztowy.getText(), txtMiasto.getText());
                } else {
                    adres = new Adres(txtUlicaINumer.getText(), Integer.parseInt(txtNumerDomuBloku.getText()), Integer.parseInt(txtNumerMieszkania.getText()), txtKodPocztowy.getText(), txtMiasto.getText());
                }
                zamowienieFrame.getKlient().dodajAdres(adres);
                zamowienieFrame.getKlient().ustawAdres(adres);
                Session s = Controller.getSession();
                s.beginTransaction();

                s.save(adres);
                s.update(zamowienieFrame.getKlient());

                try {
                    Zamowienie noweZamowienie = new Zamowienie(zamowienieFrame.getKlient(), adres, zamowienieFrame.getStronaGlowna().getKoszyk());

                    s.save(noweZamowienie);
                    for(IloscProduktuWZamowieniu i : IloscProduktuWZamowieniu.getEkstensjaIlosciProduktuWZamowieniu()){
                        s.saveOrUpdate(i);
                    }
                    for(Produkt i : Produkt.getEkstensjaProduktow()){
                        s.update(i);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                s.getTransaction().commit();

                frmDodajAdres.dispose();

                zmienAdres.getModel().addElement(adres);
                zmienAdres.getListaAdresowList().setModel(zmienAdres.getModel());

                zmienAdres.getScrollAdresow().setViewportView(zmienAdres.getListaAdresowList());
            } else {
                JLabel label = new JLabel("Sprawdź poprawność wprowadzonych danych!");
                label.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
                label.setForeground(new Color(204, 204, 204));
                label.setBackground(new Color(102, 102, 102));

                JOptionPane jop = new JOptionPane();
                jop.setBackground(new Color(102, 102, 102));
                jop.setBounds(100, 100, 542, 183);

                UIManager.put("OptionPane.background", new Color(102, 102, 102));
                UIManager.put("Panel.background", new Color(102, 102, 102));
                JOptionPane.showMessageDialog(null,label,"Niepoprawne dane!",JOptionPane.WARNING_MESSAGE);
            }

        });

        JLabel lblDodajNowyAdres = new JLabel("Dodaj nowy adres:");
        lblDodajNowyAdres.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
        lblDodajNowyAdres.setForeground(new Color(204, 204, 204));

        JLabel lblUlica = new JLabel("Ulica*");
        lblUlica.setForeground(new Color(204, 204, 204));
        lblUlica.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));

        JLabel lblNumerDomubloku = new JLabel("Numer domu/bloku*");
        lblNumerDomubloku.setForeground(new Color(204, 204, 204));
        lblNumerDomubloku.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));

        JLabel lblNumerMieszkania = new JLabel("Numer mieszkania");
        lblNumerMieszkania.setForeground(new Color(204, 204, 204));
        lblNumerMieszkania.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));

        JLabel lblMiasto = new JLabel("Miasto*");
        lblMiasto.setForeground(new Color(204, 204, 204));
        lblMiasto.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));

        JLabel lblKodpocztowy = new JLabel("Kod pocztowy*");
        lblKodpocztowy.setForeground(new Color(204, 204, 204));
        lblKodpocztowy.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));

        txtNumerDomuBloku = new JTextField();
        txtNumerDomuBloku.setForeground(new Color(204, 204, 204));
        txtNumerDomuBloku.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        txtNumerDomuBloku.setColumns(10);
        txtNumerDomuBloku.setBackground(new Color(153, 153, 153));
        GroupLayout groupLayout = new GroupLayout(frmDodajAdres.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addComponent(lblNumerDomubloku)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(lblUlica)
                                                                        .addComponent(lblNumerMieszkania)
                                                                        .addComponent(lblMiasto)
                                                                        .addComponent(lblKodpocztowy)
                                                                        .addComponent(btnAnuluj, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                                .addGap(61)
                                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                                        .addComponent(txtMiasto, Alignment.TRAILING, 180, 180, Short.MAX_VALUE)
                                                                                        .addComponent(txtUlicaINumer, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                                                                        .addComponent(txtKodPocztowy, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                                                                        .addComponent(txtNumerMieszkania, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                                                                        .addComponent(txtNumerDomuBloku, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
                                                                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                                .addComponent(btnDodaj)
                                                                                .addGap(54)))))
                                                .addContainerGap())
                                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                .addComponent(lblDodajNowyAdres)
                                                .addGap(122))))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(24)
                                .addComponent(lblDodajNowyAdres)
                                .addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblUlica)
                                        .addComponent(txtUlicaINumer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNumerDomubloku)
                                        .addComponent(txtNumerDomuBloku, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
                                .addGap(7)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNumerMieszkania)
                                        .addComponent(txtNumerMieszkania, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblMiasto)
                                        .addComponent(txtMiasto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblKodpocztowy)
                                        .addComponent(txtKodPocztowy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnAnuluj)
                                        .addComponent(btnDodaj))
                                .addContainerGap())
        );
        frmDodajAdres.getContentPane().setLayout(groupLayout);
    }

    JFrame getFrmDodajAdres() {
        return frmDodajAdres;
    }
}
