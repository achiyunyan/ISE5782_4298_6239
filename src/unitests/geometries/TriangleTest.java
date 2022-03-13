package unitests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}