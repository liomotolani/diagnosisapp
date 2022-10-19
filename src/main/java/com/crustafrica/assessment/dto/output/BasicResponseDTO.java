package com.crustafrica.assessment.dto.output;

import com.crustafrica.assessment.dto.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BasicResponseDTO extends StandardResponseDTO{

    @JsonProperty
    private Object data;

    public BasicResponseDTO() {
    }

    public BasicResponseDTO(Status status) {
        super(status);
    }

    public BasicResponseDTO(Status status, Object data) {
        super(status);
        this.data = data;
    }


    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
