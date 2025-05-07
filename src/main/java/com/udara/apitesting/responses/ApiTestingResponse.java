package com.udara.apitesting.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ApiTestingResponse<T> {
    private static final String SUCCESS = "successful";
    private static final String UNSUCCESSFUL = "unsuccessful";

    private String status;

    @Setter
    private List<T> results;

    @Setter
    private String message;

    public ApiTestingResponse() {
        this.status = SUCCESS;
        this.results = new ArrayList<>();
        this.message = "Operation successful";
    }

    public ApiTestingResponse(boolean success, List<T> results, String message) {
        this.status = success ? SUCCESS : UNSUCCESSFUL;
        this.results = results;
        this.message = message;
    }

    public ApiTestingResponse(T data) {
        this();
        addData(data);
    }

    private void addData(T data) {
        if(data != null) {
            results.add(data);
        }
    }

}
