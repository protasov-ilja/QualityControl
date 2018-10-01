
public class UrlInfo {
    private int _statusCode;
    private String _urlString;

    public UrlInfo(String urlString, int statusCode)
    {
        _urlString = urlString;
        _statusCode = statusCode;
    }

    public int GetStatusCode()
    {
        return _statusCode;
    }

    public String GetUrlString()
    {
        return _urlString;
    }
    public void SetStatusCode(int code)
    {
        _statusCode = code;
    }
}
