package unitests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

public class TriangleTest {
    /**
     * Test method for {@link geometries.Triangle#getNormal(geometries.Triangle)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Normal Normal
        Triangle t1 = new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(t1.getNormal(new Point(0, 0.5, 0.5)), new Vector(0, 0, 1), "Triangle Wrong Normal!");
        assertEquals(t1.getNormal(new Point(0, 0.5, 0.5)).length(), 1, "Triangle Wrong Normal!");
    }

    /**
     * Test method for
     * {@link geometries.Triangle#findIntersections(geometries.Triangle)}.
     */
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        // only if the ray intersects with the plane :

        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        // TC01: The Ray intersects with the triangle
        assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)),
                triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 1))),
                "Bad triangle intersection");
        // TC02: The Ray intersects with the plane but not with the triangle(between two
        // lines)
        assertEquals(List.of(new Point(-0.25,-0.25,1.5000000000000002)),
                plane.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-0.25, -0.25, 1.5))),
                "Must be intersection with the plane");
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-0.25, -0.25, 1.5))),
                "Bad triangle intersection");
        // TC03: The Ray intersects with the plane but not with the triangle(not between
        // two lines)
        assertEquals(List.of(new Point(1.0,-1.4999999999999998,1.4999999999999998)),
                plane.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, -1.5, 1.5))),
                "Must be intersection with the plane");
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, -1, 1))),
                "Bad triangle intersection");
        // =============== Boundary Values Tests ==================

        // TC01: The Ray intersects with the plane and on an edge of the triangle
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1))),
                "Bad triangle intersection");
        // TC02: The Ray intersects with the plane and on the side of the triangle
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(0.5, 0, 0.5))),
                "Bad triangle intersection");
        // TC03: The Ray intersects with the plane but not with the triangle(on a line)
        assertEquals(List.of(new Point(-1, 0, 2)),
                plane.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, 0, 2))),
                "Must be intersection with the plane");
        assertNull(triangle.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, 0, 2))),
                "Bad triangle intersection");
    }

}