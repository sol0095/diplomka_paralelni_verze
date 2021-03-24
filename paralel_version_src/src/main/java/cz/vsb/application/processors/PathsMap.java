package cz.vsb.application.processors;

import java.util.HashMap;

public class PathsMap {
    public HashMap<String, Integer> pathsWithNums;
    public int pathNum;

    public PathsMap(){
        this.pathsWithNums = new HashMap<>();
        this.pathNum = 1;
    }
}
