
#include <cmath>
#include <cstring>

#include "NamedPoint.hxx"

namespace {
	const double epsilon = 1e-5;
}

//
NamedPoint::NamedPoint( double cx, double cy, const char* cn )
  : m_x{cx}, m_y{cy}
{
  m_name = new char[1+strlen(cn)];
  strcpy(m_name, cn);
}

//
NamedPoint::NamedPoint()
  : NamedPoint{0,0,"origin"}
{}

//
NamedPoint::NamedPoint( const NamedPoint& po )
  : NamedPoint{po.m_x, po.m_y, po.m_name}
{}

//
NamedPoint::NamedPoint( NamedPoint&& po )
  : m_x{po.m_x}, m_y{po.m_y}, m_name{po.m_name}
{
  po.m_name = nullptr;
}

//
NamedPoint::~NamedPoint()
{
  delete[] m_name;
}

//
NamedPoint& NamedPoint::operator=( const NamedPoint& po )
{
  if( this != &po ) {
	delete[] m_name;
	m_name = new char[1+strlen(po.m_name)];
	strcpy(m_name, po.m_name);
  }

  return *this;
}

//
NamedPoint& NamedPoint::operator=( NamedPoint&& po )
{
  if( this != &po ) {
	delete[] m_name;
	m_name = po.m_name;
	po.m_name = nullptr;
  }

  return *this;
}

//
double NamedPoint::distance( const NamedPoint& po )
{
  const double dx = m_x - po.m_x;
  const double dy = m_y - po.m_y;
  return std::sqrt(dx * dx + dy * dy);
}

//
void NamedPoint::moveBy( double dx, double dy )
{
  m_x += dx;
  m_y += dy;
}

//
NamedPoint NamedPoint::midPoint( const NamedPoint& po, double ps )
{
  const double mx = ps * (m_x + po.m_x);
  const double my = ps * (m_y + po.m_y);
  return NamedPoint{mx, my, "midpoint"};
}

//
bool operator==( const NamedPoint& p0, const NamedPoint& p1 )
{
  const double ex = std::abs(p0.x() - p1.x());
  const double ey = std::abs(p0.y() - p1.y());
  return (ex < epsilon) && (ey < epsilon);
}

//
bool operator!=( const NamedPoint& p0, const NamedPoint& p1 )
{
  return !(p0 == p1);
}

