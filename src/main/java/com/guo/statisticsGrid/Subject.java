package com.guo.statisticsGrid;

/**
 * @Description
 * @author: Gxy
 * @Date: 2019/1/10
 */
public interface Subject {
    public void addObserver(Observer o);
    public void deleteObserver(Observer o);
    public void notifyObservers();
}
