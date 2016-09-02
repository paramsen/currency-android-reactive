package com.amsen.par.cewlrency.persistence;

import java.util.List;

import rx.Observable;

/**
 * Just a repo interface
 * @author Pär Amsen 2016
 */
public interface Storage<T, U> {
    void put(T t);
    void put(List<T> t);
    Observable<T> get(U id);
    Observable<List<T>> getAll();
    void invalidate();
}
