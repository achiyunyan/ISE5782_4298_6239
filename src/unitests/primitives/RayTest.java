package unitests.primitives;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import primitives.*;

public class RayTest {
    /**
     * Test method for {@link primitives.Ray#findClosestPoint(primitives.Ray)}.
     */
    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0,0,0), new Vector(1,1,1));

        // ============ Equivalence Partitions Tests ==============

        // TC01: The closest Point is the middle one
        List<Point> points = List.of(new Point(-1,-1,-1), new Point(0,0,0.5), new Point(1,1,1));
        assertEquals(new Point(0,0,0.5), ray.findClosestPoint(points), "Wrong Closest Point!");

        // =============== Boundary Values Tests ==================

        // TC02: list is null
        assertEquals(null, ray.findClosestPoint(null), "Wrong Closest Point!");

        // TC03: The closest Point is the first one
        points = List.of(new Point(0,0,0.5), new Point(-1,-1,-1), new Point(1,1,1));
        assertEquals(new Point(0,0,0.5), ray.findClosestPoint(points), "Wrong Closest Point!");

        // TC04: The closest Point is the last one
        points = List.of(new Point(-1,-1,-1), new Point(1,1,1), new Point(0,0,0.5));
        assertEquals(new Point(0,0,0.5), ray.findClosestPoint(points), "Wrong Closest Point!");
    }
}