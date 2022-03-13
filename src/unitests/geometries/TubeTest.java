package unitests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

public class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(geometries.Tube)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Normal Normal
        Tube t1 = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 3);
        assertEquals(t1.getNormal(new Point(0, 3, 3)), new Vector(0, 0, 3), "Tube Wrong Normal!");
        assertEquals(t1.getNormal(new Point(0, 3, 3)).length(), 1, "Tube Wrong Normal!");
        
        // =============== Boundary Values Tests ==================

        // TC02: The Point is in front of the head of the Ray
        Tube t2 = new Tube(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 3);
        assertEquals(t2.getNormal(new Point(0, 3, 0)), new Vector(0, 3, 0), "Tube Wrong Normal!");
        assertEquals(t2.getNormal(new Point(0, 3, 0)).length(), 1, "Tube Wrong Normal!");
    }
}