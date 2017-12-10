//Aleksander Kosma / Tomasz Adamczyk
//Nr. indexu: 238193 / 243217
//09.11.2017
//Algorytmy Numeryczne
//--------------------
#include <Eigen/Dense>
#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <chrono>
#include <fstream>
#include <iomanip>

using namespace std;
using namespace Eigen;

MatrixXd loadMatrix(string fileName) {
  fstream myfile("../output/" + fileName, ios_base::in);
  int rows;
  int columns;
  myfile >> rows;
  myfile >> columns;
  MatrixXd matrix(rows, columns);
  for (int i = 0; i < rows; i++) {
    for (int j = 0; j < columns; j++) {
      myfile >> matrix(i, j);
    }
  }
  return matrix;
}

void writeMatrixToFile(double time1,double time2, double result, string fileName) {
	fstream myfile("../output/" + fileName, ios_base::out);
	cout << "aa";
	myfile << fixed << result;
	myfile << fixed << time1;
	myfile << fixed << time2;

	
}

int main()
{
  clock_t begin;
  clock_t end;
  double elapsed_secs1,elapsed_secs2;
  double sum = 0;
  double avgTime = 0;
  int iterations = 1;
  int precision = std::numeric_limits<double>::max_digits10;
  cout.precision(precision);


  MatrixXd matrix1 = loadMatrix("matrix.txt");
  printf("Matrix A : \n");

  MatrixXd matrix1Parse = loadMatrix("matrix.txt");
  printf("Matrix A : \n");


  VectorXd vector = loadMatrix("vector.txt");
  printf("\nVector X : \n");

  VectorXd vectorParse = loadMatrix("vector.txt");
  printf("\nVector X : \n");

  begin = clock();
  VectorXd resultVector = matrix1.partialPivLu().solve(vector);
  end = clock();
  elapsed_secs1 = double(end - begin) / CLOCKS_PER_SEC;

  begin = clock();
  //VectorXd resultVector2 = matrix1.SparseLU().solve(vector);
  end = clock();
  elapsed_secs2 = double(end - begin) / CLOCKS_PER_SEC;

  writeMatrixToFile(elapsed_secs1, elapsed_secs2, resultVector[0], "Result.txt");
  //cout << resultVector << endl;
  int pause;
  cin >> pause;
  return 0;
}