package com.worker.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.worker.enums.Language;
import lombok.Data;

@Data
public class ExecutionRequest {

    @JsonProperty(value = "source_code", required = true)
    private String sourceCode;

    @JsonProperty(value = "language", required = true)
    private Language language;

    @JsonProperty(value = "input", required = true)
    private String input;


}
