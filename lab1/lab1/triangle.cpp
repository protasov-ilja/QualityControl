// lab1.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include "CTriangle.h"

std::string GetTriangleType(double side1, double side2, double side3);
double ConvertStringToDouble(const char* inputString);

int main(int argc, char* argv[])
{
	SetConsoleCP(1251);
	SetConsoleOutputCP(1251);
	setlocale(LC_CTYPE, "rus");

	if (argc != 4)
	{
		std::cout << "Invalid arguments count\n"
				  << "Usage: traingle.exe <a> <b> <c> where a b c are sides of triangle\n";

		return 1;
	}

	try
	{
		double side1 = ConvertStringToDouble(argv[1]);
		double side2 = ConvertStringToDouble(argv[2]);
		double side3 = ConvertStringToDouble(argv[3]);

		std::cout << GetTriangleType(side1, side2, side3);

		return 0;
	}
	catch (const std::out_of_range& err)
	{
		std::cout << err.what();
	}
	catch (const std::invalid_argument& err)
	{
		std::cout << err.what();
	}

	return 1;
}

double ConvertStringToDouble(const char* inputString)
{
	std::string str = inputString;
	std::replace(str.begin(), str.end(), ',', '.');
	double number = std::stod(str);

	if (number < 0)
	{
		throw std::out_of_range("side cant be less than zero");
	}
	else
	{
		return number;
	}
}

std::string GetTriangleType(double side1, double side2, double side3)
{
	try
	{
		CTriangle triangle(side1, side2, side3);

		return triangle.GetTriangleType();
	}
	catch (const std::invalid_argument& err)
	{
		return err.what();
	}
}
