package unitests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

public class PlaneTest {
  @Test
  void testConstructor() {
    // =============== Boundary Values Tests ==================

    // TC01: First Point equals to the Second Point
    assertThrows(IllegalArgumentException.class,
        () -> new Plane(new Point(1, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0)),
        "Constructed a Plane with 2 identical Points");

    // TC02: All 3 Points on the same line
    assertThrows(IllegalArgumentException.class,
        () -> new Plane(new Point(0, 0, 0), new Point(1, 1, 1), new Point(2, 2, 2)),
        "Constructed a Plane with 2 identical Points");
  }

  /**
   * Test method for {@link geometries.Plane#getNormal(geometries.Plane)}.
   */
  @Test
  void testGetNormal() {
    // ============ Equivalence Partitions Tests ==============

    // TC01: Normal
    Plane p1 = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
    assertEquals(p1.getNormal(new Point(1, 2, 3)), new Vector(0, 0, 1), "Plane Wrong Normal!");
    assertEquals(p1.getNormal(new Point(1, 2, 3)).length(), 1, "Plane Wrong Normal!");

  }

  /**
   * Test method for {@link geometries.Plane#findIntersections(Ray)}
   * (primitives.Ray)}.
   */
  @Test
  public void findIntersections() {

    Plane pl = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));

    // ============ Equivalence Partitions Tests ==============
    // TC01: Ray into plane
    assertEquals(List.of(new Point(1, 0, 0)),
        pl.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
        "Bad plane intersection");

    // TC02: Ray out of plane
    assertNull(pl.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
        "Must not be plane intersection");

    // =============== Boundary Values Tests ==================
    // TC11: Ray parallel to plane
    assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 1, -1))),
        "Must not be plane intersection");

    // TC12: Ray in plane
    assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, .5), new Vector(0, 1, -1))),
        "Must not be plane intersection");

    // TC13: Orthogonal ray into plane
    assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)),
        pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1))),
        "Bad plane intersection");

    // TC14: Orthogonal ray out of plane
    assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
        "Must not be plane intersection");

    // TC15: Orthogonal ray out of plane
    assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
        "Must not be plane intersection");

    // TC16: Orthogonal ray from plane
    assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 1))),
        "Must not be plane intersection");

    // TC17: Ray from plane
    assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 0))),
        "Must not be plane intersection");

  }
}