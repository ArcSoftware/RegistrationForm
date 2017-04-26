import com.google.gson.Gson;
import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Created by Jake on 4/26/17.
 */
public class Main {
    public static Gson gson = new Gson();

    public static void main(String[] args) throws SQLException {
        Spark.staticFileLocation("/public");
        Server.createWebServer().start();
        //port(Integer.valueOf(System.getenv("PORT")));
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();

        stmt.execute("create table if not exists users (id identity, username varchar, address varchar, email varchar)");

        Spark.get(
                "/user",
                ((request, response) -> {
                    System.out.println(SQL.selectUser(conn).size());
                    return gson.toJson(SQL.selectUser(conn));
                })
        );
        Spark.post(
                "/user",
                ((request, response) -> {
                    String body = request.body();
                    User a = gson.fromJson(body, User.class);
//                    User.users.add(a);
                    SQL.insertUser(conn, a);
                    return "";
                })
        );
        Spark.put(
                "/user",
                ((request, response) -> {
                    String body = request.body();
                    User a = gson.fromJson(body, User.class);
                    SQL.updateUser(conn, a);
                    return "";
                })
        );


        Spark.delete(
                "/user/:id",
                ((request, response) -> {
                    String id = request.params("id");
                    SQL.deleteUser(conn, Integer.valueOf(id));

                    return "";
                })
        );


    }
}

