package monedas;

import com.itextpdf.text.*;

public class Celda
{
    public String texto;
    public int Borde;
    public Alineacion Alineacion;
    public BaseColor Color;
    public Font Fuente;
    public int ColumnasExpandidas;

    public Celda()
    {
        this.texto = "";
        this.Borde = 0;
        this.Alineacion = Alineacion.CENTRADO;
        this.Color = BaseColor.WHITE;
        this.Fuente = DocumentoPDF.F10;
        this.ColumnasExpandidas = 1;
    }

    public Celda(String texto,
            int Borde,
            Alineacion Alineacion,
            BaseColor Color,
            Font Fuente)
    {
        this.texto = texto;
        this.Borde = Borde;
        this.Alineacion = Alineacion;
        this.Color = Color == null ? BaseColor.WHITE : Color;
        this.Fuente = Fuente;
        this.ColumnasExpandidas = 1;
    }
}