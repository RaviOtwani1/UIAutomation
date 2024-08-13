package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    static Properties prop ;
    public ConfigReader() {
        prop = new Properties();
        FileInputStream fi;
        try {
            fi = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/config.properties");
            prop.load(fi);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String  getHomeUrl(){
        return prop.getProperty("homeUrl");
    }

    public String  getDriver(){
        return prop.getProperty("browser");
    }
}
