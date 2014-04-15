package org.jgameengine.engine;

public interface LoadListener {

    void startedLoading();

    void finishedLoading();

    void percentLoaded(int percent);

}
