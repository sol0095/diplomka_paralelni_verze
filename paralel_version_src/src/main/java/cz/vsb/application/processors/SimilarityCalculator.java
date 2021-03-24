package cz.vsb.application.processors;

import cz.vsb.application.selects.SelectWithSimilarity;
import java.util.HashSet;

public class SimilarityCalculator{

    private String query;
    private int id;
    private HashSet<Integer> inputHash;
    private HashSet<Integer> fileHash = new HashSet<>();

    public SimilarityCalculator(String line, HashSet<Integer> inputHash){
        this.inputHash = inputHash;
        String[] splittedLine = line.split("\\|sep\\|");
        String[] splittedPahts = splittedLine[2].split(",");

        for(int i = 0; i < splittedPahts.length; i++)
            fileHash.add(Integer.parseInt(splittedPahts[i]));

        this.query = splittedLine[1];
        this.id = Integer.parseInt(splittedLine[0]);
    }

    public SelectWithSimilarity calculate() {

        int intersection = 0;

        for(Integer s : inputHash){
            if(fileHash.contains(s))
                intersection++;
        }
        double totalSize = inputHash.size() + fileHash.size() - intersection;

        return new SelectWithSimilarity(query, (double)intersection / totalSize);
    }
}
