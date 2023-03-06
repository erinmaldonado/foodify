package com.example.foodify.Models;

import java.util.ArrayList;

public class AnalyzedInstruction {
    private String name;
    private ArrayList<Step> steps;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }
}
