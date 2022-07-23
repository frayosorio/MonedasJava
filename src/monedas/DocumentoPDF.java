package monedas;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;

public class DocumentoPDF {

    //Fuentes a utilizar
    public static Font F7;
    public static Font F7N;
    public static Font F7NB;
    public static Font F8;
    public static Font F8N;
    public static Font F8NB;
    public static Font F9;
    public static Font F9N;
    public static Font F9NB;
    public static Font F10;
    public static Font F10N;
    public static Font F10NB;
    public static Font F12;
    public static Font F12N;
    public static Font F12NB;
    public static Font F14;
    public static Font F14N;
    public static Font F14NB;

    public static Document iniciar(String NombreArchivo,
            String Entidad,
            String Titulo,
            float[] Margenes) {
        //Crear el documento
        Document dPDF = new Document();
        try {
            dPDF.addAuthor(Entidad);
            dPDF.addTitle(Titulo);
            dPDF.setMargins(Margenes[0], Margenes[1], Margenes[2], Margenes[3]);

            //Crear el objeto escritor del Documento PDF
            PdfWriter.getInstance(dPDF, new FileOutputStream(NombreArchivo));

            //Abrir el documento para agregarle contenido
            dPDF.open();

            //Crear las fuentes
            F7 = new Font(FontFactory.getFont(FontFactory.TIMES, 7));
            F7N = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 7));
            F7NB = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 7, BaseColor.WHITE));

            F8 = new Font(FontFactory.getFont(FontFactory.TIMES, 8));
            F8N = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 8));
            F8NB = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 8, BaseColor.WHITE));

            F9 = new Font(FontFactory.getFont(FontFactory.TIMES, 9));
            F9N = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 9));
            F9NB = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 9, BaseColor.WHITE));

            F10 = new Font(FontFactory.getFont(FontFactory.TIMES, 10));
            F10N = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 10));
            F10NB = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.WHITE));

            F12 = new Font(FontFactory.getFont(FontFactory.TIMES, 12));
            F12N = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 12));
            F12NB = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, BaseColor.WHITE));

            F14 = new Font(FontFactory.getFont(FontFactory.TIMES, 14));
            F14N = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 14));
            F14NB = new Font(FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.WHITE));

            agregarLogo(dPDF);
        } catch (Exception ex) {

        }
        return dPDF;
    }

    public static void agregarLogo(Document dPDF) {
        String ArchivoLogo = System.getProperty("user.dir")
                + "/src/iconos/logo.png";
        agregarLogo(dPDF, ArchivoLogo, 40f);

    }

    public static void agregarLogo(Document dPDF, String ArchivoLogo, float escala) {
        //Crear la imagen del logo
        try {
            com.itextpdf.text.Image jpg = com.itextpdf.text.Image.getInstance(ArchivoLogo);
            jpg.scalePercent(escala);
            jpg.setAlignment(com.itextpdf.text.Image.TEXTWRAP | com.itextpdf.text.Image.ALIGN_LEFT);

            dPDF.add(jpg);
        } catch (Exception ex) {

        }
    }

    public static Paragraph crearParrafo(String texto, Alineacion alineacion, Font fuente) {
        Paragraph p = new Paragraph(texto, fuente);
        p.setAlignment(DocumentoPDF.obtenerAlineacion(alineacion));
        return p;
    }

    public static int obtenerAlineacion(Alineacion a) {
        switch (a) {
            case IZQUIERDA:
                return Element.ALIGN_LEFT;
            case CENTRADO:
                return Element.ALIGN_CENTER;
            case DERECHA:
                return Element.ALIGN_RIGHT;
            case JUSTIFICADO:
                return Element.ALIGN_JUSTIFIED;
            default:
                return Element.ALIGN_CENTER;
        }
    }

    public static PdfPTable obtenerTabla(Celda[][] celdas, int[] anchos) {
        PdfPTable t = new PdfPTable(anchos.length);
        try {
            t.setWidthPercentage(100);
            t.setWidths(anchos);
            PdfPCell c;
            int filas = celdas.length;
            int columnas = filas >= 0 ? celdas[0].length : 0;

            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    if (celdas[i][j] == null) {
                        c = new PdfPCell();
                        c.setBorder(0);
                    } else {
                        c = new PdfPCell(new Paragraph(celdas[i][j].texto, celdas[i][ j].Fuente));
                        c.setBackgroundColor(celdas[i][ j].Color);
                        c.setHorizontalAlignment(obtenerAlineacion(celdas[i][ j].Alineacion));
                        c.setBorder(celdas[i][ j].Borde);
                        c.setColspan(celdas[i][ j].ColumnasExpandidas);
                    }
                    t.addCell(c);
                }
            }
        } catch (Exception ex) {

        }
        return t;
    }//ObtenerTabla

    public static PdfPTable obtenerTabla(String[][] datos, String[] encabezados, int[] anchos, Alineacion[] alineacion, boolean anchoCompleto) {
        PdfPTable t = new PdfPTable(anchos.length);
        try {
            if (anchoCompleto) {
                t.setWidthPercentage(100);
            }
            t.setWidths(anchos);
            PdfPCell c;
            for (int i = 0; i < encabezados.length; i++) {
                c = new PdfPCell(new Paragraph(encabezados[i], DocumentoPDF.F9NB));
                c.setBackgroundColor(BaseColor.DARK_GRAY);
                c.setBorder(0);
                c.setHorizontalAlignment(1);
                c.setVerticalAlignment(1);
                t.addCell(c);
            }
            if (datos != null) {
                int filas = datos.length;
                int columnas = filas >= 0 ? datos[0].length : 0;
                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        c = new PdfPCell(new Paragraph(datos[i][j], DocumentoPDF.F9));
                        if (i % 2 == 1) {
                            c.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        }
                        c.setHorizontalAlignment(obtenerAlineacion(alineacion[j]));
                        c.setBorder(0);
                        t.addCell(c);
                    }
                }
            }
        } catch (Exception ex) {

        }
        return t;
    }


}
