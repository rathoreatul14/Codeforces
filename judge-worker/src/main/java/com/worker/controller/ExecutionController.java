package com.worker.controller;

import com.worker.request.ExecutionRequest;
import com.worker.response.ExecutionResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.worker.service.ExecutionService;

@RestController
public class ExecutionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionController.class);
    private final ExecutionService service;

    @Autowired
    public ExecutionController(ExecutionService service) {
        this.service = service;
    }

    @PostMapping("execute")
    public ResponseEntity<ExecutionResponse> executeCode(@NonNull @RequestBody ExecutionRequest request) {

        LOGGER.info("Execution Request: {}", request);
        ExecutionResponse response = service.execute(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
