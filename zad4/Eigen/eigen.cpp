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
#include<Eigen/SparseLU>

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

SparseMatrix<double> loadMatrixSparse(string fileName,VectorXi vector) {
	fstream myfile("../output/" + fileName, ios_base::in);
	int rows;
	int columns;
	myfile >> rows;
	myfile >> columns;
	SparseMatrix<double> matrix(rows, columns);
	matrix.reserve(vector);
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < columns; j++) {
			double tmp;
			myfile >> tmp;
			if (tmp != 0) {
				matrix.insert(i, j) = tmp;
			}
		}
		
	}
	return matrix;
}

VectorXi loadMatrixColsDens(string fileName) {
	fstream myfile("../output/" + fileName, ios_base::in);
	int rows;
	int columns;
	myfile >> columns;
	VectorXi matrix(columns);
		for (int j = 0; j < columns; j++) {
			int tmp;
			myfile >> tmp;
			matrix(j) = tmp;
		}
	
	return matrix;
}

void writeMatrixToFile(double time1,double time2, string fileName) {
	fstream myfile("../output/" + fileName, ios_base::app);
	int precision = std::numeric_limits<double>::max_digits10;
	ostringstream oss;
	oss.precision(precision);
	string str;

	str = to_string(time1);
	replace(str.begin(), str.end(), '.', ',');
	str = ";" + str;
	myfile << fixed << str;

	str = to_string(time2);
	replace(str.begin(), str.end(), '.', ',');
	str = ";" + str;
	myfile << fixed << str;
	cout << "finish";
	
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
  /*
  VectorXi vectorDens = loadMatrixColsDens("Densematrix.txt");
  SparseMatrix<double> matrixSparse = loadMatrixSparse("matrix.txt",vectorDens);
  SparseLU<Eigen::SparseMatrix<double> > solverA;
  matrixSparse.makeCompressed();
  solverA.analyzePattern(matrixSparse);
  solverA.factorize(matrixSparse);
  printf("Matrix sparse loaded : \n");

  VectorXd vectorParse = loadMatrix("vector.txt");
  printf("\nVector sparse loaded : \n");
  */

  VectorXi vectorDens2 = loadMatrixColsDens("Densematrix.txt");
  SparseMatrix<double> matrixSparseSeidl = loadMatrixSparse("matrix.txt", vectorDens2);
  matrixSparseSeidl.makeCompressed();
  printf("Matrix sparse loaded : \n");

  VectorXd vectorParseSeidl = loadMatrix("vector.txt");
  printf("\nVector sparse loaded : \n");
  VectorXd X(matrixSparseSeidl.rows(), 1);
  int size = matrixSparseSeidl.rows();
  sum = 0.0;

  begin = clock();

	  for (int k = 0; k < 200; k++) {
		  for (int i = 0; i < size; i++) {
			  for (int j = 0; j < i; j++) {
				  sum -= matrixSparseSeidl.coeff(i,j) * X(j);
			  }
			  for (int j = i + 1; j < size; j++) {
				  sum += 1;
			  }
		  }
	}
  end = clock();
  cout << sum << endl;
  elapsed_secs1 = double(end - begin) / CLOCKS_PER_SEC;
  cout<<"time= ";
  cout << elapsed_secs1 << endl;
  /*
  begin = clock();
  for (int i = 0; i < iterations; i++) {
	  VectorXd solnew = solverA.solve(vectorParse);
	  cout << solnew(0) << endl;
  }
  end = clock();
  elapsed_secs2 = double(end - begin) / CLOCKS_PER_SEC;
  elapsed_secs2 = elapsed_secs2 / iterations;
  cout << elapsed_secs2 << endl;
  
  writeMatrixToFile(elapsed_secs1, elapsed_secs2, "Result.txt");
 */
  int pause;
  cin >> pause;
  return 0;
}