package Sequential;

import Utils.Utils;

import java.awt.Color;
import java.io.IOException;
import java.util.Random;

/**
 * Creating image filters for grayscale, brighter, swirl,
 * glass and blur effect
 *
 * @author Jorge Coelho
 * @version 1.0
 * @contact jmn@isep.ipp.pt
 * @since 2021-02-15
 */
public class Filters {

    String file;
    Color image[][];
    Random rando = new Random();

    // Constructor with filename for source image
    Filters(String filename) {
        this.file = filename;
        image = Utils.loadImage(filename);
    }


    // Brighter filter works by adding value to each of the red, green and blue of each pixel
    // up to the maximum of 255
    public void BrighterFilter(String outputFile, int value) {
        Color[][] tmp = Utils.copyImage(image);

        // Runs through entire matrix
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {

                // fetches values of each pixel
                Color pixel = tmp[i][j];
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                // takes average of color values
                int bright = value;
                if (r + bright > 255)
                    r = 255;
                else
                    r = r + bright;
                if (g + bright > 255)
                    g = 255;
                else
                    g = g + bright;
                if (b + bright > 255)
                    b = 255;
                else
                    b = b + bright;

                // outputs average into picuture to make grayscale
                tmp[i][j] = new Color(r, g, b);

            }
        }
        Utils.writeImage(tmp, outputFile);
    }



    // Brighter filter works by adding value to each of the red, green and blue of each pixel
    public void GreyScaleFilter(String outputFile) {
        Color[][] tmp = Utils.copyImage(image);

        // Runs through entire matrix
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                Color pixel = tmp[i][j];
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();

                int average = (r + g + b) / 3;
                r = average;
                g = average;
                b = average;
                tmp[i][j] = new Color(r, g, b);

            }
        }
        Utils.writeImage(tmp, outputFile);
    }

    // The swirl filter or warp filter consists in rotating the image
    public void SwirlFilter(String outputFile) {
        Color[][] tmp = Utils.copyImage(image);
        Color[][] clone = Utils.copyImage(image);


        int width = tmp.length;
        int height = tmp[0].length;

        double x0 = 0.5 * (width - 1);
        double y0 = 0.5 * (height - 1);

        // Runs through entire matrix

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double dx = i - x0;
                double dy = j - y0;

                double distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                double angle = (Math.PI / 256) * distance;

                int x = (int) ((Math.cos(angle) * dx) - (Math.sin(angle) * dy) + x0);
                int y = (int) ((Math.sin(angle) * dx) + (Math.cos(angle) * dy) + y0);

                if (x >= 0 && x < width && y >= 0 && y < height) {
                    Color pixel = tmp[x][y];
                    int r = pixel.getRed();
                    int g = pixel.getGreen();
                    int b = pixel.getBlue();
                    clone[i][j] = new Color(r, g, b);
                }
            }
        }
        Utils.writeImage(clone, outputFile);
    }

    // Glass filter
    public void GlassFilter(String outputFile) {
        Color[][] tmp = Utils.copyImage(image);
        Color[][] clone = Utils.copyImage(image);

        int width = tmp.length;
        int height = tmp[0].length;

        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                int randomNumber = Utils.random(rando, -5, 5);
                int newX = (width + i + randomNumber) % width;
                int newY = (height + j + randomNumber) % height;
                Color pixel = tmp[newX][newY];
                int r = pixel.getRed();
                int g = pixel.getGreen();
                int b = pixel.getBlue();
                clone[i][j] = new Color(r, g, b);
            }
        }
        Utils.writeImage(clone, outputFile);
    }



    //Blur
    public void BlurFilter(String outputFile) {
        Color[][] tmp = Utils.copyImage(image);
        Color[][] clone = Utils.copyImage(image);

        int width = tmp.length;
        int height = tmp[0].length;

        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[i].length; j++) {
                int redSum = 0;
                int greenSum = 0;
                int blueSum = 0;

                for (int x = i - 1; x < i + 2; x++) {
                    for (int y = j - 1; y < j + 2; y++) {
                        if (x >= 0 && x < width && y >= 0 && y < height) {
                            Color pixel = tmp[x][y];
                            redSum += pixel.getRed();
                            greenSum += pixel.getGreen();
                            blueSum += pixel.getBlue();
                        }
                    }
                }

                int r = redSum / 9;
                int g = greenSum / 9;
                int b = blueSum / 9;
                clone[i][j] = new Color(r, g, b);
            }
        }
        Utils.writeImage(clone, outputFile);
    }


}
