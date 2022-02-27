package com.server.signalisation.services;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;
 

public class FileManagement {
	 
	
    /**
     * Resizes an image to a absolute width and height (the image may not be
     * proportional)
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param scaledWidth absolute width in pixels
     * @param scaledHeight absolute height in pixels
     * @throws IOException
     */
    public static void resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
 
        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());
 
        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
 
        // extracts extension of output file
        String formatName = outputImagePath.substring(outputImagePath
                .lastIndexOf(".") + 1);
 
        // writes to output file
        ImageIO.write(outputImage, formatName, new File(outputImagePath));
    }
 
    /**
     * Resizes an image by a percentage of original size (proportional).
     * @param inputImagePath Path of the original image
     * @param outputImagePath Path to save the resized image
     * @param percent a double number specifies percentage of the output image
     * over the input image.
     * @throws IOException
     */
    public static void resize(String inputImagePath, String outputImagePath, double percent) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        int scaledWidth = (int) (inputImage.getWidth() * percent);
        int scaledHeight = (int) (inputImage.getHeight() * percent);
        resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
    }
    
    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
    
    public static byte[] toJPG(File f) throws IOException { // png to jpg
        //Path source = Paths.get("D:\\Sary\\SaryPNG.png");
       // Path target = Paths.get("D:\\Sary\\SaryJPG.jpg");
        BufferedImage originalImage = ImageIO.read(f);
        // create a blank, RGB, same width and height
        BufferedImage newBufferedImage = new BufferedImage( originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        // draw a white background and puts the originalImage on it.
        newBufferedImage.createGraphics().drawImage(originalImage,0,0,Color.WHITE,null);
        // save an image
        //ImageIO.write(newBufferedImage, "jpg", target.toFile());
        return toByteArray(newBufferedImage, "jpg");
    }
 
    /**
     * Test resizing images
     */
    public static void main(String[] args) {
        String inputImagePath = "D:/Sary/SaryPNG.png"; // image a compresse
        String outputImagePath1 = "D:/Sary/Test_Fixed.jpg"; // sortie
        String outputImagePath2 = "D:/Sary/Test_Smaller.jpg"; // sortie
        String outputImagePath3 = "D:/Sary/Test_Bigger.jpg"; // sortie
 
        try {
            // resize to a fixed width (not proportional)
            int scaledWidth = 1024;
            int scaledHeight = 768;
            FileManagement.resize(inputImagePath, outputImagePath1, scaledWidth, scaledHeight);
 
            // resize smaller by 50%
            double percent = 0.5;
            FileManagement.resize(inputImagePath, outputImagePath2, percent);
 
            // resize bigger by 50%
            percent = 1.5;
            FileManagement.resize(inputImagePath, outputImagePath3, percent);
 
        } catch (IOException ex) {
            System.out.println("Error resizing the image.");
            ex.printStackTrace();
        }
        /*try {
            toJPG();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
    }
 
}
