#!/bin/bash
g++ -I eigen-lib/ -o3 -march=native -std=c++11 eigen.cpp -o Eigen.out
./Eigen.out
