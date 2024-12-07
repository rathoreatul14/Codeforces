package com.worker.service;

import com.worker.request.ExecutionRequest;
import com.worker.response.ExecutionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static com.worker.constants.constants.CODE_FILE_PATH;
import static com.worker.constants.constants.DOCKER_FILE_PATH;
import static com.worker.constants.constants.INPUT_FILE_PATH;
import static com.worker.constants.constants.OUTPUT_FILE_PATH;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    @Override
    public ExecutionResponse execute(ExecutionRequest request) {

        String uuid = String.valueOf(UUID.randomUUID());

        createInputFile(request, uuid);
        createCodeFile(request, uuid);
        createOutputFile(uuid);

        buildDockerImage(uuid);

        ExecutionResponse executionResponse = getExecutionResponse(uuid);
        deleteTempFiles(request, uuid);
        return executionResponse;
    }

    private void createInputFile(ExecutionRequest request, String uuid) {

        Path inputFilePath = Path.of(INPUT_FILE_PATH + uuid + ".txt").toAbsolutePath();
        try {
            Files.writeString(inputFilePath, request.getInput());
        } catch (Exception exception) {
            throw new RuntimeException("Error while writing to input file.", exception);
        }
    }

    private void createCodeFile(ExecutionRequest request, String uuid) {

        String codeFileExtension = request.getLanguage().getValue();
        Path codeFilePath = Path.of(CODE_FILE_PATH + uuid + "." + codeFileExtension).toAbsolutePath();
        try {
            Files.writeString(codeFilePath, request.getSourceCode());
        } catch (Exception exception) {
            throw new RuntimeException("Error while writing to code file.", exception);
        }
    }

    private void createOutputFile(String uuid) {

        Path outputFilePath = Path.of(OUTPUT_FILE_PATH + uuid + ".txt").toAbsolutePath();
        try {
            Files.createFile(outputFilePath);
        } catch (Exception exception) {
            throw new RuntimeException("Error while creating output file.", exception);
        }
    }

    private void buildDockerImage(String uuid) {

        // Builder to build a command and run it by .start().
        // Wait for command to execute, if terminated abruptly, throw exception
        ProcessBuilder imageBuilder = new ProcessBuilder("docker build -t image" + uuid, DOCKER_FILE_PATH);
        imageBuilder.redirectErrorStream(true);
        try {
            Process buildProcess = imageBuilder.start();
            int buildExitCode = buildProcess.waitFor();
            if (buildExitCode != 0) {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to build Docker image.", e);
        }
    }

    private ExecutionResponse getExecutionResponse(String uuid) {

        ExecutionResponse response = new ExecutionResponse();
        Path outputFilePath = Path.of(OUTPUT_FILE_PATH + uuid + ".txt").toAbsolutePath();
        try {
            response.setOutput(Files.readString(outputFilePath));
        } catch (Exception exception) {
            throw new RuntimeException("Error while reading output file.", exception);
        }
        return response;
    }

    private void deleteTempFiles(ExecutionRequest request, String uuid) {

        String codeFileExtension = request.getLanguage().getValue();
        Path inputFilePath = Path.of(INPUT_FILE_PATH + uuid + ".txt").toAbsolutePath();
        Path codeFilePath = Path.of(CODE_FILE_PATH + uuid + "." + codeFileExtension).toAbsolutePath();
        Path outputFilePath = Path.of(OUTPUT_FILE_PATH + uuid + ".txt").toAbsolutePath();
        try {
            Files.delete(inputFilePath);
            Files.delete(codeFilePath);
            Files.delete(outputFilePath);
        } catch (Exception exception) {
            throw new RuntimeException("Error while deleting files.", exception);
        }
//        deleteDockerImage(uuid);
    }

//    private void deleteDockerImage(String uuid) {
//
//        ProcessBuilder removeImageProcess = new ProcessBuilder( "docker rmi image" + uuid);
//        removeImageProcess.redirectErrorStream(true);
//        try {
//            Process removeProcess = removeImageProcess.start();
//            int buildExitCode = removeProcess.waitFor();
//            if (buildExitCode != 0) {
//                throw new RuntimeException();
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to delete Docker image.", e);
//        }
//    }
}
