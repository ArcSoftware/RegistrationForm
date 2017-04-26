import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Jake on 4/26/17.
 */
public class SQL {
    public static void insertUser(Connection conn, User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("insert into users values(null,?,?,?)");
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getAddress());
        stmt.setString(3, user.getEmail());
        stmt.execute();
    }

    public static void updateUser(Connection conn, User user) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("update users set username = ?, address = ?, email = ? where id=?");
        stmt.setInt(4, user.getId());
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getAddress());
        stmt.setString(3, user.getEmail());
        stmt.execute();
    }

    public static ArrayList selectUser(Connection conn) throws SQLException {
        ArrayList allUsers = new ArrayList();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("select * from users");
        while(results.next()) {
            Integer id = results.getInt("id");
            String username = results.getString("username");
            String address = results.getString("address");
            String email = results.getString("email");
            allUsers.add(new User(id, username, address, email));
        }
        return allUsers;
    }
    public static void deleteUser(Connection conn, Integer id) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("delete users where id = ?");
        stmt.setInt(1, id);

        stmt.execute();
    }




}


