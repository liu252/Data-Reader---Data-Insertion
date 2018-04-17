import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.sql.SQLException;

public class main
{
    public static void main (String args[]) throws IOException, SQLException
    {
        controller control = new controller();
        Iterable<CSVRecord> csvFile = control.ReadFile();
        Iterable<CSVRecord> csvFileRest = control.ReadRestofFile();
        control.connect();
        control.readAll(csvFile);
        control.readRest(csvFileRest);
        
    }
}
