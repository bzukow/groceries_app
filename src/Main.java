import GUI.StronaGlowna;
import Controller.Controller;

public class Main {
    public static void main(String[] args) {

        Controller c = new Controller();
//        try {
//            c.uzupelnijDane();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        //c.pokażDaneZBazy();
        StronaGlowna frame = new StronaGlowna(c.getKlient());
        frame.getOknoGlowne().setVisible(true);
    }
}
