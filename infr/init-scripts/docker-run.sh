#!/usr/bin/env bash

docker run -p 8080:8080 -d --mount type=bind,source=/home/ubuntu/application.properties,target=/application.properties grimz5129/sakilarestapi2
