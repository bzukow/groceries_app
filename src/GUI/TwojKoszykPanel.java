package GUI;
import Klasy.Produkt;

import javax.swing.*;
import java.awt.GridLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Font;
import java.util.Map;
/**
 * @author Barbara
 */
class TwojKoszykPanel extends JPanel{
    private Map<Produkt, Integer> koszyk;
    private StronaGlowna stronaGlowna;

    /**
     * Create the panel.
     * @param stronaGlowna
     * @param koszyk
     */
    TwojKoszykPanel(StronaGlowna stronaGlowna, Map<Produkt, Integer> koszyk) {
        this.stronaGlowna = stronaGlowna;
        this.koszyk = koszyk;
        setBackground(new Color(102, 102, 102));
        setLayout(new GridLayout(0, 1, 0, 0));

        zaktualizujGUI();
    }

    /**
     * Update GUI
     */
    private void zaktualizujGUI(){
        for (Map.Entry<Produkt, Integer> entry : koszyk.entrySet()) {
            stworzGUI(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Create GUI
     * @param produkt
     * @param iloscProduktu
     */
    private void stworzGUI(Produkt produkt, Integer iloscProduktu){
        JPanel panelProduktuWKoszyku = new JPanel();
        panelProduktuWKoszyku.setBackground(new Color(204, 204, 204));
        add(panelProduktuWKoszyku);

        JPanel zdjecieProduktu = new JPanel();

        JLabel nazwaProduktuLabel = new JLabel(produkt.getNazwa());
        nazwaProduktuLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 21));
        nazwaProduktuLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JButton usunZKoszyka = new JButton("Usun z koszyka");
        usunZKoszyka.setBackground(new Color(197, 56, 71));
        usunZKoszyka.setForeground(new Color(204, 204, 204));
        usunZKoszyka.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        usunZKoszyka.addActionListener(e -> {
            koszyk.remove(produkt);
            remove(panelProduktuWKoszyku);
            float cena = 0.0f;
            for (Map.Entry<Produkt, Integer> entry : stronaGlowna.getKoszyk().entrySet()) {
                cena += entry.getKey().getCena()*entry.getValue();
            }
            stronaGlowna.getCenaZamowieniaLabel().setText("Cena: "+ cena);
            stronaGlowna.getIloscRzeczyLabel().setText(stronaGlowna.getKoszyk().size()+" produktów");
            validate();
            repaint();

        });


        JLabel iloscLabel = new JLabel("x"+iloscProduktu);
        iloscLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 11));
        //iloscLabel.setForeground(new Color(204, 204, 204));
        iloscLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton zmienIloscButton = new JButton("Zmien ilosc");
        zmienIloscButton.setBackground(new Color(197, 56, 71));
        zmienIloscButton.setForeground(new Color(204, 204, 204));
        zmienIloscButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        zmienIloscButton.addActionListener(e->{
            DodajIloscProduktu dodajIloscProduktu = new DodajIloscProduktu(stronaGlowna, produkt, iloscLabel);
            dodajIloscProduktu.getFrame().setVisible(true);
        });

        GroupLayout gl_panelProduktuWKoszyku = new GroupLayout(panelProduktuWKoszyku);
        gl_panelProduktuWKoszyku.setHorizontalGroup(
                gl_panelProduktuWKoszyku.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelProduktuWKoszyku.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(zdjecieProduktu, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
                                .addGroup(gl_panelProduktuWKoszyku.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panelProduktuWKoszyku.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(iloscLabel)
                                                .addContainerGap())
                                        .addGroup(gl_panelProduktuWKoszyku.createSequentialGroup()
                                                .addGap(12)
                                                .addComponent(nazwaProduktuLabel, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18)
                                                .addGroup(gl_panelProduktuWKoszyku.createParallelGroup(Alignment.LEADING, false)
                                                        .addComponent(usunZKoszyka, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(zmienIloscButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                                                .addContainerGap())))
        );
        gl_panelProduktuWKoszyku.setVerticalGroup(
                gl_panelProduktuWKoszyku.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panelProduktuWKoszyku.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panelProduktuWKoszyku.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(zdjecieProduktu, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                        .addGroup(gl_panelProduktuWKoszyku.createSequentialGroup()
                                                .addComponent(iloscLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                                .addGroup(gl_panelProduktuWKoszyku.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(nazwaProduktuLabel)
                                                        .addComponent(zmienIloscButton, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                                .addGap(3)
                                                .addComponent(usunZKoszyka)))
                                .addContainerGap())
        );
        panelProduktuWKoszyku.setLayout(gl_panelProduktuWKoszyku);
    }
}
