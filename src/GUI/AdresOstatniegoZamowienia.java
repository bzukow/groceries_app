package GUI;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.*;
/**
 * @author Barbara
 */
class AdresOstatniegoZamowienia extends JPanel {
    /**
     * Create the panel.
     * @param zamowienieFrame
     */
    AdresOstatniegoZamowienia(ZamowienieFrame zamowienieFrame) {
        setBackground(new Color(102, 102, 102));

        JLabel ostatnieZamowienieLabel = new JLabel("Ostatnie zamówienie zostało dostarczone na adres:");
        ostatnieZamowienieLabel.setForeground(new Color(204, 204, 204));
        ostatnieZamowienieLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 17));

        JPanel adresPanel = new JPanel();
        adresPanel.setBackground(new Color(153, 153, 153));

        JLabel lblCzyChceszZmienic = new JLabel("Czy chcesz go zmienić?");
        lblCzyChceszZmienic.setForeground(new Color(204, 204, 204));
        lblCzyChceszZmienic.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 17));

        JButton btnZmieAdres = new JButton("Zmień adres");
        btnZmieAdres.setBackground(new Color(197, 56, 71));
        btnZmieAdres.setForeground(new Color(204, 204, 204));
        btnZmieAdres.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        btnZmieAdres.addActionListener(e->{
            ZmienAdres zmienAdres = new ZmienAdres(zamowienieFrame);
            zmienAdres.getFrmZmieAdres().setVisible(true);

        });
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(112)
                                                .addComponent(adresPanel, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(194)
                                                .addComponent(btnZmieAdres))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(47)
                                                .addComponent(ostatnieZamowienieLabel))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(153)
                                                .addComponent(lblCzyChceszZmienic)))
                                .addContainerGap(62, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(52)
                                .addComponent(ostatnieZamowienieLabel)
                                .addGap(48)
                                .addComponent(adresPanel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                .addGap(37)
                                .addComponent(lblCzyChceszZmienic)
                                .addGap(18)
                                .addComponent(btnZmieAdres)
                                .addContainerGap(63, Short.MAX_VALUE))
        );

        adresPanel.setLayout(new BorderLayout(0, 0));

        JLabel miejscowoscLabel = new JLabel(zamowienieFrame.getKlient().getListaAdresow()
                .get(zamowienieFrame.getKlient().getListaAdresow().size()-1).toString());
        miejscowoscLabel.setForeground(new Color(204, 204, 204));
        miejscowoscLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        miejscowoscLabel.setHorizontalAlignment(SwingConstants.CENTER);
        adresPanel.add(miejscowoscLabel);

        setLayout(groupLayout);
    }
}
