package Utils;


import net.steppschuh.markdowngenerator.text.Text;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public class Utils {

  Utils() {}
 

   /**
   * Loads image from filename into a Color (pixels decribed with rgb values) matrix.
   * 
   * @param filename the name of the imge in the filesystem.
   * @return Color matrix.
   */
  public static Color[][] loadImage(String filename) {
    BufferedImage buffImg = loadImageFile(filename);
    Color[][] colorImg = convertTo2DFromBuffered(buffImg);
    return colorImg;
  }

  /**
   * Converts image from a Color matrix to a .jpg file.
   * 
   * @param image the matrix of Color objects.
   * @param filename to the image.
   */
  public static void writeImage(Color[][] image,String filename){
    File outputfile = new File(filename);
		var bufferedImage = Utils.matrixToBuffered(image);
		try {
          ImageIO.write(bufferedImage, "jpg", outputfile);
        } catch (IOException e) {
          System.out.println("Could not write image "+filename+" !");
          e.printStackTrace();
          System.exit(1);
        }
  }


  /**
   * Loads in a BufferedImage from the specified path to be processed.
   * @param filename The path to the file to read.
   * @return a BufferedImage if able to be read, NULL otherwise.
   */
  private static BufferedImage loadImageFile(String filename) {
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(filename));
    } catch (IOException e) {
      System.out.println("Could not load image "+filename+" !");
      e.printStackTrace();
      System.exit(1);
    }
    return img;
  }


  public static String getOutputFileName(String fileName, String filterAndImplementation){
    String [] nameFileArray = fileName.split(".jpg");
    return nameFileArray[0] + filterAndImplementation;
  }

  /**
   *  Copy a Color matrix to another Color matrix. 
   *  Useful if one does not want to modify the original image.
   * 
   * @param image the source matrix
   * @return a copy of the image
   */
  
  public static Color[][] copyImage(Color[][] image) {
    Color[][] copy = new Color[image.length][image[0].length];
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[i].length; j++) {
        copy[i][j] = image[i][j];
      }
    }
    return copy;
  }

  public static String generateTable(long duration, String filter,String nThreads, String nameFile) {
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
    String dateString = sdf.format(new Date());
    StringBuilder s = new StringBuilder()
            .append(new Text("| " + filter + " |" +  duration +" | "+ nameFile + " | " + nThreads + " | " + dateString +" |"));

    return s.toString();
  }

  public static String generateTable(String implementation, long duration, String filter,String nThreads, String nameFile) {
    SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
    String dateString = sdf.format(new Date());
    StringBuilder s = new StringBuilder()
            .append(new Text("| " + implementation + "| " + filter + " |" +  duration +" | "+ nameFile + " | " + nThreads + " | " + dateString +" |"));

    return s.toString();
  }

  public static void writeFile(String s, String filename) throws IOException {
    FileWriter myWriter = new FileWriter(filename,true);
    myWriter.write(System.getProperty( "line.separator" ));
    myWriter.write(s);
    myWriter.close();
  }

  public static String getFileName(String filePath){

    String [] nameFileArray = filePath.split("/");
    if(nameFileArray.length == 1) {
      String separator = "\\";
      nameFileArray = filePath.split(Pattern.quote(separator));
    }
    int lengthNameFile = nameFileArray.length;

    return nameFileArray[lengthNameFile-1];
  }
  
  /**
   * Converts a matrix of Colors into a BufferedImage to 
   *  write on the filesystem.
   * 
   * @param image the matrix of Colors
   * @return the image ready for writing to filesystem
   */
  private static BufferedImage matrixToBuffered(Color[][] image) {
    int width = image.length;
    int height = image[0].length;
    BufferedImage bImg = new BufferedImage(width, height, 1);

    for (int x = 0; x < width; x++) {
      for(int y = 0; y < height; y++) {
        bImg.setRGB(x,  y, image[x][y].getRGB());
      }
    }
    return bImg;
  }

  /**
   * Converts a file loaded into a BufferedImage to a 
   * matrix of Colors
   * 
   * @param image the BufferedImage to convert
   * @return the matrix of Colors
   */

  private static Color[][] convertTo2DFromBuffered(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    Color[][] result = new Color[width][height];

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        // Get the integer RGB, and separate it into individual components.
        // (BufferedImage saves RGB as a single integer value).
        int pixel = image.getRGB(x, y);
        //int alpha = (pixel >> 24) & 0xFF;
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = pixel & 0xFF;
        result[x][y] = new Color(red, green, blue);
      }
    }
    return result;
  }

  public static CopyOnWriteArrayList copyToConcurrentList(Color[][] image) {
    CopyOnWriteArrayList<List<Color>> copy = new CopyOnWriteArrayList<>();
    for (int i = 0; i < image.length; i++) {
      CopyOnWriteArrayList<Color> innerList = new CopyOnWriteArrayList<>();
        for (int j = 0; j < image[i].length; j++) {
          innerList.add(new Color(0,0,0));
        }
      copy.add(innerList);
    }
      return copy;
  }

  public static int random(Random rando, int a, int b) {
    return a + rando.nextInt(b - a + 1);
  }

}