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
  fstream myfile("../Grzybobranie/files/" + fileName, ios_base::in);
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

int main()
{
  clock_t begin;
  clock_t end;
  double elapsed_secs;
  double sum = 0;
  double avgTime = 0;
  int iterations = 1;
  int precision = std::numeric_limits<double>::max_digits10;
  cout.precision(precision);
  MatrixXd matrix1 = loadMatrix("matrix.txt");
  printf("Matrix A : \n");
  cout << matrix1 << endl;


  VectorXd vector = loadMatrix("vector.txt");
  printf("\nVector X : \n");
  cout << vector << endl;

  VectorXd resultVector = matrix1.partialPivLu().solve(vector);
  
  cout << resultVector << endl;
  int pause;
  cin >> pause;
  return 0;
}