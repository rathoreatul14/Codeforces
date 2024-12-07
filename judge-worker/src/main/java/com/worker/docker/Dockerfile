FROM alpine:latest

ARG language

RUN apk update && apk add build-base g++
# RUN if [ "$language" = "cpp" ]; then \
#       apk update && apk add build-base g++; \
#     elif [ "$language" = "java" ]; then \
#       apk update && apk add openjdk17; \
#     elif [ "$language" = "py" ]; then \
#       apk update && apk add python3; \
#     else \
#       echo "Unsupported language"; exit 1; \
#     fi

COPY ./src/main/java/com/worker/docker .