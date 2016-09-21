package codewizards.com.ua.confidenceintervals;

import java.util.ArrayList;
import java.util.List;

import codewizards.com.ua.confidenceintervals.logic.Element;
import codewizards.com.ua.confidenceintervals.logic.Interval;
import codewizards.com.ua.confidenceintervals.logic.Operation;

/**
 * Created by Интернет on 02.09.2016.
 */

public class DataContainer {
    private static DataContainer dataContainer = new DataContainer();
    private DataContainer() {}
    public static DataContainer getInstance() {
        return dataContainer;
    }

    private List<Element> elementList = new ArrayList<>();
    private List<Interval> lastIntervals;

    public void addElement(Element element) {
        elementList.add(element);
    }

    public void removeLast() {
        Element element = elementList.remove(elementList.size() - 1);
        if(element instanceof Interval) {
            Interval.decNextName();
        }
        lastIntervals = null;
    }

    public boolean isEmpty() {
        return elementList.isEmpty();
    }

    public List<Element> getElementList() {
        return elementList;
    }

//    public List<Interval> getIntervalList() {
//        List<Interval> intervals = new ArrayList<>();
//        for(Element element : elementList) {
//            if(element instanceof Interval) {
//                intervals.add((Interval) element);
//            }
//        }
//        intervals.add(lastResult);
////        intervals.add(new Interval(-10, 5));
////        intervals.add(new Interval(1, 5));
////        intervals.add(new Interval(-9, 10));
//        return intervals;
//    }

    public List<Interval> getLastIntervals() {
        return lastIntervals;
    }

    public void setLastIntervals(List<Interval> lastIntervals) {
        this.lastIntervals = lastIntervals;
    }
}
