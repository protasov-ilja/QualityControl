import java.util.ArrayList;
import java.net.URL;
import java.io.IOException;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

enum UrlState
{
    VALID,
    INVALID,
    OTHER
}

public class UrlParser {
    private URL _mainUrl;
    private ArrayList<UrlInfo> _allLinks = new ArrayList<>();
    private ArrayList<UrlInfo> _brokenLinks = new ArrayList<>();
    private Response _response = null;

    public UrlParser(String url) throws Exception {
        _mainUrl = new URL(url);
        UrlState state = GetUrlState(url);
        if (state == UrlState.VALID) {
            ParseValidLinks(url, state);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private UrlState GetUrlState(String str) {
        try {
            URL url = new URL(str);
            if (url.getHost().equals(_mainUrl.getHost())) {
                url.toURI();

                return UrlState.VALID;
            }

            return UrlState.OTHER;
        } catch (Exception er) {
            return UrlState.INVALID;
        }
    }

    private void ParseValidLinks(String strUrl, UrlState state) {
        try {
            UrlInfo url = new UrlInfo(strUrl, 0);
            if (!IsUrlAlreadyExists(url)) {
                _response = Jsoup.connect(strUrl).execute();
                Document doc = _response.parse();
                url.SetStatusCode(_response.statusCode());
                if (state != UrlState.OTHER)
                {
                    _allLinks.add(url);
                }

                Elements newLinks = doc.getElementsByTag("a");
                for (Element elem : newLinks) {
                    String absUrl = elem.absUrl("href");
                    UrlState newUrlState = GetUrlState(absUrl);
                    if (newUrlState == UrlState.VALID) {
                        ParseValidLinks(absUrl, newUrlState);
                    } else {
                        UrlInfo subUrl = new UrlInfo(elem.toString(), 0);
                        if ((!IsUrlAlreadyExists(subUrl)) && (newUrlState == UrlState.INVALID)) {
                            _allLinks.add(subUrl);
                            _brokenLinks.add(subUrl);
                        }
                    }
                }
            }
        } catch (HttpStatusException er) {
            UrlInfo url = new UrlInfo(strUrl, er.getStatusCode());
            if (!IsUrlAlreadyExists(url)) {
                _allLinks.add(url);
                _brokenLinks.add(url);
            }
        } catch (IOException er) {
            System.out.println(er.getMessage());
        }
    }

    private boolean IsUrlAlreadyExists(UrlInfo link) {
        for (UrlInfo urlInfo : _allLinks) {
            if (urlInfo.GetUrlString().equals(link.GetUrlString())) {
                return true;
            }
        }

        return false;
    }

    public void WriteResultsInFiles(String allLinksFileName, String brokenLinksFileName) {
        try {
            FileWriter allLinksFile = new FileWriter(allLinksFileName);
            FileWriter brokenLinksFile = new FileWriter(brokenLinksFileName);
            for (UrlInfo urlInfo : _allLinks) {
                allLinksFile.write(urlInfo.GetUrlString() + ' ' + urlInfo.GetStatusCode() + System.lineSeparator());
            }

            allLinksFile.close();

            for (UrlInfo urlInfo : _brokenLinks) {
                brokenLinksFile.write(urlInfo.GetUrlString() + ' ' + urlInfo.GetStatusCode() + System.lineSeparator());
            }

            brokenLinksFile.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
