#pragma once
class CTriangle
{
public:
	CTriangle(const double& side1, const double& side2, const double& side3);
	~CTriangle();
	std::string GetTriangleType();

private:
	void SetTriangleSides(const double& side1, const double& side2, const double& side3);
	bool CheckExistenceOfTriangle(const double& side1, const double& side2, const double& side3);
	void SetTriangleType();

	double m_side1;
	double m_side2;
	double m_side3;
	std::string m_triangleType;
};
