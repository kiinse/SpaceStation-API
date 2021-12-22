package kiinse.spacestation.api.files.playercard;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Класс взятия текста из файла SpaceStationFiles/skill.json
 * @author kiinse
 * @since 0.0.1
 * @version 0.0.1
 */
@Slf4j
public class Skill {

    private final JSONParser parser = new JSONParser();

    /**
     * Метод взятия случайного текста из файла
     * @return Взятый текст
     */
    public String getSkill() {
        try {
            var jsonFile = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("SpaceStationFiles/skill.json"), StandardCharsets.UTF_8));
            int random = 1 + (int) (Math.random() * jsonFile.size());
            return jsonFile.get(String.valueOf(random)).toString();
        } catch (IOException | ParseException e) {
            log.error("Произошла ошибка при взятии навыков из файла. Подробнее: {}", e.getMessage());
        }
        return "null";
    }

}
