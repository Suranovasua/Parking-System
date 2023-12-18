package com.example.demo18;

public class CarModel {
    private final String modelId;
    private final String modelName;

    public CarModel(String modelId, String modelName) {
        this.modelId = modelId;
        this.modelName = modelName;
    }

    public String getModelId() {
        return modelId;
    }

    public String getModelName() {
        return modelName;
    }

    @Override
    public String toString() {
        return modelName;
    }
}
