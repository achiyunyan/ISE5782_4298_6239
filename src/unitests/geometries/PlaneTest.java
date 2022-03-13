package unitests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

public class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#getNormal(geometries.Plane)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Positive Normal
        Plane p1 = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(p1.getNormal(), new Vector(1, 0, 0), "Plane Wrong Normal!");

        // TC02: Negative Normal
        Plane p2 = new Plane(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
        assertEquals(p2.getNormal(), new Vector(-1, 0, 0), "Plane Wrong Normal!");
    }
}