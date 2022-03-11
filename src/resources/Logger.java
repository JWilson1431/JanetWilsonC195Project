package resources;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Logger {

    private static final String filename = "login_activity.txt";


    public static void recordLogin(String userName, boolean loginSuccessful) throws IOException {

        FileWriter fwriter = new FileWriter(filename, true);
        PrintWriter outputfile = new PrintWriter(fwriter);

        fwriter.append("Time: " + ZonedDateTime.now(ZoneOffset.UTC) + " UserName: " + userName + " sucessfully loggedin: " + loginSuccessful + "\n");

        fwriter.close();
    }
}
