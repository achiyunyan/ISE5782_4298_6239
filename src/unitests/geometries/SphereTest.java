package unitests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

public class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#getNormal(geometries.Sphere)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Normal Normal
        Sphere s1 = new Sphere(new Point(0, 0, 0), 3);
        assertEquals(s1.getNormal(new Point(0, 0, 3)), new Vector(0, 0, 3), "Sphere Wrong Normal!");
        assertEquals(s1.getNormal(new Point(0, 0, 3)).length(), 1, "Sphere Wrong Normal!");
    }
}