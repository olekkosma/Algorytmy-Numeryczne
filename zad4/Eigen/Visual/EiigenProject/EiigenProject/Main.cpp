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

int densSize;
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
	//int values;
	int columns;
	//myfile >> values;
	myfile >> rows;
	//myfile >> columns;
	double tmp, x, y;
	SparseMatrix<double> matrix(rows, rows);
	matrix.reserve(vector);
	for (int i = 0; i < densSize; i++) {
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
	myfile>>densSize;
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
	clock_t begin,end;
	double elapsed_secs1, elapsed_secs2, sum2 = 0, tmp = 0, tmp2 = 0, epsylon = 0.0000000001,avgTime = 0;
	int precision = std::numeric_limits<double>::max_digits10,z=0,counter=0,iterator=0,iter=0,iterations=1;
	bool stillCount = true;
	cout.precision(precision);
	printf("Gauss Seidel Sprase counting...\n");
	VectorXi vectorDens2 = loadMatrixColsDens("sparseDensematrix.txt");
	SparseMatrix<double, RowMajor> matrixSparseSeidl = loadMatrixSparse("sparseMatrix.txt", vectorDens2);
	matrixSparseSeidl.makeCompressed();
	printf("Matrix sparse loaded : \n");
	VectorXd vectorParseSeidl = loadMatrix("SparseVector.txt");
	printf("\nVector sparse loaded : \n");
	
	bool tmp3 = true;
	double norm2 = countNorm(vectorParseSeidl);
	begin = clock();
	for (int i = 0; i < iterations; i++) {
		VectorXd X(matrixSparseSeidl.rows(), 1);
		stillCount = true;
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
			double norm1 = countNorm((vectorParseSeidl - (matrixSparseSeidl* X)));
			if ((norm1 / norm2) < epsylon) {
				break;
			}
		}
		if (tmp3) {
			cout << X(0) << endl;
		}
		tmp3 = false;
	}
	end = clock();
	elapsed_secs2 = double(end - begin) / CLOCKS_PER_SEC;
	elapsed_secs2 = elapsed_secs2 / iterations;
	cout << "time= ";
	cout << elapsed_secs2 << endl;
	cout << "Result= ";
	//cout << X(0) << endl;
	cout << "=================== " << endl;

	printf("Gauss Sparse counting...\n");

	VectorXi vector2 = loadMatrixColsDens("sparseDensematrix.txt");
	SparseMatrix<double> matrixSparse = loadMatrixSparse("sparseMatrix.txt", vector2);
	SparseLU<Eigen::SparseMatrix<double> > solverA;
	matrixSparse.makeCompressed();
	solverA.analyzePattern(matrixSparse);
	solverA.factorize(matrixSparse);
	printf("Matrix sparse loaded : \n");

	VectorXd vectorParse = loadMatrix("SparseVector.txt");
	printf("\nVector sparse loaded : \n");

	begin = clock();
	for (int i = 0; i < iterations; i++) {
		VectorXd solnew = solverA.solve(vectorParse);
	}
	end = clock();
	elapsed_secs1 = double(end - begin) / CLOCKS_PER_SEC;
	elapsed_secs1 = elapsed_secs1 / iterations;
	VectorXd solnew2 = solverA.solve(vectorParse);
	cout << "time= ";
	cout << elapsed_secs1 << endl;
	cout << "Result= ";
	cout << solnew2(0) << endl;

	writeMatrixToFile(elapsed_secs1, elapsed_secs2, "Result.txt");
	
	cin.get();
	return 0;
}