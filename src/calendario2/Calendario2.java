/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendario2;

import helpClases.filtros.FileFiltExt;
import helpClases.utils.ImageWork;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author ANIMAL
 */
public class Calendario2 {

    static final Color color = Color.WHITE;
    static final Color colorAnno = Color.BLUE;
    static final Color colorMes = Color.BLUE;
    static final Color colorDomingo = Color.RED;
    static Color fondo = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
    static Color letra = Color.BLACK;

    static final int alturaRenglon = 100;
    static final int anchoEspacio = 115;
    static int espacioEntreMesesVertical = 10;
    static int espacioEntreMesesHorizontal = 10;
    static final int espacioEntreDiasHorizontal = 5;
    static final int espacioEntreDiasVertical = 5;
    static int tamanoAnno = alturaRenglon;

    static final int anchoMes = 7 * anchoEspacio + espacioEntreDiasVertical * 6;
    //el alto de la imagen de un  mes es la cantidad des semanas que el maximo es 8
    //por el alto de un renglon + el alto de los dias(L M Mi J V.. )+ el alto de el nombre  del mes
    static int altoMes = 8 * alturaRenglon + espacioEntreDiasHorizontal * 7;

    static HashMap<Integer, BufferedImage> dias = new HashMap();
    static HashMap<Integer, BufferedImage> diasRojos = new HashMap();
    static HashMap<Integer, BufferedImage> meses = new HashMap();
    static BufferedImage nombresSemana = new BufferedImage(anchoMes, alturaRenglon, BufferedImage.TYPE_INT_ARGB);

    private static void llenarNumeroDias() throws IOException {
        for (int i = 1; i < 32; i++) {
            BufferedImage numero = new BufferedImage(anchoEspacio, alturaRenglon, BufferedImage.TYPE_INT_ARGB);
            Graphics2D numeroG = numero.createGraphics();
            numeroG.setColor(fondo);
            numeroG.fill(new Rectangle2D.Double(0, 0, anchoEspacio, alturaRenglon));
            numeroG.setColor(letra);
//            numeroG.setPaint(new TexturePaint(ImageIO.read(new File("G:\\Fabiel\\Mis Documentos\\Imagenes\\Fondos xxx\\2006moert_2.jpg")), new Rectangle2D.Double(400, 300,500, 400)));
//            numeroG.setPaint(new GradientPaint(new Point(50, 50), Color.WHITE, new Point(100, 100), letra));
            numeroG.setFont(numeroG.getFont().deriveFont(Font.BOLD, alturaRenglon));
            FontMetrics fontMetrics = numeroG.getFontMetrics();
            int anchodia = fontMetrics.stringWidth(i + "");
            numeroG.drawString(i + "", anchoEspacio / 2 - anchodia / 2, alturaRenglon - fontMetrics.getDescent() / 2);
//            numeroG.drawString(i + "", 0, alturaRenglon);
            dias.put(i, numero);
        }
    }

    private static void llenarNumeroDiasRojos() {
        for (int i = 1; i < 32; i++) {
            BufferedImage numero = new BufferedImage(anchoEspacio, alturaRenglon, BufferedImage.TYPE_INT_ARGB);
            Graphics2D numeroG = numero.createGraphics();
            numeroG.setColor(fondo);
            numeroG.fill(new Rectangle2D.Double(0, 0, anchoEspacio, alturaRenglon));
            numeroG.setColor(colorDomingo);
            numeroG.setFont(numeroG.getFont().deriveFont(Font.BOLD, alturaRenglon));
            FontMetrics fontMetrics = numeroG.getFontMetrics();
            int anchodia = fontMetrics.stringWidth(i + "");
            numeroG.drawString(i + "", anchoEspacio / 2 - anchodia / 2, alturaRenglon - fontMetrics.getDescent() / 2);
//            numeroG.drawString(i + "", 0, alturaRenglon);
            diasRojos.put(i, numero);
        }
    }
//<editor-fold defaultstate="collapsed" desc="diassemana">

    private static void pintarDiasSemana() {
        Graphics2D createGraphics = nombresSemana.createGraphics();
        createGraphics.setFont(createGraphics.getFont().deriveFont(Font.BOLD, alturaRenglon - espacioEntreDiasHorizontal));
        String[] semana = new String[]{"L", "M", "Mi", "J", "V", "S", "D"};
        for (int i = 0; i < 7; i++) {
            createGraphics.setColor(fondo);
            createGraphics.fill(new Rectangle2D.Double(0, 0, anchoEspacio, alturaRenglon));
            if (i == 6) {
                createGraphics.setColor(colorDomingo);
            } else {
                createGraphics.setColor(letra);
            }
            FontMetrics fontMetrics = createGraphics.getFontMetrics();
            int anchodia = fontMetrics.stringWidth(semana[i]);
            createGraphics.drawString(semana[i], anchoEspacio / 2 - anchodia / 2, alturaRenglon - fontMetrics.getDescent() / 2);
//            createGraphics.drawString(semana[i], 0, alturaRenglon - espacioEntreDiasHorizontal);
            createGraphics.translate(anchoEspacio + espacioEntreDiasVertical, 0);
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="pintarmeses">
    static int anno;

    private static void pintarMeses(int anno) {
        Calendario2.anno = anno;
        Calendar calendar = Calendar.getInstance();
        calendar.set(anno, 0, 1, 0, 0, 0);
        String[] nombreMeses = new String[]{"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
//        String[] nombreMeses = new String[]{"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        for (int i = 0; i < 12; i++) {
            //   int cntdSemanas = calendar.getMaximum(Calendar.WEEK_OF_MONTH);
            //int altoMes = (cntdSemanas * alturaRenglon) + alturaRenglon * 2;
            BufferedImage calendarioDelMes = new BufferedImage(anchoMes, altoMes, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graficosDelMes = calendarioDelMes.createGraphics();
            graficosDelMes.setFont(graficosDelMes.getFont().deriveFont(Font.BOLD, alturaRenglon));
            graficosDelMes.setColor(fondo);
            graficosDelMes.fill(new Rectangle2D.Double(0, 0, anchoMes, alturaRenglon));
            graficosDelMes.setColor(colorMes);
            FontMetrics fontMetrics = graficosDelMes.getFontMetrics();
            int anchoNombreMes = fontMetrics.stringWidth(nombreMeses[i]);
            graficosDelMes.drawString(nombreMeses[i], anchoMes / 2 - anchoNombreMes / 2, alturaRenglon - fontMetrics.getDescent() / 2);
            // graficosDelMes.translate(0, espacioEntreDiasHorizontal);
            //dibujar L M Mi J V S D
            graficosDelMes.drawImage(nombresSemana, 0, alturaRenglon + espacioEntreDiasHorizontal, null);
            //trasladar los graficos
            graficosDelMes.translate(0, (alturaRenglon + espacioEntreDiasHorizontal) * 2);
            int cntdDias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int j = 0; j < cntdDias; j++) {//dibujar los numeros de los dias debajo del Dia(L M Mi J V S D) que le corresponda
                int get = calendar.get(Calendar.DAY_OF_WEEK);
                BufferedImage numero;
                if (get != 1) {
                    numero = dias.get(calendar.get(Calendar.DAY_OF_MONTH));
                } else {
                    numero = diasRojos.get(calendar.get(Calendar.DAY_OF_MONTH));
                }
                switch (get) {
                    case 2://lunes
                        graficosDelMes.drawImage(numero, 0, 0, null);
                        break;
                    case 3://martes
                        graficosDelMes.drawImage(numero, anchoEspacio + espacioEntreDiasVertical, 0, null);
                        break;
                    case 4://miercoles
                        graficosDelMes.drawImage(numero, (anchoEspacio + espacioEntreDiasVertical) * 2, 0, null);
                        break;
                    case 5://jueves
                        graficosDelMes.drawImage(numero, (anchoEspacio + espacioEntreDiasVertical) * 3, 0, null);
                        break;
                    case 6: //viernes
                        graficosDelMes.drawImage(numero, (anchoEspacio + espacioEntreDiasVertical) * 4, 0, null);
                        break;
                    case 7://sabado
                        graficosDelMes.drawImage(numero, (anchoEspacio + espacioEntreDiasVertical) * 5, 0, null);
                        break;
                    case 1://domingo
                        graficosDelMes.drawImage(numero, (anchoEspacio + espacioEntreDiasVertical) * 6, 0, null);
                        //bajar el alto del renglon para escribir los demas dias
                        graficosDelMes.translate(0, alturaRenglon + espacioEntreDiasHorizontal);
                        break;
                }
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            meses.put(i, calendarioDelMes);
        }
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Crear los Calendarios de los meses">
    public static BufferedImage creandoCalendarioMesesVertical() {
        int anchoCalendario = anchoMes * 3 + espacioEntreMesesVertical * 2;
        int altoCalendario = altoMes * 4 + espacioEntreMesesHorizontal * 3 + tamanoAnno + espacioEntreDiasHorizontal;
//pintar los numero de los dias para pintarlos una sola vez y luego rehuzarlos
        //pintar los nombres de la semana
        BufferedImage calendarioAño = new BufferedImage(anchoCalendario, altoCalendario, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2DCalendario = calendarioAño.createGraphics();
        graphics2DCalendario.setColor(fondo);
        graphics2DCalendario.fill(new Rectangle2D.Double(0, 0, anchoCalendario, tamanoAnno));
        graphics2DCalendario.setColor(colorAnno);
        graphics2DCalendario.setFont(graphics2DCalendario.getFont().deriveFont(Font.BOLD, (float) tamanoAnno));

        FontMetrics fontMetrics = graphics2DCalendario.getFontMetrics();
        String mensaje = "FELIZ " + anno + " FIGURA!!!";
        int anchoAnno = fontMetrics.stringWidth(mensaje);
        graphics2DCalendario.drawString(mensaje, anchoCalendario / 2 - anchoAnno / 2, tamanoAnno - fontMetrics.getDescent() / 2);
//        graphics2DCalendario.drawString(anno + "", 0, tamanoAnno);
        graphics2DCalendario.translate(0, tamanoAnno + espacioEntreDiasHorizontal);
        for (int i = 0; i < 12; i++) {
            switch (i) {//dibujar el mes en el calendario
                case 0:
                    graphics2DCalendario.drawImage(meses.get(i), 0, 0, null);
                    break;
                case 1:
                    graphics2DCalendario.drawImage(meses.get(i), anchoMes + espacioEntreMesesVertical, 0, null);
                    break;
                case 2:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 2, 0, null);
                    break;
                case 3:
                    graphics2DCalendario.drawImage(meses.get(i), 0, altoMes + espacioEntreMesesHorizontal, null);
                    break;
                case 4:
                    graphics2DCalendario.drawImage(meses.get(i), anchoMes + espacioEntreMesesVertical, altoMes + espacioEntreMesesHorizontal, null);
                    break;
                case 5:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 2, altoMes + espacioEntreMesesHorizontal, null);
                    break;
                case 6:
                    graphics2DCalendario.drawImage(meses.get(i), 0, (altoMes + espacioEntreMesesHorizontal) * 2, null);
                    break;
                case 7:
                    graphics2DCalendario.drawImage(meses.get(i), anchoMes + espacioEntreMesesVertical, (altoMes + espacioEntreMesesHorizontal) * 2, null);
                    break;
                case 8:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 2, (altoMes + espacioEntreMesesHorizontal) * 2, null);
                    break;
                case 9:
                    graphics2DCalendario.drawImage(meses.get(i), 0, (altoMes + espacioEntreMesesHorizontal) * 3, null);
                    break;
                case 10:
                    graphics2DCalendario.drawImage(meses.get(i), anchoMes + espacioEntreMesesVertical, (altoMes + espacioEntreMesesHorizontal) * 3, null);
                    break;
                case 11:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 2, (altoMes + espacioEntreMesesHorizontal) * 3, null);
                    break;
            }

        }
        return calendarioAño;
    }
    //  ImageIO.write(calendarioAño, "PNG", new File("calendario " + anno + ".png"));

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="creandoCalendarioMesesHorizontal">
    private static BufferedImage creandoCalendarioMesesHorizontal() {

        int anchoCalendario = anchoMes * 4 + espacioEntreMesesVertical * 3;
        int altoCalendario = altoMes * 3 + espacioEntreMesesHorizontal * 2 + tamanoAnno + espacioEntreDiasHorizontal;
//pintar los numero de los dias para pintarlos una sola vez y luego rehuzarlos

        //pintar los nombres de la semana
        BufferedImage calendarioAño = new BufferedImage(anchoCalendario, altoCalendario, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2DCalendario = calendarioAño.createGraphics();
        graphics2DCalendario.setColor(fondo);
        graphics2DCalendario.fill(new Rectangle2D.Double(0, 0, anchoCalendario, tamanoAnno));
        graphics2DCalendario.setColor(colorAnno);
        graphics2DCalendario.setFont(graphics2DCalendario.getFont().deriveFont(Font.BOLD, (float) tamanoAnno));

        FontMetrics fontMetrics = graphics2DCalendario.getFontMetrics();
        String mensaje = "FELIZ " + anno + " FIGURA!!!";
        int anchoAnno = fontMetrics.stringWidth(mensaje);
        graphics2DCalendario.drawString(mensaje, anchoCalendario / 2 - anchoAnno / 2, tamanoAnno - fontMetrics.getDescent() / 2);

//        graphics2DCalendario.drawString(anno + "", 0, tamanoAnno);
        graphics2DCalendario.translate(0, tamanoAnno + espacioEntreDiasHorizontal);

        for (int i = 0; i < 12; i++) {
            switch (i) {//dibujar el mes en el calendario
                case 0:
                    graphics2DCalendario.drawImage(meses.get(i), 0, 0, null);
                    break;
                case 1:
                    graphics2DCalendario.drawImage(meses.get(i), anchoMes + espacioEntreMesesVertical, 0, null);
                    break;
                case 2:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 2, 0, null);
                    break;
                case 3:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 3, 0, null);
                    break;
                case 4:
                    graphics2DCalendario.drawImage(meses.get(i), 0, altoMes + espacioEntreMesesHorizontal, null);
                    break;
                case 5:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical), altoMes + espacioEntreMesesHorizontal, null);
                    break;
                case 6:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 2, altoMes + espacioEntreMesesHorizontal, null);
                    break;
                case 7:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 3, altoMes + espacioEntreMesesHorizontal, null);
                    break;
                case 8:
                    graphics2DCalendario.drawImage(meses.get(i), 0, (altoMes + espacioEntreMesesHorizontal) * 2, null);
                    break;
                case 9:
                    graphics2DCalendario.drawImage(meses.get(i), anchoMes + espacioEntreMesesVertical, (altoMes + espacioEntreMesesHorizontal) * 2, null);
                    break;
                case 10:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 2, (altoMes + espacioEntreMesesHorizontal) * 2, null);
                    break;
                case 11:
                    graphics2DCalendario.drawImage(meses.get(i), (anchoMes + espacioEntreMesesVertical) * 3, (altoMes + espacioEntreMesesHorizontal) * 2, null);
                    break;
            }
        }
        //  ImageIO.write(calendarioAño, "PNG", new File("calendario " + anno + ".png"));
        return calendarioAño;
    }
//</editor-fold>

    private static void inicializar(int anno) throws IOException {
        llenarNumeroDias();
        llenarNumeroDiasRojos();
        pintarDiasSemana();
        pintarMeses(anno);
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        int annoPa = 2015, ancho = 1050, alto = 1500, espacioH = 5, espacioV = 5;
        inicializar(annoPa);
        BufferedImage calendVertical = ImageWork.getScaledInstace(ancho - espacioV * 2, alto - espacioH * 2, Image.SCALE_SMOOTH, creandoCalendarioMesesVertical());
        BufferedImage calendHorizontal = ImageWork.getScaledInstace(alto - espacioV * 2, ancho - espacioH * 2, Image.SCALE_SMOOTH, creandoCalendarioMesesHorizontal());
        File chooserSelectedFile = new File("G:\\Fabiel\\Mis Documentos\\Imagenes\\");
        File dir = new File(chooserSelectedFile, annoPa + "");
        dir.mkdirs();
        File[] listFiles = chooserSelectedFile.listFiles(FileFiltExt.filtroDeImagenes());
        for (File listFile : listFiles) {
            BufferedImage read = ImageIO.read(listFile);
            if (read.getHeight() >= read.getWidth()) {
                read = ImageWork.getScaledInstace(ancho, alto, Image.SCALE_SMOOTH, read);
                Graphics graphics = read.getGraphics();
                graphics.drawImage(calendVertical, espacioV, espacioH, null);
            } else {
                read = ImageWork.getScaledInstace(alto, ancho, Image.SCALE_SMOOTH, read);
                Graphics graphics = read.getGraphics();
                graphics.drawImage(calendHorizontal, espacioV, espacioH, null);
            }
            ImageIO.write(read, "PNG", new File(dir, listFile.getName()));
        }
//        creandoCalendarioMeses(Integer.parseInt(args[0]));
    }

}
