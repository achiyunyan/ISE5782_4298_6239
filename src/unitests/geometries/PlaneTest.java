package unitests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}