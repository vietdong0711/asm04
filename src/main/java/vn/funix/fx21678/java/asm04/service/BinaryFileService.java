package vn.funix.fx21678.java.asm04.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileService {

    public static <T> List<T> readFile(String filename) {
        try{
            FileInputStream readData = new FileInputStream(filename);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            List<T> rs = (ArrayList<T>) readStream.readObject();
            readStream.close();
            return rs;

        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<T>();
        }
//        return null;
    }

    public static <T> void writeFile(String filename , List<T> objects) throws IOException {
        //write to file
        try{
            FileOutputStream writeData = new FileOutputStream(filename);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(objects);
            writeStream.flush();
            writeStream.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

}
