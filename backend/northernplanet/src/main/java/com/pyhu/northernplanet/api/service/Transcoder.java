package com.pyhu.northernplanet.api.service;

import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
//
public class Transcoder {
//
   public static void file() throws IOException{
//
        //creating an empty presentation
        String path="/Users/gim-aeeun/file";
        File file=new File(path+"/happy.pptx");
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(file));
       System.out.println("file successfully created");

        //getting the dimensions and size of the slide
        Dimension pgsize = ppt.getPageSize();
        java.util.List<XSLFSlide> slide = ppt.getSlides();

        for (int i = 0; i < slide.size(); i++) {
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height,BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();

            //clear the drawing area
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));

            //render
            slide.get(i).draw(graphics);

            //creating an image file as output
            FileOutputStream out = new FileOutputStream(path+"/ppt_image_" + i + ".png");
            javax.imageio.ImageIO.write(img, "png", out);
            System.out.println("Image successfully created");
            out.close();
        }
    }
}