package monedas;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import monedasrepositorio.*;

public class PaisIU {

    //Colecciones de datos necesarias
    public static int indice = -1;
    private static List<Pais> paises;
    private static List<Moneda> monedas;
    //Objetos del formulario
    private static JPanel pnlLista;
    private static JPanel pnlEdicion;
    private static JTabbedPane paneles;
    private static List<JButton> botones;

    //Encabezados para la tabla que lista los datos
    private static String[] encabezados = {
        "Id",
        "País",
        "Código Alfa 2",
        "Código Alfa 3",
        "Moneda"};

    public static void actualizarDespliegue(boolean listando) {
        UtilIU.habilitarBotones(botones, !listando, new int[]{5, 6});

        if (listando) {
            if (paneles.indexOfComponent(pnlEdicion) >= 0) {
                paneles.remove(pnlEdicion);
            }
            if (paneles.indexOfComponent(pnlLista) == -1) {
                paneles.add(pnlLista);
            }
        } else {
            if (paneles.indexOfComponent(pnlLista) >= 0) {
                paneles.remove(pnlLista);
            }
            if (paneles.indexOfComponent(pnlEdicion) == -1) {
                paneles.add(pnlEdicion);
            }
        }
    }

    public static void alistarFormulario(JTabbedPane tp, JToolBar tb,
            JComboBox cmbMoneda,
            JComboBox cmbBuscar) throws Exception {
        //pasar los paneles
        pnlLista = (JPanel) tp.getComponent(0);
        pnlEdicion = (JPanel) tp.getComponent(1);
        paneles = tp;

        //pasar los botones
        botones = new ArrayList<>();
        for (Component b : tb.getComponents()) {
            if (b instanceof JButton) {
                botones.add((JButton) b);
            }
        }

        //Listar opciones de busqueda
        cmbBuscar.removeAllItems();
        cmbBuscar.addItem("Nombre");
        cmbBuscar.addItem("Codigo Alfa");
        cmbBuscar.addItem("Moneda");

        //Listar las monedas
        try {
            monedas = Moneda.obtener();

            cmbMoneda.removeAllItems();
            for (Moneda m : monedas) {
                cmbMoneda.addItem(m.getMoneda());
            }
        } catch (Exception ex) {
            throw new Exception("Error al listar Monedas:\n" + ex);
        }

    }

    //Metodo para pasar la lista de objetos a una matriz de despliegue
    private static String[][] pasarMatriz() {
        String[][] datos = null;
        if (paises.size() > 0) {
            datos = new String[paises.size()][encabezados.length];
            int i = 0;
            for (Pais p : paises) {
                datos[i][0] = String.valueOf(p.getId());
                datos[i][1] = p.getPais();
                datos[i][2] = p.getCodigoAlfa2();
                datos[i][3] = p.getCodigoAlfa3();
                datos[i][4] = p.getMoneda().getMoneda();
                i++;
            }
        }
        return datos;
    }//pasarMatriz

    public static void listar(JTable tbl, Boolean refrescar) throws Exception {
        if (refrescar) {
            try {
                paises = Pais.obtener();
            } catch (Exception ex) {
                throw new Exception("Error al listar Países:\n" + ex);
            }
        }
        actualizarDespliegue(true);
        UtilIU.mostrarTabla(tbl, pasarMatriz(), encabezados);
        paneles.setTitleAt(0, "Lista de Países");
    }

    public static void buscar(JTable tbl, int tipo, String dato) throws Exception {
        try {
            paises = Pais.buscar(tipo, dato);
        } catch (Exception ex) {
            throw new Exception("Error al buscar Países:\n [** " + ex + " **]");
        }
        UtilIU.mostrarTabla(tbl, pasarMatriz(), encabezados);
    }

    //Método para limpiar los objetos de la edicion de una Pais
    public static void limpiar(JTextField txtPais,
            JTextField txtCodigoAlfa2,
            JTextField txtCodigoAlfa3,
            JComboBox cmbMoneda
    ) {
        //Dejar los controles vacíos
        txtPais.setText("");
        txtCodigoAlfa2.setText("");
        txtCodigoAlfa3.setText("");
        cmbMoneda.setSelectedIndex(-1);
        paneles.setTitleAt(0, "Editando datos de un nuevo País");
    }//limpiar

    public static void iniciarEdicion(JTextField txtPais,
            JTextField txtCodigoAlfa2,
            JTextField txtCodigoAlfa3,
            JComboBox cmbMoneda) throws Exception {
        actualizarDespliegue(false);
        if (indice >= 0) {
            Pais p = paises.get(indice);
            txtPais.setText(p.getPais());
            txtCodigoAlfa2.setText(p.getCodigoAlfa2());
            txtCodigoAlfa3.setText(p.getCodigoAlfa3());
            cmbMoneda.setSelectedIndex(p.getMoneda() != null ? Moneda.obtenerIndice(monedas, p.getMoneda().getId()) : -1);
            paneles.setTitleAt(0, "Editando datos del país [" + p.getPais() + "]");
        } else {
            limpiar(txtPais,
                    txtCodigoAlfa2,
                    txtCodigoAlfa3,
                    cmbMoneda);
        }

    }

}
