package ChatSystem.writeFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class writeLogin {
    public static void writeLogin(String filePath, String data){
        try{
            File file = new File(filePath);
            if(!file.isDirectory()){
                file.mkdirs();
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
}
