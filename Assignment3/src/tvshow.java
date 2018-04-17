public class tvshow
{
    private int tvid;
    private String tvTitle;
    private String tvGenre;
    private boolean ongoing;
    
    public int getTvid()
    {
        return tvid;
    }
    
    public void setTvid(int tvid)
    {
        this.tvid = tvid;
    }
    
    public String getTvTitle()
    {
        return tvTitle;
    }
    
    public void setTvTitle(String tvTitle)
    {
        this.tvTitle = tvTitle;
    }
    
    public String getTvGenre()
    {
        return tvGenre;
    }
    
    public void setTvGenre(String tvGenre)
    {
        this.tvGenre = tvGenre;
    }
    
    public boolean isOngoing()
    {
        return ongoing;
    }
    
    public void setOngoing(boolean ongoing)
    {
        this.ongoing = ongoing;
    }
}
