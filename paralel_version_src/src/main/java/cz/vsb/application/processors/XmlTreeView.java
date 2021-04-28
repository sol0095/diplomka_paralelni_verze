package cz.vsb.application.processors;

import cz.vsb.application.files.PropertyLoader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class XmlTreeView {
    private static String[] searchedWords = PropertyLoader.loadProperty("functionsForSimilarity").split(", ");

    public static void getLeafPaths(Element elem, StringBuilder path, ArrayList<String> pathsInTree) {
        final int pathLen = path.length();
        if (pathLen != 0)
            path.append("->");
        path.append(elem.getTagName());

        if(elem.getTagName().equals("specialWord")){
            String str = elem.getTextContent();
            if(str != null && checkSearchedWords(str))
                path.append(str);
        }

        boolean hasChild = false;
        for (Node child = elem.getFirstChild(); child != null; child = child.getNextSibling())
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                hasChild = true;
                getLeafPaths((Element)child, path, pathsInTree);
            }
        if (! hasChild)
            pathsInTree.add(path.toString());
        path.setLength(pathLen);
    }

    private static boolean checkSearchedWords(String str){
        for(String s : searchedWords){
            if(str.contains(s))
                return true;
        }
        return false;
    }
}
