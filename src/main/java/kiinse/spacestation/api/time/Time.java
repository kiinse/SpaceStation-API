package kiinse.spacestation.api.time;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**
 * Класс времени, для получения московской даты и различных обработок чего-то, что связано с временем
 *
 * @author kiinse
 * @since 0.0.1
 * @version 0.0.1
 */
public class Time {

    /**
     * Enum Рабочие Дни/Выходные
     */
    public enum Weekday {
        WEEKENDS,
        WORKINGDAYS
    }

    /**
     * Метод получения текущей даты
     *
     * @return Возвращает текущую Московскую дату
     */
    public Date getDate() {
        var nowUtc = Instant.now();
        var moscow = ZoneId.of("Europe/Moscow");
        return Date.from(ZonedDateTime.ofInstant(nowUtc, moscow).toInstant());
    }

    /**
     * Метод получения текущего календаря
     *
     * @return Возвращает текущий календарь Московской таймзоны
     */
    public Calendar getCalendar() {
        var cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
        return cal;
    }

    /**
     * Метод обработки дней недели. Если поступает Понедельник, то возвращает 0, если Вторник, то 1 и т.д.
     *
     * @param day День недели с большой буквы
     * @return Возвращает число
     */
    public int getWeekdayNumber(String day){
        return switch (day) {
            case "Понедельник" -> 0;
            case "Вторник" -> 1;
            case "Среда" -> 2;
            case "Четверг" -> 3;
            case "Пятница" -> 4;
            case "Суббота" -> 5;
            default -> 6;
        };
    }

    /**
     * Метод получения текущего дня недели.
     *
     * @return Возвращает название текущего дня недели с большой буквы.
     */
    public String getWeekday(){
        var now = getDate();
        var day = new SimpleDateFormat("EEEEE").format(now);
        if (isCyrillic(day)) {
            return day.substring(0, 1).toUpperCase() + day.substring(1);
        } else {
            return getRussian(day);
        }
    }

    /**
     * Метод получения завтрашнего дня недели.
     *
     * @return Возвращает название завтрашнего дня недели с большой буквы.
     */
    public String getNextWeekday(){
        var now = getDate();
        var cal = getCalendar();
        cal.setTime(now);
        cal.add(Calendar.DATE, 1);
        var date = cal.getTime();
        var day = new SimpleDateFormat("EEEEE").format(date);
        if (isCyrillic(day)) {
            return day.substring(0, 1).toUpperCase() + day.substring(1);
        } else {
            return getRussian(day);
        }
    }

    /**
     * Метод для перевода дня недели с английского на русский
     * @param day день недели на английском языке
     * @return Возвращает день недели на русском языке с большой буквы
     */
    public String getRussian(String day){
        return switch (day.toLowerCase()) {
            case "monday" -> "Понедельник";
            case "tuesday" -> "Вторник";
            case "wednesday" -> "Среда";
            case "thursday" -> "Четверг";
            case "friday" -> "Пятница";
            case "saturday" -> "Суббота";
            default -> "Воскресенье";
        };
    }

    /**
     * Метод проверки на наличие кириллицы в строке
     * @param s Входная строка
     * @return True если есть кириллица
     */
    public boolean isCyrillic(String s) {
        for (char a : s.toCharArray()) {
            if (Character.UnicodeBlock.of(a) == Character.UnicodeBlock.CYRILLIC) {
                return true;
            }
        }
        return false;
    }

    /**
     * Метод проверки на субботу.
     *
     * @param Day День недели
     * @return Возвращает Enum "Weekday.WEEKENDS" если суббота или "Weekday.WORKINGDAYS" если рабочий день.
     */
    public Weekday isWeekend(String Day) {
        var day = Day.toLowerCase();
        return day.equals("суббота") ? Weekday.WEEKENDS : Weekday.WORKINGDAYS;
    }

    /**
     * Метод проверки Числитель/Знаменатель
     *
     * @return Возвращает True если знаменатель
     */
    public boolean isNumSubject() {
        var cal = getCalendar();
        int num = cal.get(Calendar.WEEK_OF_MONTH);
        return getWeekday().equals("Воскресенье") != ((num % 2) == 0);
    }
}
