package GUI;
import Klasy.*;
import Controller.Controller;
import org.hibernate.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
/**
 * @author Barbara
 */
public class ZamowienieFrame {
    private JPanel panel;
    private JFrame frmZamwienie;
    private DziekujemyZaZamowienie panelDziekujemy;
    private AdresOstatniegoZamowienia panelAdresu;
    private JButton dalejButton;
    private JButton cofnijButton;
    private StronaGlowna stronaGlowna;
    private Klient klient;
    private Adres adres;

    /**
     * Create a frame
     * @param stronaGlowna
     */
    ZamowienieFrame(StronaGlowna stronaGlowna) {
        klient = stronaGlowna.getKlient();
        adres = klient.getListaAdresow().get(klient.getListaAdresow().size()-1);
        this.stronaGlowna = stronaGlowna;
        panel = new JPanel();

        panelAdresu = new AdresOstatniegoZamowienia(this);
        panelAdresu.setVisible(true);
        panel.add(panelAdresu);

        initialize(stronaGlowna.getKlient());
    }

    /**
     * Initialize the contents of the frame.
     * @param klient
     */
    private void initialize(Klient klient) {
        frmZamwienie = new JFrame();
        frmZamwienie.setTitle("Zamówienie");
        frmZamwienie.getContentPane().setBackground(new Color(102, 102, 102));

        panel.setBackground(new Color(153, 153, 153));

        JLabel zamowienieLabel = new JLabel("Zamówienie");
        zamowienieLabel.setForeground(new Color(204, 204, 204));
        zamowienieLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 28));

        cofnijButton = new JButton("Cofnij");
        cofnijButton.setBackground(new Color(197, 56, 71));
        cofnijButton.setForeground(new Color(204, 204, 204));
        cofnijButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        cofnijButton.addActionListener(e ->{
            frmZamwienie.dispose();
            stronaGlowna.getOknoGlowne().setVisible(true);
        });

        dalejButton = new JButton("Zatwierdz");
        dalejButton.setBackground(new Color(197, 56, 71));
        dalejButton.setForeground(new Color(204, 204, 204));
        dalejButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        dalejButton.addActionListener(e -> {
            try {
                Zamowienie noweZamowienie = new Zamowienie(klient, adres, stronaGlowna.getKoszyk());
                Session s = Controller.getSession();
                s.beginTransaction();

                s.save(noweZamowienie);
                for(IloscProduktuWZamowieniu i : IloscProduktuWZamowieniu.getEkstensjaIlosciProduktuWZamowieniu()){
                    s.saveOrUpdate(i);
                }
                for(Produkt i : Produkt.getEkstensjaProduktow()){
                    s.update(i);
                }
                for(Adres i : Adres.getEkstensjaAdresow()){
                    s.update(i);
                }
                s.getTransaction().commit();
                //s.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            panelAdresu.setVisible(false);
            setPanelDziekujemy(new DziekujemyZaZamowienie(adres));
            panelDziekujemy.setVisible(true);


            cofnijButton.setText("OK");
            dalejButton.setVisible(false);

            for(ActionListener al : cofnijButton.getActionListeners()){
                cofnijButton.removeActionListener(al);
            }

            cofnijButton.addActionListener(s->{
                frmZamwienie.dispose();
                stronaGlowna.getKoszyk().clear();
                StronaGlowna stronaGlowna = new StronaGlowna(klient);
                stronaGlowna.getOknoGlowne().setVisible(true);
            });
        });

        float cena = 0.0f;
        for (Map.Entry<Produkt, Integer> entry : stronaGlowna.getKoszyk().entrySet()) {
            cena += entry.getKey().getCena()*entry.getValue();
        }
        JLabel lblcznaCena = new JLabel("Cena: "+cena);
        lblcznaCena.setForeground(new Color(204, 204, 204));
        lblcznaCena.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
        lblcznaCena.setHorizontalAlignment(SwingConstants.RIGHT);

        GroupLayout groupLayout = new GroupLayout(frmZamwienie.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(26)
                                .addComponent(cofnijButton, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 357, Short.MAX_VALUE)
                                .addComponent(dalejButton)
                                .addGap(23))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                .addGap(63)
                                .addComponent(zamowienieLabel)
                                .addContainerGap(367, Short.MAX_VALUE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap(490, Short.MAX_VALUE)
                                .addComponent(lblcznaCena)
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(35)
                                                .addComponent(zamowienieLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lblcznaCena)
                                                .addGap(18)))
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 466, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cofnijButton)
                                        .addComponent(dalejButton))
                                .addContainerGap())
        );

        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0};
        gbl_panel.rowHeights = new int[]{0};
        gbl_panel.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        frmZamwienie.getContentPane().setLayout(groupLayout);
        frmZamwienie.setBounds(100, 100, 600, 670);
        frmZamwienie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmZamwienie.setLocationRelativeTo(null);
    }

    /**
     * Gets this frame
     * @return JFrame
     */
    JFrame getFrmZamwienie() {
        return frmZamwienie;
    }

    /**
     * Gets paneldziekujemy
     * @return JPanel
     */
    DziekujemyZaZamowienie getPanelDziekujemy() {
        return panelDziekujemy;
    }

    /**
     * Gets PanelAdresu
     * @return JPanel
     */
    AdresOstatniegoZamowienia getPanelAdresu() {
        return panelAdresu;
    }

    /**
     * Gets dalejButton
     * @return
     */
    JButton getDalejButton() {
        return dalejButton;
    }

    /**
     * Gets cofnijButton
     * @return
     */
    JButton getCofnijButton() {
        return cofnijButton;
    }

    /**
     * Gets klient
     * @return Klient
     */
    public Klient getKlient() {
        return klient;
    }

    /**
     * Gets Adres
     * @return Adres
     */
    public Adres getAdres() {
        return adres;
    }

    /**
     * Sets Adres
     * @param adres
     */
    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    /**
     * Gets mainView
     * @return StronaGlowna
     */
    StronaGlowna getStronaGlowna() {
        return stronaGlowna;
    }

    /**
     * Sets PanelDziekujemy
     * @param panelDziekujemy
     */
    void setPanelDziekujemy(DziekujemyZaZamowienie panelDziekujemy) {
        this.panelDziekujemy = panelDziekujemy;
        panel.add(panelDziekujemy);
    }
}
