chmod -R 777 Generator/
chmod -R 777 Eigen/
cd Generator
javac Generator.java
java Generator
pause
cd ../Eigen
runEigen.bat
cd ..
