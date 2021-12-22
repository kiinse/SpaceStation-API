package kiinse.spacestation.api.external;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Класс получения данных из Базы Данных MySQL
 *
 * @author kiinse
 * @since 0.0.1
 * @version 0.0.1
 */
@Slf4j
public class MySQLUtils {

    /**
     * Данный метод вносит в БД информацию о пользователе при первом запуске
     *
     * @param chat String значение ID чата пользователя
     * @param username Username пользователя
     */
    public static void addUser(String chat, String username) {
        try {
            var ps = Objects.requireNonNull(MySQL.getConnection()).prepareStatement("INSERT INTO " + MySQL.getDataBaseName() + ".users(chat, username) VALUES (?,?)");
            ps.setString(1, chat);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка addUser. Код ошибки: {}", e.getErrorCode());
        }
    }

    /**
     * Метод проверки пользователя на наличие в БД по username
     *
     * @param login Username пользователя
     * @return Возвращает True если такой пользователь существует в БД
     */
    public static Boolean hasUserByLogin(String login) {
        try {
            var ps = Objects.requireNonNull(MySQL.getConnection()).prepareStatement("SELECT * FROM " + MySQL.getDataBaseName() + ".users WHERE username = ?");
            ps.setString(1, login);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка hasUserByLogin. Код ошибки: {}", e.getErrorCode());
        }
        return false;
    }

    /**
     * Метод проверки пользователя на наличие в БД по ID чата
     *
     * @param chat String значение ID чата пользователя
     * @return Возвращает True если такой пользователь существует в БД
     */
    public static Boolean hasUserByChat(String chat) {
        try {
            var ps = Objects.requireNonNull(MySQL.getConnection()).prepareStatement("SELECT * FROM " + MySQL.getDataBaseName() + ".users WHERE chat = ?");
            ps.setString(1, chat);
            var rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            log.warn("Произошла ошибка hasUserByChat. Код ошибки: {}", e.getErrorCode());
        }
        return false;
    }

    /**
     * Метод получения значения "login" у указанного Чата в БД
     *
     * @param chat String значение ID чата пользователя
     * @return Возвращает значение строки "UserName"
     */
    public static String getLogin(String chat) {
        try {
            var ps = Objects.requireNonNull(MySQL.getConnection()).prepareStatement("SELECT login FROM " + MySQL.getDataBaseName() + ".users WHERE chat = ?");
            ps.setString(1, chat);
            var rs = ps.executeQuery();
            return rs.next() ? rs.getString("login") : null;
        } catch (SQLException e) {
            log.warn("Произошла ошибка getLogin. Код ошибки: {}", e.getErrorCode());
        }
        return null;
    }

    /**
     * Метод получения значения "username" у указанного Чата в БД
     *
     * @param chat String значение ID чата пользователя
     * @return Возвращает значение строки "UserName"
     */
    public static String getUsername(String chat) {
        try {
            var ps = Objects.requireNonNull(MySQL.getConnection()).prepareStatement("SELECT username FROM " + MySQL.getDataBaseName() + ".users WHERE chat = ?");
            ps.setString(1, chat);
            var rs = ps.executeQuery();
            return rs.next() ? rs.getString("username") : null;
        } catch (SQLException e) {
            log.warn("Произошла ошибка getUsername. Код ошибки: {}", e.getErrorCode());
        }
        return null;
    }

    /**
     * Метод для обновления username у указанного пользователя в БД
     *
     * @param chat String значение ID чата пользователя
     * @param username Новое значения колонки "UserName"
     */
    public static void updateUserName(String chat, String username) {
        try {
            var ps = Objects.requireNonNull(MySQL.getConnection()).prepareStatement("UPDATE " + MySQL.getDataBaseName() + ".users SET username = ? WHERE chat = ?");
            ps.setString(1, username);
            ps.setString(2,  chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка updateUserName. Код ошибки: {}", e.getErrorCode());
        }
    }

    /**
     * Метод для обновления login у указанного пользователя в БД
     *
     * @param chat String значение ID чата пользователя
     * @param login Новое значения колонки "UserName"
     */
    public static void updateLogin(String chat, String login) {
        try {
            var ps = Objects.requireNonNull(MySQL.getConnection()).prepareStatement("UPDATE " + MySQL.getDataBaseName() + ".users SET login = ? WHERE chat = ?");
            ps.setString(1, login);
            ps.setString(2,  chat);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.warn("Произошла ошибка updateLogin. Код ошибки: {}", e.getErrorCode());
        }
    }

}
