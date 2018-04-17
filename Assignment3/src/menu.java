import java.io.*;

public class menu
{
    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public String readFilePath() throws IOException
    {
        System.out.println("Welcome to the TV Show and Movie Tracking Database!");
        System.out.print("Please enter the path to the CSV file: ");
        
        String filePath = br.readLine();
        while(!fileExists(filePath))
        {
            System.out.println("File Does Not Exist");
            System.out.print("Please enter new file path: ");
            filePath = br.readLine();
        }
        return filePath;
        
    }
    private Boolean fileExists(String filePath)
    {
        File f = new File(filePath);
        if (f.exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
