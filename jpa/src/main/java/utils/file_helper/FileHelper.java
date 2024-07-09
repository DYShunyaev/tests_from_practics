package utils.file_helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import utils.database.CommonSqlScript;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileHelper {

    public static String readFileAsString(String relativePathToResourceFile) {
        return readFileAsString(relativePathToResourceFile, UTF_8);
    }

    public static String readFileAsString(File file) {
        return readFileAsString(file,UTF_8);
    }

    public static String readFileAsString(String relativePathToResourceFile, Charset encoding) {
        relativePathToResourceFile = relativePathToResourceFile.replace("\\","/");
        String content;
        try {
            content = IOUtils.toString(getResourceFileStream(relativePathToResourceFile),encoding);
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка при получении файлов по относительному пути в ресурсах: '" + relativePathToResourceFile + "'",e);
        }
        return content;
    }

    public static String readFileAsString(File file, Charset encoding) {
        String result;
        try {
            result = FileUtils.readFileToString(file,encoding);
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка при чтении файла", e);
        }
        return result;
    }

    private static InputStream getResourceFileStream(String pathToResourceFile) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(pathToResourceFile);
    }

    public static String readXml(CommonSqlScript commonSqlScript) {
        String result = "";
        try {
            File file = new File(commonSqlScript.getPathToXml());
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            NodeList scriptList = document.getElementsByTagName("singleScript");

            for (int i = 0; i < scriptList.getLength(); i++) {
                Element scriptElement = (Element) scriptList.item(i);
                String scriptName = scriptElement.getAttribute("name");
                if (scriptName.equals(commonSqlScript.toString())) result = scriptElement.getTextContent().trim();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
