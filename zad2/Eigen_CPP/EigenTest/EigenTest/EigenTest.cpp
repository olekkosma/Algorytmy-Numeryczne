#include "stdafx.h"
#include <iostream>
#include <Eigen/Dense>
//have to select path to eigen libary
//project>>EigenTest properties>>C/C++>>General>>Aditional Include Directories

using namespace std;
using namespace Eigen;

int main()
{
	typedef Matrix<long double, Dynamic, Dynamic> MatrixXf;
	//MatrixXd m = MatrixXd::Random(3,3);
	MatrixXf m(2, 2);
	m << 1.33333333333, 2.333333333333,
		3.44455555555, 6.74564565646;


	//VectorXd u(3);
	//u << 1, 2, 3;

	std::cout << m << std::endl;

	int pause;
	cin >> pause;

	return 0;
}

