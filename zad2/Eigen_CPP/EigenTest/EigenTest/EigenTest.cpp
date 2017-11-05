#include "stdafx.h"
#include <iostream>
#include <Eigen/Dense>
#include <ctime>
#include <time.h>
#include <chrono>
#include <fstream>
//have to select path to eigen libary
//project>>EigenTest properties>>C/C++>>General>>Aditional Include Directories

using namespace std;
using namespace Eigen;


MatrixXd loadRandomValuesMatrix(string fileName) {

	fstream myfile("C:/Users/Ukleja/Desktop/Algorytmy-Numeryczne/zad2/randomValues/"+fileName, ios_base::in);
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
	cout.precision(16);
	
	
	MatrixXd matrix1 = loadRandomValuesMatrix("values1.txt");
	printf("Matrix A : \n");
	cout << matrix1 << endl;
	
	MatrixXd matrix2 = loadRandomValuesMatrix("values2.txt");
	printf("\nMatrix B : \n");
	cout << matrix2 << endl;

	MatrixXd multiplyThreeMatrix = matrix1 + matrix2;

	printf("\nMatrix A + B : \n");
	cout << multiplyThreeMatrix << endl;
	/*

	MatrixXd matrix3 = loadRandomValuesMatrix("values3.txt");
	printf("\nMatrix C : \n");
	//cout << matrix3 << endl;

	VectorXd vector = loadRandomValuesMatrix("valuesVector.txt");
	printf("\nVector X : \n");
	//cout << vector << endl;
	



	printf("\n\n A * X counting...\n");
	begin = clock();

	VectorXd matrixMultipliedbyVector = matrix1 * vector;

	end = clock();
	elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	printf("time = %f secs\n\n", elapsed_secs);
	//cout << matrixMultipliedbyVector << endl;


	printf("\n\n (A + B + C ) * X counting...\n");
	begin = clock();

	VectorXd sumOfThreeMatrixMultipliedByVector = (matrix1 + matrix2 + matrix3) * vector;

	end = clock();
	elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	printf("time = %f secs\n\n", elapsed_secs);
	//cout << sumOfThreeMatrixMultipliedByVector << endl;


	printf("\n\n A * (B * C) counting...\n");
	begin = clock();

	MatrixXd multiplyThreeMatrix = matrix1 * (matrix2 * matrix3);

	end = clock();
	elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	printf("time = %f secs\n\n", elapsed_secs);
	//cout << multiplyThreeMatrix << endl;
	

	MatrixXd A(3,3);
	VectorXd b(3);
	A << 1, 2, 3,
		4, 5, 6,
		7, 8, 10;
	b << 3, 3, 4;
	cout << "Here is the matrix A:\n" << matrix1 << endl;
	cout << "Here is the vector b:\n" << vector << endl;
	VectorXd x = matrix1.partialPivLu().solve(vector);
	cout << "The solution is:\n" << x << endl;
	*/

	int pause;
	cin >> pause;
	return 0;
}

