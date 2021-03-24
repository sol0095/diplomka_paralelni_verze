package cz.vsb.application.processors;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;

public class XmlTreeView {
    private static String[] searchedWords = {
            "JOIN", "GROUP BY", "ORDER BY", "MAX", "MIN", "SUM", "COUNT", "ASC", "AVG", "DESC", "AND", "OR", "=", "<=", ">=", "!=", ">", "<", "<>", "NULL", "IN",
            "LIKE", "ANY", "ALL", "BETWEEN", "EXISTS", "SOME", "REGEXP", "CONCAT", "CONCAT_WS", "LEFT", "LEN", "LOWER", "REPLACE", "REVERSE", "RIGHT", "STR", "SUBSTRING",
            "UPPER", "POWER", "SQRT", "EXP", "ROUND", "RAND", "DATEADD", "DATEDIFF", "DATEPART", "DAY", "GETDATE", "MONTH", "YEAR", "CAST", "ISNULL", "COALESCE",
            "DATALENGTH", "CHARINDEX", "DIFFERENCE", "FORMAT", "LTRIM", "NCHAR", "PATINDEX", "QUOTENAME", "REPLICATE", "RTRIM", "SOUNDEX", "SPACE", "STUFF",
            "TRANSLATE", "TRIM", "UNICODE", "ABS", "SQUARE", "RADIANS", "PI", "LOG10", "LOG", "FLOOR", "DEGREES", "CEILING", "CURRENT_TIMESTAMP", "DATEFROMPARTS",
            "DATENAME", "GETUTCDATE", "ISDATE", "SYSDATETIME", "SYSDATE", "SYSTIMESTAMP", "CONVERT", "IIF", "ISNUMERIC", "NULLIF", "USER_NAME", "SESSIONPROPERTY", "FROM_UNIXTIME",
            "HEX", "STRFTIME", "DATE", "IFNULL", "INSTR", "TIME", "STR_TO_DATE", "INITCAP", "ASCII", "LPAD", "RPAD", "SUBSTR", "REGEXP_REPLACE", "REGEXP_SUBSTR",
            "REGEXP_INSTR", "CURRENT_DATE", "EXTRACT", "MONTHS_BETWEEN", "TO_TIMESTAMP", "TO_CHAR", "TRUNC", "UNISTR", "TO_NUMBER", "TO_NCHAR", "TO_DATE", "PRINTF"
    };

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
