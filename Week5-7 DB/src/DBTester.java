import java.io.*;
import java.net.*;

public class DBTester
{
    final static char EOT = 4;

    public static void main(String args[])
    {
        try {
            BufferedReader commandLine = new BufferedReader(new InputStreamReader(System.in));
            Socket socket = new Socket("127.0.0.1", 8888);
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(true) handleNextCommand(commandLine, socketReader, socketWriter);
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    private static void handleNextCommand(BufferedReader commandLine, BufferedReader socketReader, BufferedWriter socketWriter)
    {
        try {
            System.out.print("SQL:> ");
            String command = commandLine.readLine();
            socketWriter.write(command + "\n");
            socketWriter.flush();
            String incomingMessage = socketReader.readLine();
            while( ! incomingMessage.contains("" + EOT + "")) {
                System.out.println(incomingMessage);
                incomingMessage = socketReader.readLine();
            }
        } catch(IOException ioe) {
            System.out.println(ioe);
        }
    }

    /*public static void testingScript()
    {
        ArrayList<String> commands = new ArrayList<>();
        /* Basic/core set of test queries
        commands.add("USE markbook;");
        commands.add("DROP TABLE marks;");
        commands.add("DROP DATABASE markbook;");
        commands.add("CREATE DATABASE markbook;");
        commands.add("USE markbook;");
        commands.add("CREATE TABLE marks (name, mark, pass);");
        commands.add("INSERT INTO marks VALUES ('Steve', 65, true);");
        commands.add("INSERT INTO marks VALUES ('Dave', 55, true);");
        commands.add("INSERT INTO marks VALUES ('Bob', 35, false);");
        commands.add("INSERT INTO marks VALUES ('Clive', 20, false);");
        commands.add("SELECT * FROM marks;");
        commands.add("SELECT * FROM marks WHERE name != 'Dave';");
        commands.add("SELECT * FROM marks WHERE (name == 'Dave') AND (pass == true);");
        commands.add("SELECT * FROM marks WHERE pass == true;");
        commands.add("UPDATE marks SET mark = 38 WHERE name == 'Clive';");
        commands.add("SELECT * FROM marks WHERE name == 'Clive';");
        commands.add("DELETE FROM marks WHERE name == 'Dave';");
        commands.add("SELECT * FROM marks;");
        commands.add("DELETE FROM marks WHERE mark < 40;");
        commands.add("SELECT * FROM marks;");
        /* More substantial sample database
        commands.add("USE imdb;");
        commands.add("DROP TABLE actors;");
        commands.add("DROP TABLE movies;");
        commands.add("DROP TABLE roles;");
        commands.add("DROP DATABASE imdb;");
        commands.add("CREATE DATABASE imdb;");
        commands.add("USE imdb;");
        commands.add("CREATE TABLE actors (name, nationality, awards);");
        commands.add("INSERT INTO actors VALUES ('Hugh Grant', 'British', 3);");
        commands.add("INSERT INTO actors VALUES ('Toni Collette', 'Australian', 12);");
        commands.add("INSERT INTO actors VALUES ('James Caan', 'American', 8);");
        commands.add("INSERT INTO actors VALUES ('Emma Thompson', 'British', 10);");
        commands.add("CREATE TABLE movies (name, genre);");
        commands.add("INSERT INTO movies VALUES ('Mickey Blue Eyes', 'Comedy');");
        commands.add("INSERT INTO movies VALUES ('About a Boy', 'Comedy');");
        commands.add("INSERT INTO movies VALUES ('Sense and Sensibility', 'Period Drama');");
        commands.add("SELECT id FROM movies WHERE name == 'Mickey Blue Eyes';");
        commands.add("SELECT id FROM movies WHERE name == 'About a Boy';");
        commands.add("SELECT id FROM movies WHERE name == 'Sense and Sensibility';");
        commands.add("SELECT id FROM actors WHERE name == 'Hugh Grant';");
        commands.add("SELECT id FROM actors WHERE name == 'Toni Collette';");
        commands.add("SELECT id FROM actors WHERE name == 'James Caan';");
        commands.add("SELECT id FROM actors WHERE name == 'Emma Thompson';");
        commands.add("CREATE TABLE roles (name, movieID, actorID);");
        commands.add("INSERT INTO roles VALUES ('Edward', 3, 1);");
        commands.add("INSERT INTO roles VALUES ('Frank', 1, 3);");
        commands.add("INSERT INTO roles VALUES ('Fiona', 2, 2);");
        commands.add("INSERT INTO roles VALUES ('Elinor', 3, 4);");
        /* Advanced test queries
        commands.add("SELECT * FROM actors WHERE awards < 5;");
        commands.add("ALTER TABLE actors ADD age;");
        commands.add("SELECT * FROM actors;");
        commands.add("UPDATE actors SET age = 45 WHERE name == 'Hugh Grant';");
        commands.add("SELECT * FROM actors WHERE name == 'Hugh Grant';");
        commands.add("SELECT nationality FROM actors WHERE name == 'Hugh Grant';");
        commands.add("ALTER TABLE actors DROP age;");
        commands.add("SELECT * FROM actors WHERE name == 'Hugh Grant';");
        commands.add("SELECT * FROM actors WHERE (awards > 5) AND (nationality == 'British');");
        commands.add("SELECT * FROM (awards > 5) AND ((nationality == 'British') OR (nationality == 'Australian'));");
        commands.add("SELECT * FROM actors WHERE name LIKE 'an';");
        commands.add("SELECT * FROM actors WHERE awards >= 10;");
        commands.add("DELETE FROM actors WHERE name == 'Hugh Grant';");
        commands.add("DELETE FROM actors WHERE name == 'James Caan';");
        commands.add("DELETE FROM actors WHERE name == 'Emma Thompson';");
        commands.add("DROP TABLE actors;");
        commands.add("SELECT * FROM actors;");
        commands.add("DROP DATABASE imdb;");
        commands.add("USE imdb;");
        /* Robustness testing queries (imdb database create again)
        commands.add("CREATE DATABASE imdb;");
        commands.add("USE imdb;");
        commands.add("CREATE TABLE actors (name, nationality, awards);");
        commands.add("INSERT INTO actors VALUES ('Hugh Grant', 'British', 3);");
        commands.add("INSERT INTO actors VALUES ('Toni Collette', 'Australian', 12);");
        commands.add("INSERT INTO actors VALUES ('James Caan', 'American', 8);");
        commands.add("INSERT INTO actors VALUES ('Emma Thompson', 'British', 10);");
        commands.add("CREATE TABLE movies (name, genre);");
        commands.add("INSERT INTO movies VALUES ('Mickey Blue Eyes', 'Comedy');");
        commands.add("INSERT INTO movies VALUES ('About a Boy', 'Comedy');");
        commands.add("INSERT INTO movies VALUES ('Sense and Sensibility', 'Period Drama');");
        commands.add("SELECT * FROM actors");
        commands.add("SELECT * FROM crew;");
        commands.add("SELECT spouse FROM actors;");
        commands.add("SELECT * FROM actors WHERE name == 'Hugh Grant;");
        commands.add("SELECT * FROM actors WHERE name > 10;");
    commands.add("SELECT name age FROM actors;");
    commands.add("SELECT * FROM actors awards > 10;");
    commands.add("SELECT * FROM actors WHERE name LIKE 10;");
    commands.add("    SELECT * FROM actors WHERE awards > 10;");
    commands.add("USE ebay;");

        for (String s:commands)
        {
            try {
                DBCommandHandler ch = new DBCommandHandler(s);
                System.out.println(s);
                System.out.println(ch.handleQuery());
            }
            catch (DBException dbe)
            {
                System.out.println(dbe.toString());
            }
            catch (IOException ioe)
            {
                System.out.println(ioe.toString());
            }
        }
    }*/
}