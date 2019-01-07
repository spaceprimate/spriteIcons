import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;


/**
 * Creates a png sprite sheet in multiple colors from all png icons
 * stored in the icons_input directory
 *
 * incoming icons must be 32x32, transparent, png files
 */
public class SpriteIcons {

    // array of rgb values for colors
    private static int[][] colors = {
            {0,0,0},        // black
            {46,46,46},     // dark grey
            {100,100,100},  // light grey
            {255,255,255},  // white
            {20,108,173}    // sky blue
    };
    // color names used for css classes
    private static String[] colorNames = {
            "black",
            "grey",
            "lt-grey",
            "white",
            "blue"
    };

    public static void main(String[] args) {

        try {
            ArrayList<BufferedImage> imgs = new ArrayList<>();
            File pngDir = new File("icons_input");
            File[] pngFiles = pngDir.listFiles();
            for (int i = 0; i < pngFiles.length; i++) {
                imgs.add(ImageIO.read(pngFiles[i]));
            }

            BufferedImage img = genPng(imgs);
            ImageIO.write(img, "png", new File("icons_output/icons.png"));

            genCss(pngFiles, img.getWidth(), img.getHeight());
            genHtml(pngFiles);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static void genCss(File[] files, int width, int height){
        width = width /2;
        height = height / 2;
        String css =    ".icon{\n" +
                "   display: inline-block;\n" +
                "   background: url('icons.png') no-repeat;\n" +
                "   overflow: hidden;\n" +
                "   text-indent: -9999px;\n" +
                "   text-align: left;\n" +
                "   width: 16px;\n" +
                "   height: 16px;\n" +
                "   background-size: " + width + "px " + height + "px;\n" +
                "   margin-top: 2px;\n" +
                "}\n" +

                ".icon.icon-left{\n" +
                "   float: left;\n" +
                "   margin-right: 7px;\n" +
                "}\n" +
                ".icon.icon-right{\n" +
                "   float: right;\n" +
                "   margin-right: 7px;\n" +
                "}\n" +
                ".icon.icon-sm{\n" +
                "    background-size: " + (width/2) + "px " + (height / 2) + "px;\n" +
                "    width: 8px; height: 8px;\n" +
                "}\n" +
                ".icon.icon-md{\n" +
                "    background-size: " + ( (width * 4) / 3) + "px " + ((height * 4) / 3) + "px;\n" +
                "    width: 24px; height: 24px;\n" +
                "}\n" +
                ".icon.icon-lg{\n" +
                "    background-size: " + (width*2) + "px " + (height*2) + "px;\n" +
                "    width: 36px; height: 36px;\n" +
                "}\n";

        String name;
        int x,y;
        for (int i = 0; i < files.length; i++) {
            y = (i * 18) + 2;
            x = 2;

            name = files[i].getName();
            name = name.substring(0, name.lastIndexOf('.'));

            css += ".icon-" + name + "{background-position: -" + x + "px -" + y + "px;}\n";
            css += ".icon-" + name + ".icon-sm{background-position: -" + x + "px -" + ( (i * 9) ) + "px;}\n";
            css += ".icon-" + name + ".icon-md{background-position: -" + x + "px -" + ( (i * 24) + 2 ) + "px;}\n";
            css += ".icon-" + name + ".icon-lg{background-position: -" + x + "px -" + ( (i * 36) + 2 ) + "px;}\n";
            //print colors

        }

        for (int j = 0; j < colorNames.length; j++) {
            x = (j * 18) + 2;
            css += ".icon-" + colorNames[j] + "{background-position-x: -" + x + "px;}\n";
            css += ".icon-" + colorNames[j] + ".icon-sm{background-position-x: -" + ((j * 9) ) + "px;}\n";
            css += ".icon-" + colorNames[j] + ".icon-md{background-position-x: -" + ((j * 24) + 2) + "px;}\n";
            css += ".icon-" + colorNames[j] + ".icon-lg{background-position-x: -" + ((j * 36) + 2) + "px;}\n";
        }

        css += ".icon-hover:hover{background-position-x: -" + ((18 * (colorNames.length - 1 )) + 2 ) + "px;}\n";
        css += ".icon-hover.icon-sm:hover{background-position-x: -" + ((9 * (colorNames.length - 1 )) ) + "px;}\n";
        css += ".icon-hover.icon-md:hover{background-position-x: -" + ((24 * (colorNames.length - 1 )) + 2 ) + "px;}\n";
        css += ".icon-hover.icon-lg:hover{background-position-x: -" + ((36 * (colorNames.length - 1 )) + 2 ) + "px;}\n";

        css += ".icon-" + colorNames[colorNames.length - 1] + ".icon-hover:hover{background-position-x: -2px;}\n";
        css += ".icon-" + colorNames[colorNames.length - 1] + ".icon-sm.icon-hover:hover{background-position-x: 0px;}\n";
        css += ".icon-" + colorNames[colorNames.length - 1] + ".icon-md.icon-hover:hover{background-position-x: -2px;}\n";
        css += ".icon-" + colorNames[colorNames.length - 1] + ".icon-lg.icon-hover:hover{background-position-x: -2px;}\n";





        try(PrintStream out = new PrintStream(new FileOutputStream("icons_output/icons.css"))){
            out.print(css);
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
    }

    private static void genHtml(File[] files){
        String name;
        String html =   "<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Sprite Icons</title>\n" +
                "    <link rel='stylesheet' type='text/css' href='icons.css'>\n" +
                "</head>\n" +
                "<body style='background: #ffe188;'>\n" +
                "    <div style='width: 710px; max-width: 100%; margin: auto;'>\n" +
                "       <h1 style='text-align:center;'>refreshing sprite</h1>\n" +
                "       <div style='width: 110px; max-width: 50%; padding: 18px; float:left'>\n" +
                "           <p>default size: </p>";


        for (int i = 0; i < files.length; i++) {
            name = files[i].getName();
            name = name.substring(0, name.lastIndexOf('.'));
            for (int j = 0; j < colorNames.length; j++) {
                html += "<span class='icon icon-" + name + " icon-" + colorNames[j] + " icon-hover'></span>";
            }
            html += "<br>";
        }

        html +=         "    </div>\n" +
                "    <div style='width: 67px; max-width: 50%; padding: 18px; float:left'>\n" +
                "       <p>.icon-sm</p>";

        for (int i = 0; i < files.length; i++) {
            name = files[i].getName();
            name = name.substring(0, name.lastIndexOf('.'));
            for (int j = 0; j < colorNames.length; j++) {
                html += "<span class='icon icon-" + name + " icon-" + colorNames[j] + " icon-hover icon-sm'></span>";
            }
            html += "<br>";
        }

        html +=         "    </div>\n" +
                "    <div style='width: 168px; max-width: 50%; padding: 18px; float:left'>\n" +
                "       <p>.icon-md</p>";

        for (int i = 0; i < files.length; i++) {
            name = files[i].getName();
            name = name.substring(0, name.lastIndexOf('.'));
            for (int j = 0; j < colorNames.length; j++) {
                html += "<span class='icon icon-" + name + " icon-" + colorNames[j] + " icon-hover icon-md'></span>";
            }
            html += "<br>";
        }



        html +=         "    </div>\n" +
                "    <div style='width: 220px; max-width: 50%; padding: 18px; float:left'>\n" +
                "       <p>.icon-lg</p>";

        for (int i = 0; i < files.length; i++) {
            name = files[i].getName();
            name = name.substring(0, name.lastIndexOf('.'));
            for (int j = 0; j < colorNames.length; j++) {
                html += "<span class='icon icon-" + name + " icon-" + colorNames[j] + " icon-hover icon-lg'></span>";
            }
            html += "<br>";
        }

        html +=         "       </div>\n" +
                "   </div>\n" +
                "</body>\n" +
                "</html>";
        try(PrintStream out = new PrintStream(new FileOutputStream("icons_output/index.html"))){
            out.print(html);
        }
        catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
    }

    private static BufferedImage genPng(ArrayList<BufferedImage> imgs) {

        //dimensions of incoming icons
        int width = 32;
        int height = 32;

        //calculate dimension of final png
        int newHeight = 36 * (imgs.size());
        int newWidth = 36 * (colors.length);

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster;
        WritableRaster rasterNew = newImage.getRaster();
        int yMod = 0;
        for (int i = 0; i < imgs.size(); i++) {
            yMod = i * 36;
            raster = imgs.get(i).getRaster();

            for (int xx = 0; xx < width; xx++) {
                for (int yy = 0; yy < height; yy++) {
                    int[] pixels = raster.getPixel(xx, yy, (int[]) null);

                    for (int cc = 0; cc < colors.length; cc++){
                        pixels[0] = colors[cc][0];
                        pixels[1] = colors[cc][1];
                        pixels[2] = colors[cc][2];
                        rasterNew.setPixel(xx + 2 + (cc * 36), yy + 2 + yMod, pixels);
                    }
                }
            }
        }

        return newImage;
    }

}
