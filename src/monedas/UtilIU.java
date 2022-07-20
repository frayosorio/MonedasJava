package monedas;

import java.awt.Desktop;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class UtilIU {

    public static String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

    public static void error(String mensaje) {
        JFrame frmMensaje = new JFrame();
        JOptionPane.showMessageDialog(frmMensaje, mensaje, "Error", 0);
    }//error

    public static void mensaje(String strTitulo, String strMensaje) {
        JFrame frmMensaje = new JFrame();
        JOptionPane.showMessageDialog(frmMensaje, strMensaje, strTitulo, 1);
    }//mensaje

    public static boolean decidir(String strTitulo, String strMensaje) {
        JFrame frmMensaje = new JFrame();
        Object[] opciones = new String[]{"S\u00ed", "No"};
        int opcion = JOptionPane.showOptionDialog(frmMensaje, strMensaje, strTitulo, -1, 3, null, opciones, opciones[0]);
        if (opcion == 0) {
            return true;
        }
        return false;
    }//decidir

    public static boolean abrirArchivoEnPrograma(String nombreArchivo) {
        try {
            File f = new File(nombreArchivo);
            if (!Desktop.isDesktopSupported()) {
                return false;
            }

            Desktop desktop = Desktop.getDesktop();
            if (!desktop.isSupported(Desktop.Action.OPEN)) {
                return false;
            }
            desktop.open(f);
        } catch (IOException e) {
            return false;
        }
        return true;
    }//abrirArchivoEnPrograma

    //Médodo que muestra una conjunto de datos en una objeto JTABLE
    public static void mostrarTabla(JTable tbl, String[][] datos, String[] encabezados) {
        DefaultTableModel dtm = new DefaultTableModel(datos, encabezados);
        tbl.setModel(dtm);
    }//MostrarTabla

    //Metodo para habilitar botones
    public static void habilitarBotones(List<JButton> botones, Boolean habilitado, int[] posiciones) {
        if (botones != null) {
            for (int i = 0; i < botones.size(); i++) {
                JButton btn = botones.get(i);

                Boolean enLista = false;
                for (int j = 0; j < posiciones.length; j++) {
                    if (i == posiciones[j]) {
                        enLista = true;
                    }
                    if (enLista) {
                        btn.setEnabled(habilitado);
                    } else {
                        btn.setEnabled(!habilitado);
                    }
                }
            }
        }
    }//HabilitarBotones

//    public static void HabilitarBotones(ToolStripButton[] botones, Boolean habilitado, Boolean agregando, String[] nombres)
//    {
//        if (botones != null)
//        {
//            foreach (ToolStripButton btn in botones)
//            {
//                Boolean enLista = false;
//                foreach (String nombre in nombres)
//                {
//                    if (btn.Name == nombre)
//                        enLista = true;
//                }
//                if (enLista)
//                {
//                    btn.Enabled = habilitado;
//                }
//                else
//                {
//                    btn.Enabled = agregando ? false : !habilitado;
//                }
//            }
//        }
//    }//HabilitarBotones
}
