package com.diyishuai.tess4j;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * @author: Bruce
 * @date: 2020-03-03
 * @description:
 */
public class Main {

    public static void main(String[] args) throws TesseractException {
        System.out.println(getTextByImage("C:\\Users\\SF\\Desktop\\20200303\\文字.png"));
    }

    public static String getTextByImage(String imgPath) throws TesseractException {
        File imageFile = new File(imgPath);
        if (!imageFile.exists()){
            throw new RuntimeException("图片不存在");
        }
        Tesseract tesseract = new Tesseract();
        // 设置训练库的位置,https://tesseract-ocr.github.io/tessdoc/Data-Files
        tesseract.setDatapath("C:\\Users\\SF\\Documents");
        // 设置识别语言为中文
        tesseract.setLanguage("chi_sim");

        String result = tesseract.doOCR(imageFile);
        return result;
    }

}
