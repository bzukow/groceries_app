package GUI;
import Klasy.Klient;
import Klasy.Produkt;
import Controller.Controller;
import org.hibernate.Session;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author Barbara
 */
public class StronaGlowna {
    private JFrame oknoGlowne;
    private JPanel panelZmian;
    private JScrollPane panelKoszyka;
    private JPanel panelGlowny;
    private JPanel panelSzczegolowWybranego;
    private JLabel tytulLabel;
    private JButton zamowButton;
    private JButton cofnijButton;
    private JLabel iloscRzeczyLabel;

    private List<Produkt> listaProduktow = new ArrayList<>();
    private Map<Produkt, Integer> koszyk = new HashMap<>();
    private Klient klient;
    private JLabel cenaZamowieniaLabel;

    /**
     * Load data
     */
    private void zaktualizujDane() {
        Session session = Controller.getSession();
        session.beginTransaction();
        Produkt.setEkstensjaProduktow(session.createQuery("from Produkt").list());
        session.getTransaction().commit();
    }

    /**
     * Create frame
     * @param klient
     */
    public StronaGlowna(Klient klient) {
        this.klient = klient;
        zaktualizujDane();
        panelZmian = new JPanel();

        panelKoszyka = new JScrollPane(new TwojKoszykPanel(this, koszyk));
        panelZmian.add(panelKoszyka);
        panelKoszyka.setVisible(false);

        panelSzczegolowWybranego = new JPanel();
        panelSzczegolowWybranego.setVisible(false);

        panelGlowny = new SpisProduktowPanel(this);
        panelZmian.add(panelGlowny);
        panelGlowny.setVisible(true);

        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        StronaGlowna s = this;
        oknoGlowne = new JFrame();
        oknoGlowne.setTitle("E-targ warzyw");
        oknoGlowne.getContentPane().setBackground(new Color(102, 102, 102));
        oknoGlowne.setBounds(100, 100, 600, 670);
        oknoGlowne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        oknoGlowne.setLocationRelativeTo(null);

        JPanel obrazekKoszyka = new JPanel();
        obrazekKoszyka.setBackground(new Color(102, 102, 102));
        obrazekKoszyka.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelKoszyka = new JScrollPane(new TwojKoszykPanel(s, getKoszyk()));
                panelZmian.add(panelKoszyka);
                //panelKoszyka.setVisible(false);

                panelGlowny.setVisible(false);
                panelSzczegolowWybranego.setVisible(false);
                panelKoszyka.setVisible(true);
                tytulLabel.setText("Twój koszyk");
                cofnijButton.setVisible(true);
                zamowButton.setVisible(true);
            }
        });

        JLabel koszykLabel = new JLabel("Koszyk");
        koszykLabel.setForeground(new Color(204, 204, 204));
        koszykLabel.setFont(new Font("Calibri Light", Font.PLAIN, 15));
        koszykLabel.setHorizontalAlignment(SwingConstants.CENTER);

        tytulLabel = new JLabel("E-targ warzyw");
        tytulLabel.setForeground(new Color(204, 204, 204));
        tytulLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 28));

        panelZmian.setBackground(new Color(153, 153, 153));

        iloscRzeczyLabel = new JLabel("0 rzeczy");
        iloscRzeczyLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        iloscRzeczyLabel.setForeground(new Color(204, 204, 204));
        iloscRzeczyLabel.setHorizontalAlignment(SwingConstants.CENTER);

        zamowButton = new JButton("Zamów");
        zamowButton.setBackground(new Color(197, 56, 71));
        zamowButton.setForeground(new Color(204, 204, 204));
        zamowButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        zamowButton.addActionListener(e -> {
            if(koszyk.size()>0){

                ZamowienieFrame zamowienie = new ZamowienieFrame(this);
                oknoGlowne.setVisible(false);
                zamowienie.getFrmZamwienie().setVisible(true);
            } else {
                JLabel label = new JLabel("Brak produktów w koszyku!");
                label.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
                label.setForeground(new Color(204, 204, 204));
                label.setBackground(new Color(102, 102, 102));

                UIManager.put("OptionPane.background", new Color(102, 102, 102));
                UIManager.put("Panel.background", new Color(102, 102, 102));
                JOptionPane.showMessageDialog(null,label,"Błąd",JOptionPane.WARNING_MESSAGE);
            }
        });

        cofnijButton = new JButton("Cofnij");
        cofnijButton.setForeground(new Color(204, 204, 204));
        cofnijButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        cofnijButton.setBackground(new Color(197, 56, 71));
        cofnijButton.addActionListener(e -> {
            panelKoszyka.setVisible(false);
            panelGlowny.setVisible(true);

            cofnijButton.setVisible(false);
            zamowButton.setVisible(false);
            tytulLabel.setText("E-targ warzyw");
        });
        //panelGlowny.setPreferredSize(new Dimension(530, 500));
        JScrollPane scrollPanePaneluZmian = new JScrollPane(panelZmian);
        scrollPanePaneluZmian.getVerticalScrollBar().setUnitIncrement(16);
        cenaZamowieniaLabel = new JLabel("Cena: 0");
        cenaZamowieniaLabel.setForeground(new Color(204, 204, 204));
        cenaZamowieniaLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 14));
        GroupLayout groupLayout = new GroupLayout(oknoGlowne.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(scrollPanePaneluZmian, GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(27)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(tytulLabel)
                                                                .addPreferredGap(ComponentPlacement.RELATED, 290, Short.MAX_VALUE))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(10)
                                                                .addComponent(cenaZamowieniaLabel)
                                                                .addPreferredGap(ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                                                                .addComponent(cofnijButton, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18)
                                                                .addComponent(zamowButton)
                                                                .addGap(121)))
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(iloscRzeczyLabel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(koszykLabel, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                                        .addComponent(obrazekKoszyka, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(tytulLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(cenaZamowieniaLabel)
                                                .addGap(26))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(obrazekKoszyka, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(koszykLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(iloscRzeczyLabel)
                                                        .addComponent(zamowButton)
                                                        .addComponent(cofnijButton, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(ComponentPlacement.UNRELATED)))
                                .addComponent(scrollPanePaneluZmian, GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                                .addContainerGap())
        );

        zamowButton.setVisible(false);
        cofnijButton.setVisible(false);

        GridBagLayout gbl_panelZmian = new GridBagLayout();
        gbl_panelZmian.columnWidths = new int[]{0};
        gbl_panelZmian.rowHeights = new int[]{0};
        gbl_panelZmian.columnWeights = new double[]{Double.MIN_VALUE};
        gbl_panelZmian.rowWeights = new double[]{Double.MIN_VALUE};
        panelZmian.setLayout(gbl_panelZmian);

        oknoGlowne.getContentPane().setLayout(groupLayout);

    }

    /**
     * Gets iloscRzeczyLabel
     * @return JLabel
     */
    JLabel getIloscRzeczyLabel() {
        return iloscRzeczyLabel;
    }

    /**
     * Gets OknoGlowne
     * @return JFrame
     */
    public JFrame getOknoGlowne() {
        return oknoGlowne;
    }

    /**
     * Gets PanelZmian
     * @return JPanel
     */
    JPanel getPanelZmian() {
        return panelZmian;
    }

    /**
     * Gets PanelGlowny
     * @return JPanel
     */
    JPanel getPanelGlowny() {
        return panelGlowny;
    }

    /**
     * Gets PanelSzczegolow
     * @return Jpanel
     */
    JPanel getPanelSzczegolowWybranego() {
        return panelSzczegolowWybranego;
    }

    /**
     * Gets listaProduktow
     * @return List of products
     */
    public List<Produkt> getListaProduktow() {
        return listaProduktow;
    }

    /**
     * Gets koszyk
     * @return Map od products and amount
     */
    Map<Produkt, Integer> getKoszyk() {
        return koszyk;
    }

    /**
     * Gets PanelKoszyka
     * @return JScrollPane
     */
    JScrollPane getPanelKoszyka() {
        return panelKoszyka;
    }

    /**
     * Sets PanelKoszyka
     * @param panelKoszyka
     */
    public void setPanelKoszyka(JScrollPane panelKoszyka) {
        this.panelKoszyka = panelKoszyka;
    }

    /**
     * Sets PanelSzczegolow
     * @param panelSzczegolowWybranego
     */
    void setPanelSzczegolowWybranego(JPanel panelSzczegolowWybranego) {
        this.panelSzczegolowWybranego = panelSzczegolowWybranego;
    }

    /**
     * Gets klient
     * @return Klient
     */
    public Klient getKlient() {
        return klient;
    }

    /**
     * Gets cena's label
     * @return
     */
    JLabel getCenaZamowieniaLabel() {
        return cenaZamowieniaLabel;
    }

}
