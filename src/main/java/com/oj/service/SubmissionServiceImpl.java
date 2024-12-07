package com.oj.service;

import org.springframework.stereotype.Service;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Override
    public String execute() {
        String source_code = "#include <iostream> using namespace std; int main() { cout << \"Hello World\"; return 0; }\n";

        return "";
    }
}
