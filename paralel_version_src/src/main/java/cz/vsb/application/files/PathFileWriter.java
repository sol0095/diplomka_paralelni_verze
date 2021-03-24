package cz.vsb.application.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PathFileWriter {
    private static BufferedWriter bufferedWriter;

    public static synchronized void write(String str){
        try {
            bufferedWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startWriting(String path){
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(path, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopWriting(){
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
