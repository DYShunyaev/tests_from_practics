package config_provider;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ConfigProvider {

    static Config readConfig(){
        return ConfigFactory.systemProperties().hasPath("testProfile")
                ? ConfigFactory.load(ConfigFactory.systemProperties().getString("testProfile"))
                : ConfigFactory.load("application.conf");
    }

    String URL = readConfig().getString("url");
    String DRIVER_URL = readConfig().getString("driverUrl");
    String URL_TO_DOWNLOAD_FILE = readConfig().getString("url_to_download_file");
    String PATH_TO_SAVE_FILE = readConfig().getString("path_to_save_file");
}
