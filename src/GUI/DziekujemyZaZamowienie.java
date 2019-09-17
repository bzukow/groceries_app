package GUI;
import Klasy.Adres;

import javax.swing.JPanel;
import java.awt.*;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
/**
 * @author Barbara
 */
class DziekujemyZaZamowienie extends JPanel {
    /**
     * Create the panel.
     * @param adres
     */
    DziekujemyZaZamowienie(Adres adres) {
        setBackground(new Color(102, 102, 102));
        setVisible(true);

        JLabel lblDzikujemyZaZakupy = new JLabel("Dziękujemy za zakupy w naszym sklepie!");
        lblDzikujemyZaZakupy.setForeground(new Color(204, 204, 204));
        lblDzikujemyZaZakupy.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 21));

        JLabel lblOczekujPrzesykiW = new JLabel("Oczekuj przesyłki w przeciągu tygodnia pod adresem:");
        lblOczekujPrzesykiW.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
        lblOczekujPrzesykiW.setForeground(new Color(204, 204, 204));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(153, 153, 153));
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(116)
                                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(57)
                                                .addComponent(lblDzikujemyZaZakupy))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(66)
                                                .addComponent(lblOczekujPrzesykiW)))
                                .addContainerGap(58, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                .addGap(64)
                                .addComponent(lblDzikujemyZaZakupy)
                                .addGap(49)
                                .addComponent(lblOczekujPrzesykiW)
                                .addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                                .addGap(50))
        );

        JLabel lblMiejscowosckod = new JLabel(adres.toString());
        lblMiejscowosckod.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 12));
        lblMiejscowosckod.setForeground(new Color(204, 204, 204));
        lblMiejscowosckod.setHorizontalAlignment(SwingConstants.CENTER);
        panel.setLayout(new BorderLayout(0, 0));
        panel.add(lblMiejscowosckod);
        setLayout(groupLayout);

    }
}
