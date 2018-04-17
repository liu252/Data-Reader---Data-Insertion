import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class controller
{
    private static Connection con;
    private menu m = new menu();
    private String filePath;
    public Iterable<CSVRecord> ReadFile() throws IOException
    {
        filePath = m.readFilePath();
        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        
        return records;
    }
    
    public Iterable<CSVRecord> ReadRestofFile() throws IOException
    {
        Reader in = new FileReader(filePath);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
        
        return records;
    }
    
    public void connect()
    {
        try
        {
            con = DBConfig.GetConnection();
            if (con.isClosed())
            {
                System.out.println("Connection was closed; creating new connection to MySQL.");
                con = DBConfig.GetConnection();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void readAll(Iterable<CSVRecord> records)
    {
        for(CSVRecord record : records)
        {
            user u = new user();
            u.setFirstName(record.get(0));
            u.setLastName(record.get(1));
            u.setAge(Integer.parseInt(record.get(2)));
            
            fillUsersTable(u);
            
            movie m = new movie();
            m.setMovieTitle(record.get(3));
            m.setMovieGenre(record.get(4));
            if(record.get(5).equals(""))
            {
                m.setMovieYear(0);
            }
            else
            {
                m.setMovieYear(Integer.parseInt(record.get(5)));
            }
            fillMoviesTable(m);
            
            tvshow t = new tvshow();
            t.setTvTitle(record.get(6));
            t.setTvGenre(record.get(7));
            t.setOngoing(Boolean.parseBoolean(record.get(8)));
            
            fillTVTable(t);
            
        }
    }
    
    public void readRest(Iterable<CSVRecord> records)
    {
        for(CSVRecord record : records)
        {
            String userMovieFirstName = record.get(9);
            String userMovieLastName = record.get(10);
            String movie = record.get(11);
        
            int userMovieID = findUserID(userMovieFirstName, userMovieLastName);
            int movieID = findMovieID(movie);
        
            fillMovieTrackingTable(userMovieID, movieID);
        
            String userTVFirstName = record.get(12);
            String userTVLastName = record.get(13);
            String tv = record.get(14);
        
            int userTVID = findUserID(userTVFirstName, userTVLastName);
            int tvID = findTVID(tv);
        
            fillTVTrackingTable(userTVID, tvID);
        }
    }
    
    private void fillTVTrackingTable(int userTVID, int tvID)
    {
        PreparedStatement ps = null;
        try
        {
            ps = con.prepareStatement("INSERT INTO TVShowTracking(UserID, TVShowID) VALUES(?,?)");
            ps.clearParameters();
            ps.setInt(1, userTVID);
            ps.setInt(2, tvID);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    private int findTVID(String tv)
    {
        PreparedStatement ps = null;
        int movieID = 0;
        try
        {
            ps = con.prepareStatement("SELECT TVID FROM TVShows WHERE  Title = ?");
            ps.clearParameters();
            ps.setString(1,tv);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                movieID = rs.getInt(1);
            }
        
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return movieID;
    }
    
    private void fillMovieTrackingTable(int userMovieID, int movieID)
    {
        PreparedStatement ps = null;
        try
        {
            ps = con.prepareStatement("INSERT INTO MovieTracking(UserID, MovieID) VALUES(?,?)");
            ps.clearParameters();
            ps.setInt(1, userMovieID);
            ps.setInt(2, movieID);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    private int findMovieID(String movie)
    {
        PreparedStatement ps = null;
        int movieID = 0;
        try
        {
            ps = con.prepareStatement("SELECT MovieID FROM movies WHERE  Title = ?");
            ps.clearParameters();
            ps.setString(1,movie);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                movieID = rs.getInt(1);
            }
        
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return movieID;
    }
    
    private int findUserID(String userFirstName, String userLastName)
    {
        PreparedStatement ps = null;
        int userID = 0;
        try
        {
            ps = con.prepareStatement("SELECT UserID FROM User WHERE FirstName = ? AND LastName = ?");
            ps.clearParameters();
            ps.setString(1,userFirstName);
            ps.setString(2,userLastName);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                userID = rs.getInt(1);
            }
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return userID;
    }
    
    private void fillTVTable(tvshow t)
    {
        
        PreparedStatement ps = null;
        try
        {
            ps = con.prepareStatement("INSERT INTO TVShows(Title, Genre, OnGoing) VALUES(?,?,?)");
            ps.clearParameters();
            ps.setString(1, t.getTvTitle());
            ps.setString(2, t.getTvGenre());
            ps.setBoolean(3, t.isOngoing());
            ps.executeUpdate();
    
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    private void fillMoviesTable(movie m)
    {
        PreparedStatement ps = null;
        try
        {
            ps = con.prepareStatement("INSERT INTO Movies(Title, Genre, Year) VALUES(?,?,?)");
            ps.clearParameters();
            ps.setString(1, m.getMovieTitle());
            ps.setString(2, m.getMovieGenre());
            ps.setInt(3, m.getMovieYear());
            ps.executeUpdate();
    
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        
    }
    
    public void fillUsersTable(user u)
    {
    
        PreparedStatement ps = null;
        try
        {
            ps = con.prepareStatement("INSERT INTO User(FirstName, LastName, Age) VALUES(?,?,?)");
            ps.clearParameters();
            ps.setString(1, u.getFirstName());
            ps.setString(2, u.getLastName());
            ps.setInt(3, u.getAge());
            ps.executeUpdate();
    
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
