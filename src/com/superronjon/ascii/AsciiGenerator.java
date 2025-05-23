package com.superronjon.ascii;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;

public class AsciiGenerator {
    private BufferedImage image;
    private double[][] brightnessValues;
    private final String[] asciiCharacters = { "@", "%", "#", "*", "+", "=", "-", ":", ".", " " };
    private final String[] asciiCharactersInverse = asciiCharacters.clone();

    public AsciiGenerator(BufferedImage img) {
		this(img, 1.0);
    }

    public AsciiGenerator(BufferedImage img, double scaling) {
		this(img, scaling, scaling);
    }

	public AsciiGenerator(BufferedImage img, double scalingWidth, double scalingHeight) {
		this.image = img;
		Collections.reverse(Arrays.asList(asciiCharactersInverse));
		if(scalingWidth != 1.0 || scalingHeight != 1.0) {
			scaleImage(scalingWidth, scalingHeight);
		}
		setBrightnessValues();
	}

    public void writeToFile(String outFileName) {
        writeToFile(outFileName, false);
    }

    public void writeToFile(String outFileName, boolean invert) {
        writeToFile(outFileName, invert, false);
    }

    public void writeToFile(String outFileName, boolean invert, boolean removeBorder) {

		File outFile = new File(outFileName);
		if(outFile.getParentFile() != null) {
			outFile.getParentFile().mkdirs();
		}
        PrintWriter writer;
        try {
			writer = new PrintWriter(outFileName, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.write(toString(invert, removeBorder));
        writer.close();
    }

    public String toString() {
        return toString(false);
    }

    public String toString(boolean invert) {
        return toString(invert, false);
    }

    public String toString(boolean invert, boolean removeBorder) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < brightnessValues.length; i++) {
            for(int j = 0; j < brightnessValues[i].length; j++) {
                int charIndex = (int) Math.floor(brightnessValues[i][j] / (255.1 / asciiCharacters.length));

                String brightnessChar = invert ? asciiCharactersInverse[charIndex] : asciiCharacters[charIndex];
                if(removeBorder && (i == brightnessValues.length - 1 || j == brightnessValues[i].length - 1)) {
                    if(!brightnessChar.equals("@")) {
                        builder.append(brightnessChar);
                    }
                }
                else {
                    builder.append(brightnessChar);
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private void setBrightnessValues() {
        brightnessValues = new double[image.getHeight()][image.getWidth()];
        for(int i = 0; i < image.getHeight() - 1; i++) {
            for(int j = 0; j < image.getWidth() - 1; j++) {
                brightnessValues[i][j] = getBrightnessValue(new Color(image.getRGB(j, i)));
            }
        }
    }

    private double getBrightnessValue(Color c) {
        final double pr = .299;
        final double pg = .587;
        final double pb = .114;

        return Math.sqrt((pr * Math.pow(c.getRed(), 2)) + (pg * Math.pow(c.getGreen(), 2)) + (pb * Math.pow(c.getBlue(), 2)));
    }

    private void scaleImage(double fWidth, double fHeight) {
        int dWidth = Double.valueOf(image.getWidth() * fWidth).intValue();
        int dHeight = Double.valueOf(image.getHeight() * fHeight).intValue();
        BufferedImage scaledImage = null;
        if(image != null) {
            scaledImage = new BufferedImage(dWidth, dHeight, image.getType());
            Graphics2D graphics2D = scaledImage.createGraphics();
            graphics2D.drawImage(image, 0, 0, dWidth, dHeight, null);
            graphics2D.dispose();
        }
        image = scaledImage;
    }
}
