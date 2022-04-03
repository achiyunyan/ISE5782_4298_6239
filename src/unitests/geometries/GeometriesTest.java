package unitests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

public class GeometriesTest {
    @Test
    void testFindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        Plane plane = new Plane(new Point(4, 0, 0), new Vector(3, 4, 6));
        Sphere sphere = new Sphere(new Point(0, 0, 0), 2);
        Triangle triangle = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));
        Polygon polygon = new Polygon(new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 0, 0), new Point(0, -1, 0));

        // TC01: The Ray intersects with some geometries(not all of them)
        Geometries geometries = new Geometries(plane, sphere, triangle, polygon);
        List<Point> result = geometries.findIntersections(new Ray(new Point(0, 0, 1), new Vector(0, 0, -1)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getZ() < result.get(1).getZ())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(0, 0, 0), new Point(0, 0, -2)), result, "Wrong points,bad intersection");

        // =============== Boundary Values Tests ==================

        // TC01: The Ray intersects with none of the geometries
        assertNull(geometries.findIntersections(new Ray(new Point(0, 0, 3), new Vector(0.5, 0.5, 1))),
                "Bad geometries intersection");
        // TC02: The Ray intersects with one of the geometries(1 point)
        assertEquals(List.of(new Point(2, 0, 0)),
                geometries.findIntersections(new Ray(new Point(0, 0, 1), new Vector(2, 0, -1))),
                "Wrong points,bad intersection");
        // TC03: The Ray intersects with all of the geometries(5 point)
        result = geometries.findIntersections(new Ray(new Point(-3, -3, -3), new Vector(1, 1, 1)));
        assertEquals(5, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(0.9230769230769225,0.9230769230769225,0.9230769230769225), new Point(1.1547005383792524,1.1547005383792524,1.1547005383792524), new Point(-1.1547005383792508,-1.1547005383792508,-1.1547005383792508),
                new Point(0.3333333333333339,0.3333333333333339,0.3333333333333339), new Point(0, 0, 0)), result, "Wrong points,bad intersection");
        // TC04: Empty collection
        geometries = new Geometries();
        assertNull(geometries.findIntersections(new Ray(new Point(0, 0, -1), new Vector(1, 0, 1))),
                "Bad geometries intersection");
    }
}
