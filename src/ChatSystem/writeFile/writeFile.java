package ChatSystem.writeFile;

import java.io.*;
public class writeFile {
    public static void writeFile(String filePath ,String data){
        try{
            File file = new File(filePath);
            if(!file.isDirectory()){
                file.createNewFile();
            }
            file=new File(filePath);
            FileWriter fileWritter =new FileWriter(file, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void writeFile(String filePath){
        try{
            File file = new File(filePath);
            FileWriter fileWritter =new FileWriter(file, true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write('\n');
            bufferWritter.close();
            System.out.print("record successfully"+'\n');
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
