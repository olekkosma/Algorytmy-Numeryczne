javac .\Monte-Carlo\src\*.java
cd Monte-Carlo\src
java  -cp . Main
cd ..
cd ..
cd .\Grzybobranie\src
javac *.java
java  -cp . Main
cd..
cd..
cd Eigen
g++ -I eigen-lib/ -std=c++11 -o3 -march=native eigen.cpp -o Eigen.exe
Eigen.exe

pause