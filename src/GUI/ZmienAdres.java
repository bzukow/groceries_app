package GUI;
import Klasy.*;
import Controller.Controller;
import org.hibernate.Session;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
/**
 * @author Barbara
 */
class ZmienAdres {
    private JFrame frmZmieAdres;
    private ZamowienieFrame zamowienieFrame;
    private DefaultListModel<Adres> model = new DefaultListModel<>();
    private JList<Adres> listaAdresowList = new JList<>();
    private JScrollPane scrollAdresow;
    /**
     * Create the application.
     * @param parent
     */
    ZmienAdres(ZamowienieFrame parent) {
        zamowienieFrame = parent;
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmZmieAdres = new JFrame();
        frmZmieAdres.setTitle("Zmień adres");
        frmZmieAdres.getContentPane().setBackground(new Color(102, 102, 102));
        frmZmieAdres.setBounds(100, 100, 509, 388);
        frmZmieAdres.setLocationRelativeTo(null);

        JLabel wybierzLabel = new JLabel("Wybierz jeden z adresów zapisanych poniżej:");
        wybierzLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 17));
        wybierzLabel.setForeground(new Color(204, 204, 204));


        zaktualizujListe();

        JButton zatwierdzButton = new JButton("Zatwierdz");
        zatwierdzButton.setBackground(new Color(197, 56, 71));
        zatwierdzButton.setForeground(new Color(204, 204, 204));
        zatwierdzButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        zatwierdzButton.addActionListener(r->{
            if(listaAdresowList.isSelectionEmpty()){
                JOptionPane.showMessageDialog(null, "Wybierz jeden z adresów z listy!");
            }
            int index = listaAdresowList.getSelectedIndex();
            try {
                Zamowienie noweZamowienie = new Zamowienie(zamowienieFrame.getKlient(), model.getElementAt(index), zamowienieFrame.getStronaGlowna().getKoszyk());
                Session s = Controller.getSession();
                s.beginTransaction();

                s.save(noweZamowienie);
                for(IloscProduktuWZamowieniu i : IloscProduktuWZamowieniu.getEkstensjaIlosciProduktuWZamowieniu()){
                    s.saveOrUpdate(i);
                }
                for(Produkt i : Produkt.getEkstensjaProduktow()){
                    s.saveOrUpdate(i);
                }
                s.getTransaction().commit();
                //s.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            zamowienieFrame.getKlient().ustawAdres(model.getElementAt(index));

            frmZmieAdres.dispose();
            zamowienieFrame.getPanelAdresu().setVisible(false);


            zamowienieFrame.setPanelDziekujemy(new DziekujemyZaZamowienie(model.getElementAt(index)));
            zamowienieFrame.getPanelDziekujemy().setVisible(true);


            zamowienieFrame.getCofnijButton().setText("OK");

            zamowienieFrame.getDalejButton().setVisible(false);
            for(ActionListener al : zamowienieFrame.getCofnijButton().getActionListeners()){
                zamowienieFrame.getCofnijButton().removeActionListener(al);
            }
            zamowienieFrame.getCofnijButton().addActionListener(s->{
                zamowienieFrame.getFrmZamwienie().dispose();
                zamowienieFrame.getStronaGlowna().getKoszyk().clear();
                StronaGlowna stronaGlowna = new StronaGlowna(zamowienieFrame.getKlient());
                stronaGlowna.getOknoGlowne().setVisible(true);
            });
        });

        JButton dodajAdresButton = new JButton("Dodaj adres");
        dodajAdresButton.setBackground(new Color(197, 56, 71));
        dodajAdresButton.setForeground(new Color(204, 204, 204));
        dodajAdresButton.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));
        dodajAdresButton.addActionListener(e->{
            DodajAdres dodajAdres = new DodajAdres(zamowienieFrame, this);
            dodajAdres.getFrmDodajAdres().setVisible(true);
        });

        JLabel dodajLabel = new JLabel("... lub dodaj nowy");
        dodajLabel.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 15));
        dodajLabel.setForeground(new Color(204, 204, 204));

        GroupLayout groupLayout = new GroupLayout(frmZmieAdres.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(30)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollAdresow, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(wybierzLabel))
                                .addContainerGap(35, Short.MAX_VALUE))
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                .addContainerGap(166, Short.MAX_VALUE)
                                .addComponent(dodajLabel)
                                .addGap(18)
                                .addComponent(dodajAdresButton)
                                .addGap(18)
                                .addComponent(zatwierdzButton)
                                .addGap(47))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(21)
                                .addComponent(wybierzLabel)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(scrollAdresow, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(zatwierdzButton)
                                        .addComponent(dodajAdresButton)
                                        .addComponent(dodajLabel))
                                .addContainerGap(24, Short.MAX_VALUE))
        );
        frmZmieAdres.getContentPane().setLayout(groupLayout);
    }

    /**
     * Gets this frame
     * @return JFrame
     */
    JFrame getFrmZmieAdres() {
        return frmZmieAdres;
    }

    /**
     * Update list of addresses
     */
    private void zaktualizujListe() {

        listaAdresowList.setBackground(new Color(153, 153, 153));
        listaAdresowList.setFont(new Font("Microsoft YaHei UI Light", Font.PLAIN, 13));

        model.removeAllElements();
        for(Adres adres : zamowienieFrame.getKlient().getListaAdresow()){
            model.addElement(adres);
        }
        listaAdresowList.setModel(model);

        scrollAdresow = new JScrollPane();
        scrollAdresow.setViewportView(listaAdresowList);
    }

    /**
     * Gets ListaAdresowList
     * @return JList of Addresses
     */
    JList<Adres> getListaAdresowList() {
        return listaAdresowList;
    }

    /**
     * Gets model
     * @return model for JScrollPane
     */
    DefaultListModel<Adres> getModel() {
        return model;
    }

    /**
     * Gets JScrollPaneAdressow
     * @return JScrollPane
     */
    JScrollPane getScrollAdresow() {
        return scrollAdresow;
    }
}
