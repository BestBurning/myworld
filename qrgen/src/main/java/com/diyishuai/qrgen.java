package com.diyishuai;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * @author Bruce
 * @since 2018/5/16
 */
public class qrgen {

    public static void main(String[] args) throws FileNotFoundException {
        String path = "/Users/yysz/Documents/testQR.png";
        // get QR file from text using defaults
//        File file = QRCode.from("Hello World").file();

        // get QR stream from text using defaults
//        ByteArrayOutputStream stream = QRCode.from("Hello World").stream();
//
//        // override the image type to be JPG
//        File qrFile = QRCode.from("Hello World").to(ImageType.PNG).file(path);
//        QRCode.from("Hello World").to(ImageType.JPG).stream();
//
//        // override image size to be 250x250
//        QRCode.from("Hello World").withSize(250, 250).file();
//        QRCode.from("Hello World").withSize(250, 250).stream();
//
//        // override size and image type
        QRCode.from("https://di1shuai.com")
                .to(ImageType.PNG)
                .withSize(250, 250)
                .writeTo(new FileOutputStream(new File(path)));
//        QRCode.from("Hello World").to(ImageType.GIF).withSize(250, 250).stream();
//
//        // override default colors (black on white)
//        // notice that the color format is "0x(alpha: 1 byte)(RGB: 3 bytes)"
//        // so in the example below it's red for foreground and yellowish for background, both 100% alpha (FF).
//        QRCode.from("Hello World").withColor(0xFFFF0000, 0xFFFFFFAA).file();
//
//        // supply own outputstream
//        QRCode.from("Hello World").to(ImageType.PNG).writeTo(outputStream);
//
//        // supply own file name
//        QRCode.from("Hello World").file("QRCode");
//
//        // supply charset hint to ZXING
//        QRCode.from("Hello World").withCharset("UTF-8");
//
//        // supply error correction level hint to ZXING
//        QRCode.from("Hello World").withErrorCorrection(ErrorCorrectionLevel.L);
//
//        // supply any hint to ZXING
//        QRCode.from("Hello World").withHint(EncodeHintType.CHARACTER_SET, "UTF-8");
//
//        // encode contact data as vcard using defaults
//        VCard johnDoe = new VCard("John Doe")
//                .setEmail("john.doe@example.org")
//                .setAddress("John Doe Street 1, 5678 Doestown")
//                .setTitle("Mister")
//                .setCompany("John Doe Inc.")
//                .setPhoneNumber("1234")
//                .setWebsite("www.example.org");
//        QRCode.from(johnDoe).file();
//
//        // encode email data
//        EMail email = new EMail("John.Doe@example.org");
//        QRCode.from(email).file();
//
//        // encode mms data
//        MMS mms = new MMS("Hello World");
//        QRCode.from(mms).file();
//
//        // encode sms data
//        SMS sms = new SMS("Hello World");
//        QRCode.from(sms).file();
//
//        // encode MeCard data
//        MeCard johnDoe = new MeCard("John Doe");
//        johnDoe.setEmail("john.doe@example.org");
//        johnDoe.setAddress("John Doe Street 1, 5678 Doestown");
//        johnDoe.setTelephone("1234");
//        QRCode.from(johnDoe).file();
//
//        // if using special characters don't forget to supply the encoding
//        VCard johnSpecial = new VCard("Jöhn Dɵe")
//                .setAddress("ëåäöƞ Sträät 1, 1234 Döestüwn");
//        QRCode.from(johnSpecial).withCharset("UTF-8").file();

        // QRGen currently supports the following schemas:
        // - BizCard
        // - Bookmark
        // - Email
        // - GeoInfo
        // - Girocode
        // - GooglePlay
        // - ICal
        // - KddiAu
        // - MMS
        // - MeCard
        // - SMS
        // - Telephone
        // - Url
        // - VCard
        // - Wifi
        // - YouTube
    }

}
