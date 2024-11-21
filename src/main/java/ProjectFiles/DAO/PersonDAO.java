package ProjectFiles.DAO;

import ProjectFiles.Models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private int PERSON_ID;
    private List<Person> people;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "12345678";

    private static Connection connection;//создание соединия с БД

    static {//все это для подключения к консоли! БД и работы с ней
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Person> all() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(SQL);//получение объекта из БД с данными о человеке(ключ-значение)
            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person id(int id) {
    Person person = null;
    try {
        // Подготавливаем запрос
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM person WHERE id = ?");
        preparedStatement.setInt(1, id);

        // Выполняем запрос
        ResultSet resultSet = preparedStatement.executeQuery();

        // Проверяем, есть ли данные
        if (resultSet.next()) {
            person = new Person(); // Создаем объект только если есть данные
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

    return person; // Вернем null, если данных нет
    }


    public void save(Person person) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO person (name, age, email) VALUES (?, ?, ?)");

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());

            preparedStatement.executeUpdate();// обновление бд
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(int id , Person person) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE Person SET name = ?, age = ?, email = ? WHERE id = ?");

            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3, person.getEmail());
            preparedStatement.setInt(4, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE id = ?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}