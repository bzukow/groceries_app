package GUI;
import Klasy.Certyfikat;
import Klasy.Produkt;
import Klasy.ProduktCertyfikat;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
/**
 * @author Barbara
 */
class SzczegolowyProduktPanel extends JPanel{
    /**
     * Create the panel
     * @param stronaGlowna
     * @param produkt
     */
    SzczegolowyProduktPanel(StronaGlowna stronaGlowna, Produkt produkt) {
        setBackground(new Color(153, 153, 153));

        JPanel zdjecieProduktu = new JPanel();

        JLabel cenaLabel = new JLabel("Cena:");
        cenaLabel.setForeground(new Color(204, 204, 204));
        cenaLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 16));

        JLabel wartoscCeny = new JLabel(String.valueOf(produkt.getCena()));
        wartoscCeny.setForeground(new Color(204, 204, 204));
        wartoscCeny.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 16));

        JButton dodajProduktButton = new JButton("Dodaj produkt");
        dodajProduktButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        dodajProduktButton.setBackground(new Color(197, 56, 71));
        dodajProduktButton.setForeground(new Color(204, 204, 204));
        dodajProduktButton.addActionListener(e->{
            DodajIloscProduktu dodajIloscProduktu = new DodajIloscProduktu(stronaGlowna, produkt);
            dodajIloscProduktu.getFrame().setVisible(true);
        });

        JLabel nazwaProduktu = new JLabel(produkt.getNazwa());
        nazwaProduktu.setForeground(new Color(204, 204, 204));
        nazwaProduktu.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 20));
        nazwaProduktu.setHorizontalAlignment(SwingConstants.CENTER);

        JButton cofnijButton = new JButton("Cofnij");
        cofnijButton.setBackground(new Color(197, 56, 71));
        cofnijButton.setForeground(new Color(204, 204, 204));
        cofnijButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        cofnijButton.addActionListener(e->{
            stronaGlowna.getPanelGlowny().setVisible(true);
            stronaGlowna.getPanelSzczegolowWybranego().setVisible(false);
        });

        JLabel certyfikatyLabel = new JLabel("Certyfikaty:");
        certyfikatyLabel.setForeground(new Color(204, 204, 204));
        certyfikatyLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));

        DefaultListModel<Certyfikat> model = new DefaultListModel<>();

        JList<Certyfikat> listaCertyfikatow = new JList<>(model);
        JScrollPane scrollListycCertyf = new JScrollPane();
        listaCertyfikatow.setForeground(new Color(255, 255, 255));
        listaCertyfikatow.setBackground(new Color(204, 204, 204));
        listaCertyfikatow.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 14));
        scrollListycCertyf.setViewportView(listaCertyfikatow);

        listaCertyfikatow.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    int index = listaCertyfikatow.getSelectedIndex();
                    Certyfikat certyfikat = model.getElementAt(index);

                    UIManager.put("OptionPane.messageFont", new Font("Microsoft YaHei UI Light", Font.PLAIN, 14));
                    UIManager.put("OptionPane.buttonFont", new Font("Microsoft YaHei UI Light", Font.PLAIN, 14));
                    UIManager.put("OptionPane.messageForeground", new Color(204, 204, 204));
                    UIManager.put("OptionPane.background", new Color(102, 102, 102));
                    UIManager.put("Panel.background", new Color(102, 102, 102));
                    JOptionPane.showMessageDialog(null, certyfikat.zwrocProdukty());

                }
            }
        });
        for(ProduktCertyfikat certyfikatyProduktu : produkt.getListaCertyfikatow()){
            model.addElement(certyfikatyProduktu.getCertyfikat());
        }
//
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(zdjecieProduktu, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(nazwaProduktu, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(16)
                                                .addComponent(cenaLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(wartoscCeny, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(22)
                                .addComponent(cofnijButton, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                                .addComponent(dodajProduktButton, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
                                .addGap(20))
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                .addContainerGap(40, Short.MAX_VALUE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(certyfikatyLabel)
                                        .addComponent(scrollListycCertyf, GroupLayout.PREFERRED_SIZE, 423, GroupLayout.PREFERRED_SIZE))
                                .addGap(37))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(zdjecieProduktu, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(nazwaProduktu)
                                                .addGap(60)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(cenaLabel)
                                                        .addComponent(wartoscCeny))))
                                .addGap(24)
                                .addComponent(certyfikatyLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollListycCertyf, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(dodajProduktButton)
                                        .addComponent(cofnijButton, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        setLayout(groupLayout);
    }
}
