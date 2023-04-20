package banner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Banner {

    public void bannerReader() throws IOException {
        File file = new File("./src/main/resources/Banner.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine())!=null){
            System.out.println(line);
        }
    }
}