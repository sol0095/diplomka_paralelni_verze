package cz.vsb.application;

import cz.vsb.application.files.InputFileReader;
import cz.vsb.application.files.PathFileWriter;
import cz.vsb.application.files.PropertyLoader;
import cz.vsb.application.processors.InputPreparator;
import cz.vsb.application.processors.PathGenerator;
import cz.vsb.application.processors.PathsMap;
import cz.vsb.application.processors.SimilarityCalculator;
import cz.vsb.application.selects.SelectComparator;
import cz.vsb.application.selects.SelectWithSimilarity;
import java.util.*;
import java.util.stream.Stream;

public class Application{

    public static void runApplication(){

        char grammar = PropertyLoader.loadProperty("grammar").charAt(0);
        String queryStmt = getStmtName(grammar);

        if(Boolean.parseBoolean(PropertyLoader.loadProperty("generatePathsFile")))
            writePathsTofile(queryStmt);
        calculateSimilarity(grammar, queryStmt);
    }

    private static void writePathsTofile(String queryStmt){
        long start = System.currentTimeMillis();

        PathsMap pathsMap = new PathsMap();
        Stream<String> lines = InputFileReader.readFile(PropertyLoader.loadProperty("inputXmlFile"));

        PathFileWriter.startWriting(PropertyLoader.loadProperty("selectsFile"));
        lines.forEach(e ->{
            PathGenerator.generate(e, pathsMap, queryStmt);
        });
        PathFileWriter.stopWriting();

        PathFileWriter.startWriting(PropertyLoader.loadProperty("pathToIdFile"));
        pathsMap.pathsWithNums.forEach((k,v)->{
            PathFileWriter.write(k + "__" + v + "\n");
        });
        PathFileWriter.stopWriting();

        long finish = System.currentTimeMillis();
        System.out.println("Writing time: " + (finish-start) + "ms");
    }

    private static void calculateSimilarity(char grammar, String quieryStmt){
        long start = System.currentTimeMillis();
        InputPreparator.prepareInput(PropertyLoader.loadProperty("inputQuery"), grammar, quieryStmt);
        long finish = System.currentTimeMillis();
        System.out.println("Prepare time: " + (finish-start) + "ms");

        List<SelectWithSimilarity> resultList = Collections.synchronizedList(new ArrayList<>());

        start = System.currentTimeMillis();
        Stream<String> lines = InputFileReader.readFile(PropertyLoader.loadProperty("selectsFile"));
        finish = System.currentTimeMillis();
        System.out.println("Reading file time: " + (finish-start) + "ms");

        start = System.currentTimeMillis();
        lines.forEach(e ->{
            SelectWithSimilarity selectWithSimilarity = new SimilarityCalculator(e, InputPreparator.getInputPaths()).calculate();
            resultList.add(selectWithSimilarity);
        });
        finish = System.currentTimeMillis();
        System.out.println("Computing time: " + (finish-start) + "ms");


        start = System.currentTimeMillis();
        Collections.sort(resultList, new SelectComparator());

        finish = System.currentTimeMillis();
        System.out.println("Sorting time: " + (finish-start) + "ms");

        for(int i = 0; i < 20; i++){
            System.out.println(resultList.get(i).getQuery());
            System.out.println(resultList.get(i).getSimilarity());
        }
    }

    private static String getStmtName(char grammar){
        String queryStmt = null;
        switch(grammar) {
            case '0':   //mysql
                queryStmt = "root";
                break;
            case '1':   //sqlite
                queryStmt = "parse";
                break;
            case '2':   //tsql
                queryStmt = "tsql_file";
                break;
            case '3':   //plsql
                queryStmt = "sql_script";
                break;
        }
        return queryStmt;
    }
}
