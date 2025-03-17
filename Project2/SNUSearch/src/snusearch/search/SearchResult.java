package snusearch.search;

public class SearchResult {
    public String title;
    public String link;
    public SearchResult(String title, String link) {
        this.title = title;
        this.link = link;
    }

    @Override
    public String toString() {
        return "Title= " + title + "\nLink= " + link ;
    }
}