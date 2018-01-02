#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <chrono>
#include <fstream>
#include <iomanip>
#include <Eigen/Dense>
#include <Eigen/SparseLU>
#include <cmath>

using namespace std;
using namespace Eigen;

MatrixXd loadMatrix(string fileName) {
	fstream myfile("../../../../output/" + fileName, ios_base::in);
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

SparseMatrix<double> loadMatrixSparse(string fileName, VectorXi vector) {
	fstream myfile("../../../../output/" + fileName, ios_base::in);
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
	fstream myfile("../../../../output/" + fileName, ios_base::in);
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

void writeMatrixToFile(double time1, double time2, string fileName) {
	fstream myfile("../../../../output/" + fileName, ios_base::app);
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


int findBiggestRowInColumn(SparseMatrix<double> matrix, int column) {
	double max = 0.0;
	int row = 0;

	for (int i = 0; i < matrix.rows(); i++) {
		if (max < matrix.coeff(i,column)) {
			max = matrix.coeff(i, column);
			row = i;
		}

	}
	return row;
}

void swapRows(SparseMatrix<double> matrix, int row1, int row2) {
	for (int i = 0; i < matrix.cols(); i++) {
		double tmp = matrix.coeff(row1,i);
		matrix.insert(row1, i) = matrix.coeff(row2, i);
		matrix.insert(row2, i) = tmp;
	}
}

void swapRowsVector(VectorXd matrix, int row1, int row2) {
		double tmp = matrix(row1);
		matrix(row1) = matrix(row2);
		matrix(row2) = tmp;
}
int main() {
	clock_t begin;
	clock_t end;
	double elapsed_secs1;
	double sum = 0;
	double avgTime = 0;
	//int iterations = 1;
	int precision = std::numeric_limits<double>::max_digits10;
	cout.precision(precision);

	VectorXi vectorDens2 = loadMatrixColsDens("Densematrix.txt");
	SparseMatrix<double> matrixSparseSeidl = loadMatrixSparse("matrix.txt", vectorDens2);
	matrixSparseSeidl.makeCompressed();
	printf("Matrix sparse loaded : \n");

	VectorXd vectorParseSeidl = loadMatrix("vector.txt");
	printf("\nVector sparse loaded : \n");
	VectorXd X(matrixSparseSeidl.rows(), 1);

	int size = matrixSparseSeidl.rows();

	//int size = 16250;
	sum = 0;
	int iterations = 0;
	int z = 0;
	bool stillCount = true;
	double tmp = 0, tmp2 = 0;
	int counter = 0, iterator = 0;
	double epsylon = 0.0000000001;
	begin = clock();
	
	while (stillCount) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < i; j++) {
				sum -= matrixSparseSeidl.coeff(i, j) * X(j);
			}
			for (int j = i + 1; j < size; j++) {
				sum -= matrixSparseSeidl.coeff(i, j) * X(j);
			}
			if (matrixSparseSeidl.coeff(i, i) == 0.0) {
				int row = findBiggestRowInColumn(matrixSparseSeidl, i);
				swapRows(matrixSparseSeidl, i, row);
				swapRowsVector(vectorParseSeidl, i, row);
			}
			X(i) = (vectorParseSeidl(i) + sum) / matrixSparseSeidl.coeff(i, i);
			sum = 0.0;
		}
		if (z != 0) {
			for (int g = 0; g < X.rows(); g++) {
				tmp2 += abs(X(g));
			}
			tmp2 = tmp2 / X.rows();
			if (abs(tmp - tmp2) > epsylon) {
				iterator = 0;
			}
			else {
				if (iterator == 4) {
					stillCount = false;
				}
				iterator++;
			}
			tmp = tmp2;
			counter++;
		}
		z = 1;
		iterations++;
	}

	end = clock();
	cout << X(0) << endl;
	cout << "iterations= ";
	cout << iterations << endl;


	elapsed_secs1 = double(end - begin) / CLOCKS_PER_SEC;
	cout << "time= ";
	cout << elapsed_secs1 << endl;


	cin.get();
	return 0;
}