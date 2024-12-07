package com.worker.service;

import com.worker.request.ExecutionRequest;
import com.worker.response.ExecutionResponse;

public interface ExecutionService {

    ExecutionResponse execute(ExecutionRequest request);
}
