package org.jgameengine.nineteenfourtysomething.logic;

import java.io.Serializable;

public class HighScore implements Comparable, Serializable {

    private String name;

    private int score;

    public HighScore() {
    }

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int compareTo(Object o) {
        HighScore other = (HighScore) o;

        return new Integer(other.score).compareTo(score);
    }

    public String getScoreString() {
        return Integer.toString(score);
    }
}
