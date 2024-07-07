import config_provider.ConfigProvider;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specifications.Specifications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static io.restassured.RestAssured.given;

@DisplayName("Задание на скачивание файла")
public class FileDownloadTask {

    private final Path FILE_PATH = Paths.get(ConfigProvider.PATH_TO_SAVE_FILE);
    private final File DOWNLOAD_FILE = FILE_PATH.toFile();

    @Test
    @DisplayName("Проверка на скачивание файла с помощью RestAssured")
    public void testFromDownloadFile() throws IOException {
        Allure.step("Подготовка спецификаций, ожидаемый код - 200",() ->
                Specifications.installSpecification(
                        Specifications.requestSpecFromDownload(ConfigProvider.URL_TO_DOWNLOAD_FILE),Specifications.responseSpecOK200()));
        Allure.step("Отправка GET запроса, получение файла",() -> {
            InputStream response = given()
                    .when()
                    .get("/download/txt/sample-1.txt")
                    .getBody()
                    .asInputStream();
            Files.copy(response, FILE_PATH, StandardCopyOption.REPLACE_EXISTING);
            Assertions.assertTrue(DOWNLOAD_FILE.exists());
            Assertions.assertTrue(DOWNLOAD_FILE.length() > 0);
        });

    }

    @AfterEach
    public void deleteFile() {
        DOWNLOAD_FILE.delete();
    }
}
