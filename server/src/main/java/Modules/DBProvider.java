package Modules;

import CollectionObject.*;
import Network.User;

import java.sql.*;
import java.util.Date;

public class DBProvider{
    private static Connection connection;

    public static void establishConnection(String url, String user, String password){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkUserExistence(String username){

        String query = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ?)";

        try (PreparedStatement p = connection.prepareStatement(query)){

            p.setString(1, username);
            ResultSet res = p.executeQuery();
            if (res.next()){
                return res.getBoolean(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean checkUserPassword(User user){
        var username = user.getUsername();
        var hashedPassword = user.getPassword();

        String query = "SELECT hashedpassword FROM users WHERE username = ?";

        try (PreparedStatement p = connection.prepareStatement(query)){

            p.setString(1, username);
            ResultSet res = p.executeQuery();

            if (res.next()){
                String storedHashedPassword = res.getString("hashedpassword");
                return storedHashedPassword.equals(hashedPassword);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static void addUser(User user){
        var username = user.getUsername();
        var hashedPassword = user.getPassword();

        String query = "INSERT INTO users (username, hashedpassword) VALUES (?, ?)";

        try (PreparedStatement p = connection.prepareStatement(query)){

            p.setString(1, username);
            p.setString(2, hashedPassword);
            p.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void load() {
        CollectionService.elementsCount = loadElCount();

        String query = "SELECT cities.id, cities.name, cities.x, cities.y, cities.creationdate, cities.area," +
                "cities.population, cities.metersabovesealevel, citites.telephonecode, cities.agglomeration, cities.standardofliving, cities.governor, cities.vehicletype, users.username FROM cities JOIN users ON users.id = cities.creatorid";

        try (PreparedStatement p = connection.prepareStatement(query)){
            ResultSet res = p.executeQuery();

            while (res.next()){
                try {
                    var element = new City(
                            res.getLong(1),
                            res.getString(2),
                            new Coordinates(res.getFloat(3), res.getDouble(4)),
                            res.getDate(5),
                            res.getInt(6),
                            res.getInt(7),
                            res.getFloat(8),
                            res.getLong(9),
                            res.getLong(10),
                            StandardOfLiving.valueOf(res.getString(11)),
                            new Human(res.getString(12)),
                            res.getString(13)
                    );
                    if (checkUserExistence(element.getCreator())){
                        CollectionService.collection.add(element);
                    }

                } catch (IllegalArgumentException e){
                    Server.logger.error("Повреждённый атрибут type у элемента с id " + res.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean addCity(CityModel cityModel){

        String query = "INSERT INTO cities (name, x, y, creationDate, area, population, metersabovesealevel, telephonecode, agglomeration, standardofliving, governor, creatorid)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT id FROM users WHERE username = ?))";

        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString(1, cityModel.getName());
            p.setFloat(2, cityModel.getCoordinates().getX());
            p.setDouble(3, cityModel.getCoordinates().getY());

            long dateInMilliseconds = new Date().getTime();
            p.setTimestamp(4, new Timestamp(dateInMilliseconds));

            p.setInt(5, cityModel.getArea());
            p.setInt(6, cityModel.getPopulation());
            p.setFloat(7, cityModel.getMetersAboveSeaLevel());
            p.setLong(8,cityModel.getTelephoneCode());
            p.setLong(9,cityModel.getAgglomeration());
            p.setString(10, String.valueOf(cityModel.getStandardOfLiving()));
            p.setString(11, cityModel.getGovernor().getName());
            p.setString(12, cityModel.getUser().getUsername());

            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCity(User user, long id, CityModel cityModel){

        String query = "UPDATE cities SET name = ?, x = ?, y = ?, area = ?, population = ?," +
                " metersabovesealevel = ?, telephonecode = ?, agglomeration = ?, standardofliving = ?, governor = ? WHERE (id = ? AND creatorid IN (SELECT id FROM users WHERE username = ?))";

        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString(1, cityModel.getName());
            p.setFloat(2, cityModel.getCoordinates().getX());
            p.setDouble(3, cityModel.getCoordinates().getY());
            p.setInt(4, cityModel.getArea());
            p.setInt(5, cityModel.getPopulation());
            p.setFloat(6, cityModel.getMetersAboveSeaLevel());
            p.setLong(7, cityModel.getTelephoneCode());
            p.setLong(8, cityModel.getAgglomeration());
            p.setString(9, String.valueOf(cityModel.getStandardOfLiving()));
            p.setString(10, cityModel.getGovernor().getName());
            p.setInt(11, (int) id);
            p.setString(12, user.getUsername());

            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeCityById(long id){

        String query = "DELETE FROM cities WHERE id = ?";

        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setLong(1, id);
            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeCitiesLowerThanId(User user, long id){

        String query = "DELETE FROM cities WHERE (id < ? AND creatorid IN (SELECT id FROM users WHERE username = ?))";

        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setLong(1, id);
            p.setString(2, user.getUsername());
            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeCitiesByType(User user, StandardOfLiving type){

        String query = "DELETE FROM cities WHERE (vehicletype = ? AND creatorid IN (SELECT id FROM users WHERE username = ?))";

        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString(1, type.getType());
            p.setString(2, user.getUsername());
            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean clearCities(User user){

        String query = "DELETE FROM cities WHERE creatorid IN (SELECT id FROM users WHERE username = ?)";

        try (PreparedStatement p = connection.prepareStatement(query)){
            p.setString(1, user.getUsername());
            p.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long loadElCount(){
        String query = " select last_value from cities_id_seq";

        try (PreparedStatement p = connection.prepareStatement(query)){
            ResultSet res = p.executeQuery();

            if (res.next()){
                return res.getLong(1);
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

}
