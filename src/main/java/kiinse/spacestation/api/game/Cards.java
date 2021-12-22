package kiinse.spacestation.api.game;

import kiinse.spacestation.api.files.playercard.*;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

/**
 * Класс для генерации карточек игроков и их форматирования
 * @author kiinse
 * @since 0.0.1
 * @version 0.0.1
 */

@Slf4j
public class Cards {

    Disease disease = new Disease();
    Gender gender = new Gender();
    Item item = new Item();
    Name name = new Name();
    Phobia phobia = new Phobia();
    Profession profession = new Profession();
    Skill skill = new Skill();

    /**
     * Берёт случайные значения из файлов и объединяет их в один JSONObject.\n
     * Данные в json: name, age, gender, main-profession, additional-profession, skill, phobia, disease, item
     * @return JSONObject с данными для карточки
     */
    public JSONObject generatePlayerCardJson() {
        try {
            var card = new JSONObject();
            var nameInCard = name.getName();
            card.put("name", nameInCard);
            card.put("age", 15 + (int) (Math.random() * 50));
            card.put("gender", manOrWoman(gender.getGender(), nameInCard));
            card.put("main-profession", profession.getProfession());
            card.put("additional-profession", profession.getProfession());
            card.put("skill", skill.getSkill());
            var phobies = new StringBuilder();
            var phobiaRandom = 1 + (int) (Math.random() * 3);
            for (int i = 0; i < phobiaRandom; i++) {
                var phobie = phobia.getPhobia();
                var formattedPhobia = phobie.substring(0, 1).toUpperCase() + phobie.substring(1);
                if (!phobies.toString().contains(formattedPhobia)) {
                    phobies.append(formattedPhobia);
                    if (i+1 != phobiaRandom) {
                        phobies.append(", ");
                    }
                } else {
                    i--;
                }
            }
            card.put("phobia", phobies.toString());
            card.put("disease", disease.getDisease());
            var items = new StringBuilder();
            var itemRandom = (int) (Math.random() * 3);
            if (itemRandom == 0) {
                card.put("item", "Багаж пуст. Он был потерян по дороге.");
            } else {
                for (int i = 0; i < itemRandom; i++) {
                    var itemm = item.getItem();
                    if (!items.toString().contains(itemm)) {
                        items.append(item.getItem());
                        if (i+1 != itemRandom) {
                            items.append(", ");
                        }
                    } else {
                        i--;
                    }
                }
                card.put("item", items.toString());
            }

            return card;
        } catch (Exception e) {
            log.error("Произошла ошибка при генерации карточки игрока. Подробнее: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Метод, определяющий по имени пол персонажа
     * @param gender Пол персонажа из файла
     * @param name Имя персонажа из файла
     * @return Мужчина, Женщина или другой гендер
     */
    private String manOrWoman(String gender, String name) {
        if (gender.equals("Мужчина/Женщина")) {
            if (name.split(" ")[0].endsWith("а")) {
                return "Женщина";
            } else {
                return "Мужчина";
            }
        }
        return gender;
    }

    /**
     * Форматирует JSON карточку игрока в читаемый вид для отправки.
     * @param player Имя игрока
     * @param card Карточка JSONObject
     * @return Отформатированный текст
     */
    public String formatPlayerCard(String player, JSONObject card) {
        return "<b>▬▬▬▬ Карточка " + player + " ▬▬▬▬</b>\n" +
                " <b>→ Имя:</b> " + card.getString("name") + "\n" +
                " <b>→ Возраст:</b> " + card.getInt("age") + "\n" +
                " <b>→ Гендер:</b> " + card.getString("gender") + "\n" +
                " <b>→ Основная профессия:</b> " + card.getString("main-profession") + "\n" +
                " <b>→ Второстепенная профессия:</b> " + card.getString("additional-profession") + "\n" +
                " <b>→ Навыки:</b> " + card.getString("skill") + "\n" +
                " <b>→ Фобии:</b> " + card.getString("phobia") + "\n" +
                " <b>→ Болезнь:</b> " + card.getString("disease") + "\n" +
                " <b>→ Предметы:</b> " + card.getString("item") + "\n\n" +
                " <b>Ключ карточки:</b> " + card.hashCode();
    }

}
