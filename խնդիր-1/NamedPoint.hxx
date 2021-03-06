
/**
 * Խնդիր #1 (C++)
 *  
 * Գրել `NamedPoint` դասը, որն ունիի երեք անդամ․ հարթության 
 * վրա կետի կոորդինատները ներկայացնող երկու իրական թվեր և 
 * `char*`֊ով ներկայացվող կետի անունը։ Ենթադրվում է, որ այս 
 * դասը պետք է օգտագործվի ինչ֊որ կռիվ֊խաղում, որտեղ աշխարհը 
 * ներկայացված է հարթությամբ, իսկ անվանված կետերը ներկայացնում 
 * են քաղաքներ, մարտադաշտեր և այլն։
 */

#ifndef NAMED_POINT_H
#define NAMED_POINT_H

/** 
 * @brief Cartesian (named) point.  
 */
class NamedPoint {
private:
  /// @brief Abscissa
  double m_x = 0.0;
  /// @brief Ordinate
  double m_y = 0.0;
  /// @brief Name
  char* m_name = nullptr;

public:
  /** @brief "Full" constructor.
   *
   * @param cx Value for abscissa
   * @param cy Value for ordinate
   * @param cn Value for name
   */
  NamedPoint( double cx, double cy, const char* cn );
  /// @brief Copy constructor
  NamedPoint( const NamedPoint& po );
  /// @brief Move constructor
  NamedPoint( NamedPoint&& po );
  /// @brief Default constructor
  /// Creates point for origin (0,0)
  NamedPoint();

  ~NamedPoint();

  /// @brief Regular assignment
  NamedPoint& operator=( const NamedPoint& po );
  /// @brief Move-assignment
  NamedPoint& operator=( NamedPoint&& po );

  double x() const { return m_x; }
  double y() const { return m_y; }
  const char* name() const { return m_name; }

  /** @brief Shifts point by given @c dx and @c dy
   * 
   * @param dx delta for abscissa
   * @param dy delta for ordinate
   */
  void moveBy( double dx, double dy );
  /// @brief Distance to a given point
  double distance( const NamedPoint& po );
  /** @brief "Middle" point between a given point.
   *
   * @param po another point
   * @param ps factor for midpoint. If @c ps is 0.5, then
   * the result is exact middle point between two points.
   */
  NamedPoint midPoint( const NamedPoint& po, double ps = 0.5 );
};

/// @brief Equality
bool operator==( const NamedPoint& p0, const NamedPoint& p1 );
/// @brief Inequality
bool operator!=( const NamedPoint& p0, const NamedPoint& p1 );

#endif

