/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sam;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;

/**
 *
 * @author Sam
 * I got the basis for this library here http://fiehnlab.ucdavis.edu/staff/scholz/dev/java/labelwriter but that link no longer exists 
 * It can be used as a stand-alone JAR you add to your code, all you need to do to use it is something like this:
 * labelwriter.printLabel("test0", "test1", "test2", "test3", "test4", "test5", "test6", "test7", "test8", "test9", "test10", "test11");
 */
public class LabelWriter {

    public static final String PRINTERNAME = "DYMO LabelWriter";
    /**
     * true if you want a menu to select the printer
     */
    public static final boolean PRINTMENU = false;
    public static String printThis[] = new String[11];
    PrinterJob printerJob = PrinterJob.getPrinterJob();
    PageFormat pageFormat = printerJob.defaultPage();
    Paper paper = new Paper();

    public void printLabel(final String text0, final String text1, final String text2, final String text3, final String text4, final String text5, final String text6, final String text7,
            final String text8, final String text9, final String text10, final String text11) {


        final double widthPaper = (1.125 * 72);
        final double heightPaper = (3.5 * 72);

        paper.setSize(widthPaper, heightPaper);
        paper.setImageableArea(0, 0, widthPaper, heightPaper);

        pageFormat.setPaper(paper);

        pageFormat.setOrientation(PageFormat.LANDSCAPE);


        PrintService[] printService = PrinterJob.lookupPrintServices();

        for (int i = 0; i < printService.length; i++) {
            System.out.println(printService[i].getName());

            if (printService[i].getName().contains(PRINTERNAME)) {
                try {
                    printerJob.setPrintService(printService[i]);
                    printerJob.setPrintable(new Printable() {
                        @Override
                        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
                                throws PrinterException {
                            if (pageIndex < getPageNumbers()) {
                                Graphics2D g = (Graphics2D) graphics;
                                g.translate(20, 10);

                                String value = "";

                                int x = 10;
                                int y = 15;

                                // label under barcode
                                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 8));
                                value = text1;
                                g.drawString("" + value, x + 70, y + 10);

                                // Contract Number
                                g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 7));
                                value = text2;
                                g.drawString("" + value, x, y + 20);

                                // Code
                                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 7));
                                value = text3;
                                g.drawString("" + value, x, y + 27);

                                // Customer ID
                                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 7));
                                value = text4;
                                g.drawString("" + value, x, y + 34);


                                // Item Code
                                g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 7));
                                value = text5;
                                g.drawString("" + value, x + 35, y + 20);

                                // Code Descr
                                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 7));
                                value = text6;
                                g.drawString("" + value, x + 35, y + 27);

                                // Customer Name
                                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 7));
                                value = text7;
                                g.drawString("" + value, x + 35, y + 34);



                                // AD Code
                                g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 7));
                                value = text8;
                                g.drawString("" + value, x + 155, y + 20);

                                // Edition
                                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 7));
                                value = text9;
                                g.drawString("" + value, x + 155, y + 27);




                                // date
                                g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 7));
//                                value = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).format(new Date());
                                value = text10;
                                g.drawString(value, x, y + 40);

                                // Customer Account Number
                                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 7));
                                value = text11;
                                g.drawString("" + value, x + 35, y + 42);


                                InputStream ttf = this.getClass().getResourceAsStream("3of9.TTF");

                                Font font = null;
                                try {
                                    font = Font.createFont(Font.TRUETYPE_FONT, ttf);
                                } catch (FontFormatException | IOException ex) {
                                    Logger.getLogger(LabelWriter.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                g.setFont(font);

                                g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 24));
                                value = text0;

                                g.drawString(value, x, y);

                                return PAGE_EXISTS;
                            } else {
                                return NO_SUCH_PAGE;
                            }
                        }
                    }, pageFormat); // The 2nd param is necessary for printing into a label width a right landscape format.
                    printerJob.print();
                } catch (PrinterException e) {
                    e.printStackTrace();
                }
            }
        }


        System.exit(0);

    }

    public int getPageNumbers() {
        return 1;
    }
}
