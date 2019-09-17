package GUI;
import Klasy.Produkt;

import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
/**
 * @author Barbara
 */
class DodajIloscProduktu {
    private JFrame frame;
    private JSpinner iloscProduktuTextField;
    private StronaGlowna oknoGlowne;

    /**
     * Create the application.
     * @param oknoGlowne
     * @param produkt
     */
    public DodajIloscProduktu(StronaGlowna oknoGlowne, Produkt produkt) {
        this.oknoGlowne = oknoGlowne;

        initialize(produkt, null);
    }

    /**
     * Create the application.
     * @param oknoGlowne
     * @param produkt
     * @param iloscLabel
     */
    public DodajIloscProduktu(StronaGlowna oknoGlowne, Produkt produkt, JLabel iloscLabel) {
        this.oknoGlowne = oknoGlowne;

        initialize(produkt, iloscLabel);
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize(Produkt produkt, JLabel labelIlosci) {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(102, 102, 102));
        frame.setBounds(100, 100, 169, 172);
        frame.setLocationRelativeTo(null);


        SpinnerNumberModel model = new SpinnerNumberModel(50, 0, 100, 1);
        iloscProduktuTextField = new JSpinner(model);
        iloscProduktuTextField.setBackground(new Color(204, 204, 204));
        iloscProduktuTextField.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        Component mySpinnerEditor = iloscProduktuTextField.getEditor();
        JFormattedTextField jftf = ((JSpinner.DefaultEditor) mySpinnerEditor).getTextField();
        jftf.setColumns(9);


        JLabel iloscProduktuLabel = new JLabel("Ilość produktu:");
        iloscProduktuLabel.setForeground(new Color(204, 204, 204));
        iloscProduktuLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
        iloscProduktuLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton dodajProduktButton = new JButton("Dodaj produkt");
        dodajProduktButton.setBackground(new Color(197, 56, 71));
        dodajProduktButton.setForeground(new Color(204, 204, 204));
        dodajProduktButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));

        dodajProduktButton.addActionListener(e->{
            if(oknoGlowne.getPanelKoszyka().isVisible()){
                oknoGlowne.getKoszyk().remove(produkt);
                labelIlosci.setText("x"+iloscProduktuTextField.getValue().toString());
            }

            oknoGlowne.getKoszyk().put(produkt, (Integer) iloscProduktuTextField.getValue());
            float cena = 0.0f;
            for (Map.Entry<Produkt, Integer> entry : oknoGlowne.getKoszyk().entrySet()) {
                cena += entry.getKey().getCena()*entry.getValue();
            }
            oknoGlowne.getCenaZamowieniaLabel().setText("Cena: "+String.valueOf(cena));
            oknoGlowne.getIloscRzeczyLabel().setText(oknoGlowne.getKoszyk().size()+" produktów");
            frame.dispose();
        });
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(dodajProduktButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(iloscProduktuLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(iloscProduktuTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(149, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(iloscProduktuLabel)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(iloscProduktuTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addComponent(dodajProduktButton)
                                .addContainerGap(31, Short.MAX_VALUE))
        );
        frame.getContentPane().setLayout(groupLayout);
    }

    JFrame getFrame() {
        return frame;
    }
}
