import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfig
{
    
    public static Connection GetConnection()
    {
        Connection mySQLConnection = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionURL = "jdbc:mysql://localhost:3306/assignment3?useSSL=false";
            mySQLConnection = DriverManager.getConnection(connectionURL, "java", "javaProjects");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return mySQLConnection;
    }
}
