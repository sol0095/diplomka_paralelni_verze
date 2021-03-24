package cz.vsb.application.files;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class InputFileReader {

    public static Stream<String> readFile(String path){
        try {
            return Files.lines(Paths.get(path), StandardCharsets.ISO_8859_1).parallel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }
}
