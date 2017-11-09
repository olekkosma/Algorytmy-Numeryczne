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

MatrixXd loadRandomValuesMatrix(string fileName) {
  fstream myfile("../Values/" + fileName, ios_base::in);
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

void writeMatrixToFile(MatrixXd matrix, string fileName, double time) {
  fstream myfile("../Values/AddMultiplyFiles/values" + fileName, ios_base::out);
  int rows = matrix.rows();
  int columns = matrix.cols();
  myfile << fixed << time << endl;
  myfile << rows << endl;
  myfile << columns << endl;
  int precision = std::numeric_limits<double>::max_digits10;
  for (int i = 0; i < rows; i++) {
    for (int j = 0; j < columns; j++) {
      myfile << setprecision(precision) << matrix(i, j) << endl;
    }
  }
}

int main()
{
  clock_t begin;
  clock_t end;
  double elapsed_secs;
  double sum = 0;
  int iterations = 1;
  double avgTime = 0;
  int precision = std::numeric_limits<double>::max_digits10;
  cout.precision(precision);
  MatrixXd matrix1 = loadRandomValuesMatrix("values1.txt");
  printf("Matrix A : \n");
  //cout << matrix1 << endl;

  MatrixXd matrix2 = loadRandomValuesMatrix("values2.txt");
  printf("\nMatrix B : \n");
  //cout << matrix2 << endl;

  MatrixXd matrix3 = loadRandomValuesMatrix("values3.txt");
  printf("\nMatrix C : \n");
  //cout << matrix3 << endl;

  VectorXd vector = loadRandomValuesMatrix("valuesVector.txt");
  printf("\nVector X : \n");
  //cout << vector << endl;

  int decision = 1;
  if (decision == 1) {
    printf("\n\n A * X counting...\n");
    begin = clock();
    VectorXd matrixMultipliedbyVector = matrix1 * vector;
    end = clock();
    elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
    printf("time = %f secs\n\n", elapsed_secs);
    //cout << matrixMultipliedbyVector << endl;
    writeMatrixToFile(matrixMultipliedbyVector, "AX.txt", elapsed_secs);
    printf("\n\n (A + B + C ) * X counting...\n");
    begin = clock();
    VectorXd sumOfThreeMatrixMultipliedByVector = (matrix1 + matrix2 + matrix3) * vector;
    end = clock();
    elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
    printf("time = %f secs\n\n", elapsed_secs);
    //cout << sumOfThreeMatrixMultipliedByVector << endl;
    writeMatrixToFile(sumOfThreeMatrixMultipliedByVector, "ABCX.txt", elapsed_secs);
    sum = 0;
    avgTime = 0;
    printf("\n\n A * (B * C) counting...\n");
    begin = clock();
    MatrixXd multiplyThreeMatrix = matrix1 * (matrix2 * matrix3);
    end = clock();
    elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
    printf("time = %f secs\n\n", elapsed_secs);
    //cout << multiplyThreeMatrix << endl;
    writeMatrixToFile(multiplyThreeMatrix, "ABC.txt", elapsed_secs);
  }
  else {
    begin = clock();
    VectorXd resultVector = matrix1.partialPivLu().solve(vector);
    end = clock();
    elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
    writeMatrixToFile(resultVector, "partial.txt", elapsed_secs);
    begin = clock();
    VectorXd resultVectorFull = matrix1.fullPivLu().solve(vector);
    end = clock();
    elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
    sum = sum + elapsed_secs;
    writeMatrixToFile(resultVectorFull, "full.txt", elapsed_secs);
  }
  //int pause;
  //cin >> pause;
  return 0;
}

