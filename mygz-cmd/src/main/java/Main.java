import storyteller_f.Start;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        Start start=new Start();
        String configPath = "/config.yaml";
        URL resource = Main.class.getResource(configPath);
        File file = new File("./config.yaml");
        if (file.exists()) {
            System.out.println(file.getAbsolutePath());
            try {
                start.config(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (resource != null) {
                System.out.println("resources");
                start.config(Start.class.getResourceAsStream(configPath));
            } else {
                System.out.println("没要找到配置文件");
            }
        }
    }
}
