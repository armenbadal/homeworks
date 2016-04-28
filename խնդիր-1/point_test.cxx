
#include <cmath>
#include <iostream>
#include <utility>

#include "NamedPoint.hxx"

//
void print( const NamedPoint& po )
{
  std::cout << po.name() << "(" << po.x()
			<< "," << po.y() << ")" 
			<< std::endl;	
}

template<typename Q>
void check_for( Q e, Q a )
{
  std::cout << "expected " << e << ", and got " << a << ", ";
  std::cout << (e == a ? "Ok" : "Bad") << std::endl;
}

//
int main()
{
  // some points
  NamedPoint origin;
  NamedPoint north{0, 10, "north"};
  NamedPoint south{0, -10, "south"};
  NamedPoint east{10, 0, "east"};
  NamedPoint west{-10, 0, "west"};

  // checking data
  print(origin);
  print(north);
  print(south);
  print(east);
  print(west);

  // distances: expected 10
  check_for(10.0, origin.distance(north));
  check_for(10.0, origin.distance(south));
  check_for(10.0, origin.distance(east));
  check_for(10.0, origin.distance(west));
  check_for(20.0, east.distance(west));
  check_for(std::sqrt(200), north.distance(east));

  //
  west.moveBy(10, 10);
  check_for(true, west == north);
  check_for(true, south != origin);

  //
  NamedPoint mp{origin};
  mp = north.midPoint(south);
  check_for(true, mp == origin);

  NamedPoint np0 = std::move(mp);
  check_for(true, np0 == origin);

  NamedPoint np1;
  np1 = np0;
  check_for(true, np1 == origin);

  print(np1);
  print(np0);
}


