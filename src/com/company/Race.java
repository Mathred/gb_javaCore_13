package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class Race {
    private ArrayList<Stage> stages;
    private boolean winFlag;
    public ArrayList<Stage> getStages() { return stages; }
    public Race(boolean winFlag, Stage... stages) {
        this.stages = new ArrayList<>(Arrays.asList(stages));
        this.winFlag = winFlag;
    }

    public void setWinFlag(boolean winFlag) {
        this.winFlag = winFlag;
    }

    public boolean isWinFlag() {
        return winFlag;
    }
}