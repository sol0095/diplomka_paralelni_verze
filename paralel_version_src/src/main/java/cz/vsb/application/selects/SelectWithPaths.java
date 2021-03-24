package cz.vsb.application.selects;

import java.util.ArrayList;

public class SelectWithPaths {

    private int id;
    private String query;
    private ArrayList<String> paths;

    public SelectWithPaths(int id, String query, ArrayList<String> paths) {
        this.id = id;
        this.query = query;
        this.paths = paths;
    }

    public String getQuery() {
        return query;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }

    public int getId(){
        return id;
    }
}
