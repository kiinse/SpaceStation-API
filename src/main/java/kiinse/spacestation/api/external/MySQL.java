package kiinse.spacestation.api.external;

import lombok.extern.slf4j.Slf4j;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Класс подключения к БД MySQL
 *
 * @author kiinse
 * @since 0.0.1
 * @version 0.0.1
 */
@Slf4j
public class MySQL {

    /**
     * Свойство, хранящее в себе подключение в БД
     */
    private static Connection con;
    private static String dbName;

    /**
     * Метод для подключения MySQL
     *
     * @param host IP базы данных
     * @param port Порт базы данных
     * @param login Логин пользователя
     * @param password Пароль пользователя
     * @param dbName Имя создаваемой базы данных
     */
    public static void connect(String host, String port, String login, String password, String dbName) {
        if (!isConnected()) {
            try {
                Properties connInfo = new Properties();
                connInfo.setProperty("user",  login);
                connInfo.setProperty("password", password);
                connInfo.setProperty("useUnicode","true");
                connInfo.setProperty("characterEncoding","UTF-8");
                con = DriverManager.getConnection("jdbc:mysql://" + host  + ":" + port, connInfo);
                Statement s = con.createStatement();
                s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName + " CHARACTER SET utf8 COLLATE utf8_general_ci");
                s.executeUpdate("CREATE TABLE IF NOT EXISTS " + dbName + ".users (ID INT(255) PRIMARY KEY NOT NULL AUTO_INCREMENT, chat MEDIUMTEXT NOT NULL, login VARCHAR(255) NOT NULL DEFAULT 'НЕ УКАЗАНО', username VARCHAR(255) NOT NULL) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;");
                s.executeUpdate("set character_set_results=utf8");
                MySQL.dbName = dbName;
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("Не удалось подключиться к базе данных. Хост: {} | Порт: {} | Имя БД: {} | Логин: {} | Пароль: {} | Код ошибки: {}", host, port, dbName , login, password, e.getErrorCode());
            }
        }
    }

    /**
     * Метод для получения имени базы данных
     *
     * @return Возвращает имя БД
     */
    public static String getDataBaseName() {
        return MySQL.dbName;
    }

    /**
     * Метод для проверки соединения MySQL
     *
     * @return Возвращает True если con {@link java.sql.Connection} не является null
     */
    public static boolean isConnected() {
        return (con != null);
    }

    /**
     * Метод для получения соединения MySQL
     *
     * @return Возвращает con {@link java.sql.Connection}
     */
    public static Connection getConnection() {
        return con;
    }
}
