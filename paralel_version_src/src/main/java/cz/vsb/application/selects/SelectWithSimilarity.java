package cz.vsb.application.selects;

public class SelectWithSimilarity {

    private String query;
    private double similarity;

    public SelectWithSimilarity(String query, double similarity) {
        this.similarity = similarity;
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public double getSimilarity() {
        return similarity;
    }
}
