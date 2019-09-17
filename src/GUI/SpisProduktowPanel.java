package GUI;
import Klasy.Produkt;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
/**
 * @author Barbara
 */
class SpisProduktowPanel extends JPanel{
    private StronaGlowna oknoGlowne;
    private JLabel nazwaProduktuLabel;

    /**
     * Create the panel.
     * @param oknoGlowne
     */
    SpisProduktowPanel(StronaGlowna oknoGlowne) {
        this.oknoGlowne = oknoGlowne;
        setBackground(new Color(102, 102, 102));
        setLayout(new GridLayout(0, 1, 0, 0));
        zaktualizujGUI();
    }

    /**
     * Update view
     */
    private void zaktualizujGUI(){
        for(Produkt produkt : Produkt.getEkstensjaProduktow()){
            stworzGUI(produkt);
        }
    }

    /**
     * Create current view
     * @param produkt
     */
    private void stworzGUI(Produkt produkt){
        StronaGlowna s = oknoGlowne;
        JPanel panelProduktu = new JPanel();
        panelProduktu.setBackground(new Color(204, 204, 204));
        add(panelProduktu);

        JPanel zdjecieProduktu = new JPanel();

        nazwaProduktuLabel = new JLabel(produkt.getNazwa());
        nazwaProduktuLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 18));
        nazwaProduktuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font font = nazwaProduktuLabel.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        nazwaProduktuLabel.setFont(font.deriveFont(attributes));
        nazwaProduktuLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nazwaProduktuLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //tu musi sie wyswietlac info chyba z wybranego jakos
                oknoGlowne.getPanelGlowny().setVisible(false);

                oknoGlowne.setPanelSzczegolowWybranego(new SzczegolowyProduktPanel(s, produkt));
                oknoGlowne.getPanelZmian().add(oknoGlowne.getPanelSzczegolowWybranego());
                oknoGlowne.getPanelSzczegolowWybranego().setVisible(true);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                nazwaProduktuLabel.setForeground(new Color(255, 153, 102));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                nazwaProduktuLabel.setForeground(new Color(0, 0, 0));
            }
        });

        JLabel cenaLabel = new JLabel("Cena:");
        cenaLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));

        JButton btnDodajProdukt = new JButton("Dodaj produkt");
        btnDodajProdukt.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        btnDodajProdukt.setBackground(new Color(197, 56, 71));
        btnDodajProdukt.setForeground(new Color(204, 204, 204));
        btnDodajProdukt.addActionListener(e ->{
            //a tu musi patrzec ktory byl klikniety i przekazywac najlepiej produkt a tam mape w srodku
            DodajIloscProduktu dodajIloscProduktu = new DodajIloscProduktu(oknoGlowne, produkt);
            dodajIloscProduktu.getFrame().setVisible(true);
        });

        JLabel wartoscCeny = new JLabel(String.valueOf(produkt.getCena()));
        wartoscCeny.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        GroupLayout gl_panelProduktu = new GroupLayout(panelProduktu);
        gl_panelProduktu.setHorizontalGroup(
                gl_panelProduktu.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelProduktu.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(zdjecieProduktu, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                .addGroup(gl_panelProduktu.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelProduktu.createSequentialGroup()
                                                .addGap(18)
                                                .addGroup(gl_panelProduktu.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(gl_panelProduktu.createSequentialGroup()
                                                                .addComponent(cenaLabel)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(wartoscCeny))
                                                        .addComponent(nazwaProduktuLabel))
                                                .addContainerGap(100, Short.MAX_VALUE))
                                        .addGroup(Alignment.TRAILING, gl_panelProduktu.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnDodajProdukt)
                                                .addGap(29))))
        );
        gl_panelProduktu.setVerticalGroup(
                gl_panelProduktu.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_panelProduktu.createSequentialGroup()
                                .addGroup(gl_panelProduktu.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panelProduktu.createSequentialGroup()
                                                .addGap(21)
                                                .addComponent(nazwaProduktuLabel)
                                                .addGap(18)
                                                .addGroup(gl_panelProduktu.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(cenaLabel)
                                                        .addComponent(wartoscCeny))
                                                .addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                                .addComponent(btnDodajProdukt))
                                        .addGroup(gl_panelProduktu.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(zdjecieProduktu, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        panelProduktu.setLayout(gl_panelProduktu);
    }
}
