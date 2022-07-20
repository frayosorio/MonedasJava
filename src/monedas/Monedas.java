package monedas;

import monedasrepositorio.Moneda;
import java.util.List;
import monedasrepositorio.ConexionBD;

public class Monedas {

    public static void main(String[] args) {
        try {
            ConexionBD.establecer();
            new FrmPais().setVisible(true);

        } catch (Exception ex) {
            System.out.println(ex);

        }
    }

}
