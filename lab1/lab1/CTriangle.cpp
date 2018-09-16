#include "stdafx.h"
#include "CTriangle.h"

CTriangle::CTriangle(const double& side1, const double& side2, const double& side3)
{
	if (CheckExistenceOfTriangle(side1, side2, side3))
	{
		SetTriangleSides(side1, side2, side3);
		SetTriangleType();
	}
	else
	{
		throw std::invalid_argument("не треугольник");
	}
}

CTriangle::~CTriangle()
{
}

void CTriangle::SetTriangleType()
{
	if ((m_side1 == m_side2) && (m_side2 == m_side3))
	{
		m_triangleType = "равносторонний";
	}
	else if ((m_side1 == m_side2) || (m_side2 == m_side3) || (m_side3 == m_side1))
	{
		m_triangleType = "равнобедренный";
	}
	else
	{
		m_triangleType = "обычный";
	}
}

void CTriangle::SetTriangleSides(const double& side1, const double& side2, const double& side3)
{
	m_side1 = side1;
	m_side2 = side2;
	m_side3 = side3;
}

std::string CTriangle::GetTriangleType()
{
	return m_triangleType;
}

bool CTriangle::CheckExistenceOfTriangle(const double& side1, const double& side2, const double& side3)
{
	if ((side1 < (side2 + side3)) && (side2 < (side1 + side3)) && (side3 < (side2 + side1)) && (side1 != 0) && (side2 != 0) && (side3 != 0))
	{
		return true;
	}

	return false;
}
