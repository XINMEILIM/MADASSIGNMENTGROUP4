package com.example.madassignment4.DailyWellnessModule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StepViewModel extends ViewModel {
    private final MutableLiveData<Integer> stepCount = new MutableLiveData<>(0);

    // Getter for LiveData
    public LiveData<Integer> getStepCount() {
        return stepCount;
    }

    // Method to update step count
    public void setStepCount(int steps) {
        stepCount.setValue(steps);
    }

    // Method to increment step count
    public void incrementStep() {
        Integer currentSteps = stepCount.getValue();
        if (currentSteps != null) {
            stepCount.setValue(currentSteps + 1);
        }
    }
}
