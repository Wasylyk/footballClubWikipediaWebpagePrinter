package info.football.model;

public class WikiWebpage {

    private final String url;
    private final String snippet;

    public WikiWebpage(String url, String snippet) {
        this.url = url;
        this.snippet = snippet;
    }


    private boolean isProbablyAboutFootballClub() {

        return snippet.contains("football club");

    }

    public String getUrl() {
        return url;
    }
}
