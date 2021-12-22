package kiinse.spacestation.api.files;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Класс создания файлов в папку SpaceStationFiles из resources внутри jar
 *
 * @author kiinse
 * @since 0.0.1
 * @version 0.0.1
 */
@Slf4j
public class FileManager {

    /**
     * Метод, который копирует файлы из resources внутри jar
     */
    public void export() {
        var disease = accessFile("disease.json");
        var gender = accessFile("gender.json");
        var item = accessFile("item.json");
        var name = accessFile("name.json");
        var phobia = accessFile("phobia.json");
        var profession = accessFile("profession.json");
        var skill = accessFile("skill.json");

        try {
            copyFile(disease, new File("SpaceStationFiles" + File.separator + "disease.json"));
            copyFile(gender, new File("SpaceStationFiles" + File.separator + "gender.json"));
            copyFile(item, new File("SpaceStationFiles" + File.separator + "item.json"));
            copyFile(name, new File("SpaceStationFiles" + File.separator + "name.json"));
            copyFile(phobia, new File("SpaceStationFiles" + File.separator + "phobia.json"));
            copyFile(profession, new File("SpaceStationFiles" + File.separator + "profession.json"));
            copyFile(skill, new File("SpaceStationFiles" + File.separator + "skill.json"));
        } catch (IOException e) {
            log.warn("Произошла ошибка при копировании файлов: {}", e.getMessage());
        }
    }

    /**
     * Метод для чтения файлов внутри jar
     * @param resource Имя нужного файла
     * @return Возвращает InputStream указанного файла
     */
    public static InputStream accessFile(String resource) {
        InputStream input = FileManager.class.getResourceAsStream(File.separator + "resources" + File.separator + resource);
        if (input == null) {
            input = FileManager.class.getClassLoader().getResourceAsStream(resource);
        }
        return input;
    }

    /**
     * Метод копирования файлов
     * @param sourceFile Входной файл
     * @param destFile Выходной файл
     * @throws IOException Ловит ошибки при невозможности получения доступа к файлам.
     */
    private static void copyFile(InputStream sourceFile, File destFile) throws IOException {
        if (destFile.exists()) {
            FileUtils.deleteQuietly(destFile);
        }
        FileUtils.copyInputStreamToFile(sourceFile, destFile);
    }
}
