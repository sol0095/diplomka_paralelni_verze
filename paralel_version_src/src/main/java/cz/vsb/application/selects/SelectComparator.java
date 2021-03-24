package cz.vsb.application.selects;

import java.util.Comparator;

public class SelectComparator implements Comparator<SelectWithSimilarity>
{
    public int compare(SelectWithSimilarity o1, SelectWithSimilarity o2)
    {
        return Double.compare(o2.getSimilarity(), o1.getSimilarity());
    }
}

