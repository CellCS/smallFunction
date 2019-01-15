import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class Main {

    private static String OS = System.getProperty("os.name").toLowerCase();
    private int IMG_WIDTH = 676;
    private static final int IMG_HEIGHT = 100;

    private static final String[] okFileExtensions = new String[] {"jpg", "png", "gif"};


    public static void main(String[] args) {
        
        System.out.println(OS);

        if (isWindows()) {
            System.out.println("This is Windows");
        } else if (isMac()) {
            System.out.println("This is Mac");
        } else if (isUnix()) {
            System.out.println("This is Unix or Linux");
        } else if (isSolaris()) {
            System.out.println("This is Solaris");
        } else {
            System.out.println("Your OS is not support!!");
        }

        // print out all java setting env
        //System.getProperties().list(System.out);



        String inputStr = "";
        String defaultLocation = "/Users/?????/Desktop/Images";
    //"C:\\Users\\???????\\Desktop\\New folder"

        System.out.println("Please type the folder directory! like: C:\\Users\\????\\Desktop\\New folder");
        System.out.println("Default is : C:\\Users\\bmesy\\Desktop\\New folder");
        System.out.println("Please type \"y\" if you choose default dir. Or type your own dir:");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));




        try {
            inputStr = br.readLine();
            if(inputStr.indexOf("y")==0){
                inputStr = defaultLocation;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Please choose resize image by ratio or by a width:");
        System.out.println("1: by Ratio; 2: by Width (default is 676)");
        BufferedReader methodChoice = new BufferedReader(new InputStreamReader(System.in));
        int resizeMethod =2;
        float userSetRatio=0;
        try {
            String inputChoiceStr = methodChoice.readLine();
            if(Integer.parseInt(inputChoiceStr)==1){
                resizeMethod = 1;
                System.out.println("Please type the ration:");
                BufferedReader userRationInput = new BufferedReader(new InputStreamReader(System.in));
                String inputRatio = methodChoice.readLine();
                userSetRatio = Float.parseFloat(inputRatio);
            } else if(Integer.parseInt(inputChoiceStr)==2){resizeMethod = 2;}
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean isUserSetRation = false;
        if(resizeMethod ==1 ){isUserSetRation = true;}

        File folder = new File(inputStr);
        File[] listOfFiles = folder.listFiles();

        // List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()&&accept(listOfFiles[i])) {
                System.out.println(listOfFiles[i].getName());
                resizeImage(inputStr,listOfFiles[i].getName(),i,userSetRatio,isUserSetRation);
            } else if (listOfFiles[i].isFile()&&!accept(listOfFiles[i])) {
                //delete

            }else if (listOfFiles[i].isDirectory()){
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

    }

    public static void resizeImage(String dir, String imageName, int index, float userSetRatio, Boolean isUserSetRation) {
        String finalName = dir + "/" + "img-" + Integer.toString(index)+".png";
        float ration = 0;
        try {
            File input = new File(dir + "/" + imageName);
            BufferedImage image = ImageIO.read(input);
            int originalWidth = image.getWidth();
            if(isUserSetRation){ration = userSetRatio;}else{ration = (float) 676 / originalWidth;}

            BufferedImage resized = resizeByRation(image,ration);
            File output = new File(finalName);
            ImageIO.write(resized, "png", output);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean accept(File file)
    {
        for (String extension : okFileExtensions)
        {
            if (file.getName().toLowerCase().endsWith(extension))
            {
                return true;
            }
        }
        return false;
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    private static BufferedImage resizeByRation(BufferedImage img, float ratio) {
        int width = (int) Math.round(img.getWidth()*ratio);
        int height = (int) Math.round(img.getHeight()*ratio);
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

        public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
        
    }

    public static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);

    }


}


