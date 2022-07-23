package monedas;

import java.awt.Desktop;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;
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

    public static void mostrarImagenVentana(byte[] bImagen, String titulo) {
        //Instancia una nueva ventana
        JFrame frmMapa = new JFrame();
        frmMapa.setTitle(titulo);
        frmMapa.setSize(600, 400);
        frmMapa.setVisible(true);
        //frmMapa.setClosable(true);
        frmMapa.setResizable(true);

        if (bImagen != null) {
            try {
                //Obtener la imagen a partir de los bytes
                InputStream is = new ByteArrayInputStream(bImagen);
                BufferedImage img = ImageIO.read(is);

                //Cargar la imagen en un objeto JLABEL
                ImageIcon icon = new ImageIcon(img);
                JLabel lbl = new JLabel();
                lbl.setIcon(icon);
                //Definir un panel de desplazamiento
                JScrollPane jsp = new JScrollPane(lbl);
                //Ubicarlo la ventana
                jsp.setBounds(0, 0, frmMapa.getWidth(), frmMapa.getHeight());
                //Agregarlo a la ventana
                frmMapa.getContentPane().add(jsp);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }//mostrarImagenVentana

    public static Properties abrirConfiguracion() {
        String ruta = System.getProperty("user.dir") + "/src/configuracion/app.config";
        try {
            FileInputStream fis = new FileInputStream(ruta);
            Properties p = new Properties();
            p.load(fis);
            return p;
        } catch (Exception ex) {

        }
        return null;
    }//abrirConfiguracion
    
        public static byte[] obtenerImagen(String nombreArchivo) throws Exception {
        try {
            //Obtener la imagen desde archivo
            URL url = new URL(nombreArchivo);
            BufferedImage bi = ImageIO.read(url);
            //Convertirla a bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);
            byte[] b = baos.toByteArray();
            return b;
        } catch (Exception ex) {
            throw new Exception("Error al Obtener Imagen:\n" + ex);
        }
    }//obtenerImagen

    public static void mostrarImagen(byte[] b, JPanel pnl) {
        if (b != null) {
            try {
                //Obtener la imagen a partir de los bytes
                InputStream is = new ByteArrayInputStream(b);
                BufferedImage img = ImageIO.read(is);

                //Cargar la imagen en un objeto JLABEL
                ImageIcon icon = new ImageIcon(img);
                JLabel lbl = new JLabel();
                lbl.setIcon(icon);
                //Definir un panel de desplazamiento
                JScrollPane jsp = new JScrollPane(lbl);
                //Ubicarlo en el panel
                jsp.setBounds(0, 0, pnl.getWidth(), pnl.getHeight());
                //Agregarlo a la ventana
                pnl.add(jsp);

            } catch (Exception ex) {

            }
        }
    }//mostrarImagen

}
