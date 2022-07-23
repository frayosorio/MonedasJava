package monedas;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import java.awt.Component;
import java.util.*;
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

    }//iniciarEdicion

    public static boolean guardar(JTextField txtPais,
            JTextField txtCodigoAlfa2,
            JTextField txtCodigoAlfa3,
            JComboBox cmbMoneda) throws Exception {

        Pais p = new Pais(indice >= 0 ? paises.get(indice).getId() : -1,
                txtPais.getText(),
                txtCodigoAlfa2.getText(),
                txtCodigoAlfa3.getText(),
                cmbMoneda.getSelectedIndex() >= 0 ? monedas.get(cmbMoneda.getSelectedIndex()).getId() : -1
        );
        try {
            if (Pais.guardar(p)) {
                if (indice >= 0) {
                    paises.set(indice, p);
                } else {
                    paises.add(p);
                }
                return true;
            }
        } catch (Exception ex) {
            throw new Exception("Error al actualizar el País:\n [** " + ex + " **]");
        }
        return false;
    }//guardar

    //Método para Eliminar una Pais
    public static boolean eliminar() throws Exception {
        try {
            if (Pais.eliminar(paises.get(indice))) {
                paises.remove(indice);
                return true;
            }
        } catch (Exception ex) {
            throw new Exception("Error al eliminar el País:\n [** " + ex + " **]");
        }
        return false;
    }

    public static void imprimir() throws Exception {
        String nombreArchivo = System.getProperty("user.dir")
                + "/src/Listado de Paises.pdf";

        Document dPDF = DocumentoPDF.iniciar(nombreArchivo,
                "Curso Mintic",
                "Listado de Paises",
                new float[]{20, 20, 20, 20});

        //Agregar el titulo de encabezado
        Paragraph p = DocumentoPDF.crearParrafo("Listado de Paises\n\n\n", Alineacion.CENTRADO, DocumentoPDF.F12N);
        dPDF.add(p);

        String[][] datos = pasarMatriz();
        int[] anchos = new int[]{1, 3, 1, 1, 2};
        Alineacion[] alineaciones = new Alineacion[5];
        for (int i = 0; i < alineaciones.length; i++) {
            alineaciones[i] = Alineacion.CENTRADO;
        }
        PdfPTable t = DocumentoPDF.obtenerTabla(datos, encabezados, anchos, alineaciones, true);
        dPDF.add(t);

        dPDF.close();

        UtilIU.abrirArchivoEnPrograma(nombreArchivo);
    }//imprimir

    public static void mostrarMapa() throws Exception {
        if (indice >= 0) {
            String titulo = "Mapa de " + paises.get(indice).getPais();
            try {
                byte[] bMapa = paises.get(indice).getMapa();

                UtilIU.mostrarImagenVentana(bMapa, titulo);
            } catch (Exception ex) {
                throw new Exception("Error al actualizar el País:\n [** " + ex + " **]");
            }
        }
    }//mostrarMapa

}
