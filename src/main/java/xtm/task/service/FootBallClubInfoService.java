package xtm.task.service;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FootBallClubInfoService {

    private HttpURLConnection wikiConnection;

    public String findLinkToClub(String word) {

        String searchLinksQueryUrl = "https://en.wikipedia.org/w/api.php?action=query&list=search&format=json&srsearch=%22"+ word + "%22&srlimit=10";

        Map<String, Map<String, ArrayList<Map>>> parsedResponseBody = parseJson(doGetRequest(searchLinksQueryUrl));
        List<Map> wikiEntriesFound = getWikiEntriesFound(parsedResponseBody);

        Map<String,Object> desiredWikiArticle = (Map<String,Object>)wikiEntriesFound.stream()
                .filter(wiki -> snippetMentionsFootballClub(wiki))
                .findFirst().get();
        Double pageId = (Double)desiredWikiArticle.get("pageid");

        String findWikiByIdQueryUrl = "https://en.wikipedia.org/w/api.php?action=query&prop=info&pageids=" + pageId.longValue() + "&inprop=url&format=json";
        Map<String, Map<String, Map<String, Map<String, Object>>>> desiredArticleInfo = parseJson(doGetRequest(findWikiByIdQueryUrl));
        return getDirectLink(desiredArticleInfo,Long.toString(pageId.longValue()));

    }

    private boolean snippetMentionsFootballClub(Map<String,Object> wiki){
        return ((String)wiki.get("snippet")).toLowerCase().contains("football club");
    }
    private ArrayList<Map> getWikiEntriesFound(Map<String, Map<String, ArrayList<Map>>> parsedResponseBody) {
        return  parsedResponseBody.get("query").get("search");
    }

    private Map parseJson(String responseBody) {
        return new Gson().fromJson(responseBody, Map.class);
    }

    private String getDirectLink(final Map<String, Map<String, Map<String,Map<String,Object>>>> parsedResponseBody, String id) {
       return (String) parsedResponseBody.get("query").get("pages").get(id).get("fullurl");

    }

    private String doGetRequest(final String adress) {

        String result = null;
        String apiAdress = adress;

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(apiAdress);
            urlConnection = (HttpURLConnection) url
                    .openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.setDoOutput(true);

            urlConnection.setReadTimeout(15 * 1000);
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            StringBuilder sb = new StringBuilder();

            int data = isw.read();
            while (data != -1) {
                char current = (char) data;
                data = isw.read();
                sb.append(current);
            }
            result = sb.toString();

        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return result;
    }


}