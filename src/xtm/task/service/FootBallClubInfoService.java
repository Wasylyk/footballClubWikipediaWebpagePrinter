package xtm.task.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FootBallClubInfoService {

    private HttpURLConnection wikiConnection;

    public List<String> searchLinks(String word) {

        String apiAdress = "https://en.wikipedia.org/w/api.php?action=query&generator=search&gsrsearch=\""+word+"\"&list=search&format=json&srsearch=\"Liverpool\"&prop=info&inprop=url&gsrprop=snippet&srlimit=10";
        List<String> result = new ArrayList<>();
        try {
            URL url = new URL(apiAdress);
            HttpURLConnection urlConnection = (HttpURLConnection) url
                    .openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.setDoOutput(true);

            urlConnection.setReadTimeout(15*1000);
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
            String responseBody = sb.toString();

            String regex = "\"fullurl\":\"(.[^\"]+)\"";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(responseBody);

            while(matcher.find()) {
                result.add(matcher.group(1));
            }

        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        return result;
    }
}