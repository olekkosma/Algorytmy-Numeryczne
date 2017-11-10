chmod -R 777 Generator/
chmod -R 777 Eigen/
cd Generator
javac Generator.java
java Generator
cd ../Eigen
runEigen.bat
cd ..
