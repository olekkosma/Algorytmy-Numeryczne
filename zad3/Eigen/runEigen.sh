#!/bin/bash
g++ -I eigen-lib/ -std=c++11 -o3 -march=native eigen.cpp -o Eigen.out
./Eigen.out
