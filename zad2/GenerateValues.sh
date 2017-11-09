#!/bin/bash
cd Generator
javac Generator.java
java Generator
cd ../Eigen
./run.sh
cd ..
