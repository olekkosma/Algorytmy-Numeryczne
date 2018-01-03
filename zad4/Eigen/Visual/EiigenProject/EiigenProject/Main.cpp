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
	int values;
	int columns;
	myfile >> values;
	myfile >> rows;
	myfile >> columns;
	double tmp, x, y;
	SparseMatrix<double> matrix(rows, columns);
	matrix.reserve(vector);
	for (int i = 0; i < values; i++) {
		myfile >> x;
		myfile >> y;
		myfile >> tmp;
		matrix.insert(x, y) = tmp;
	}
	return matrix;
}

VectorXi loadMatrixColsDens(string fileName) {
	fstream myfile("../../../../output/" + fileName, ios_base::in);
	int rows=0;
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

int findBiggestRowInColumn2(MatrixXd matrix, int column) {
	double max = 0.0;
	int row = 0;

	for (int i = 0; i < matrix.rows(); i++) {
		if (max < matrix(i, column)) {
			max = matrix(i, column);
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

void swapRows2(MatrixXd matrix, int row1, int row2) {
	for (int i = 0; i < matrix.cols(); i++) {
		double tmp = matrix(row1, i);
		matrix(row1, i) = matrix(row2, i);
		matrix(row2, i) = tmp;
	}
}
double countNorm(VectorXd matrix) {
	double normValue = 0.0;
	for (int i = 0; i < matrix.rows(); i++) {
		normValue += matrix(i) * matrix(i);
	}
	normValue = sqrt(normValue);
	return  normValue;
}

void swapRowsVector(VectorXd matrix, int row1, int row2) {
		double tmp = matrix(row1);
		matrix(row1) = matrix(row2);
		matrix(row2) = tmp;
}
int main() {
	clock_t begin;
	clock_t end;
	double elapsed_secs1, elapsed_secs2;
	double sum = 0;
	double avgTime = 0;
	//int iterations = 1;
	int precision = std::numeric_limits<double>::max_digits10;
	cout.precision(precision);

	VectorXi vectorDens2 = loadMatrixColsDens("Densematrix.txt");
	SparseMatrix<double, RowMajor> matrixSparseSeidl = loadMatrixSparse("matrix.txt", vectorDens2);
	matrixSparseSeidl.makeCompressed();
	printf("Matrix sparse loaded : \n");
	VectorXd vectorParseSeidl = loadMatrix("vector.txt");
	printf("\nVector sparse loaded : \n");
	VectorXd X(matrixSparseSeidl.rows(), 1);

	int size = matrixSparseSeidl.rows();
	sum = 0;
	double sum2 = 0;
	int iterations = 0;
	int z = 0;
	bool stillCount = true;
	double tmp = 0, tmp2 = 0;
	int counter = 0, iterator = 0;
	double epsylon = 0.0000000001;
	int iter = 0;
	double norm2 = countNorm(vectorParseSeidl);
	begin = clock();
	while (stillCount) {
		iter = 0;
			for (int k = 0; k < matrixSparseSeidl.outerSize(); ++k) {
				for (SparseMatrix<double, RowMajor>::InnerIterator it(matrixSparseSeidl, k); it; ++it) {
					if (it.col() != it.row()) {
						sum2 -= it.value()*X(it.col());
					}
					else {
						if (it.value() == 0.0) {
						
							int row = findBiggestRowInColumn(matrixSparseSeidl, it.row());
							swapRows(matrixSparseSeidl, it.row(), row);
							swapRowsVector(vectorParseSeidl, it.row(), row);
						}
					}
					
				}
				X(iter) = (vectorParseSeidl(iter) + sum2) / matrixSparseSeidl.coeff(iter, iter);
				sum2 = 0.0;
				iter++;
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
	elapsed_secs2 = double(end - begin) / CLOCKS_PER_SEC;
	cout << "time= ";
	cout << elapsed_secs2 << endl;
	cout << "Result= ";
	cout << X(0) << endl;
	cout << "iterations= ";
	cout << iterations << endl;
	cout << "=================== " << endl;

	z = 0,iterations = 0, tmp = 0, tmp2 = 0, counter = 0, iterator = 0, stillCount = true;
	VectorXd X2(matrixSparseSeidl.rows(), 1);
	begin = clock();

	while (stillCount) {
		for (int i = 0; i < matrixSparseSeidl.rows(); i++) {
			/*for (int j = 0; j < matrixSparseSeidl.rows(); j++) {           //Alternative
				if (i != j) {
					sum -= matrixSparseSeidl.coeff(i, j) * X2(j);
				}
				else {
					if (matrixSparseSeidl.coeff(i, i) == 0.0) {
						int row = findBiggestRowInColumn(matrixSparseSeidl, i);
						swapRows(matrixSparseSeidl, i, row);
						swapRowsVector(vectorParseSeidl, i, row);
					}
				}
			}*/
			for (int j = 0; j < i; j++) {
				sum -= matrixSparseSeidl.coeff(i, j) * X2(j);
			}
			for (int j = i + 1; j < size; j++) {
				sum -= matrixSparseSeidl.coeff(i, j) * X2(j);
			}
			if (matrixSparseSeidl.coeff(i, i) == 0.0) {
				int row = findBiggestRowInColumn(matrixSparseSeidl, i);
				swapRows(matrixSparseSeidl, i, row);
				swapRowsVector(vectorParseSeidl, i, row);
			}
			X2(i) = (vectorParseSeidl(i) + sum) / matrixSparseSeidl.coeff(i, i);
			sum = 0.0;
		}
		if (z != 0) {
			for (int g = 0; g < X2.rows(); g++) {
				tmp2 += abs(X2(g));
			}
			tmp2 = tmp2 / X2.rows();
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
	elapsed_secs1 = double(end - begin) / CLOCKS_PER_SEC;
	cout << "time= ";
	cout << elapsed_secs1 << endl;
	cout << "Result= ";
	cout << X2(0) << endl;
	cout << "iterations= ";
	cout << iterations << endl;
	
	cin.get();
	return 0;
}