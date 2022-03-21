package unitests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

public class CylinderTest {
    /**
     * Test method for {@link geometries.Cylinder#getNormal(geometries.Cylinder)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Side Normal
        Cylinder t1 = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 3, 5);
        assertEquals(t1.getNormal(new Point(0, 3, 3)), new Vector(0, 1, 0), "Tube Wrong Normal!");
        assertEquals(t1.getNormal(new Point(0, 3, 3)).length(), 1, "Tube Wrong Normal!");

        // TC02 : Base1 Normal
        assertEquals(t1.getNormal(new Point(0, 2, 5)), t1.getAxisRay().getDir(), "Tube Wrong Normal!");
        assertEquals(t1.getNormal(new Point(0, 2, 5)).length(), 1, "Tube Wrong Normal!");

        // TC03 : Base2 Normal
        assertEquals(t1.getNormal(new Point(0, 2, 0)), t1.getAxisRay().getDir().scale(-1), "Tube Wrong Normal!");
        assertEquals(t1.getNormal(new Point(0, 2, 0)).length(), 1, "Tube Wrong Normal!");

        // =============== Boundary Values Tests ==================

        // TC04 : Base 1 center
        assertEquals(t1.getNormal(new Point(0, 0, 5)), t1.getAxisRay().getDir(), "Tube Wrong Normal!");
        assertEquals(t1.getNormal(new Point(0, 0, 5)).length(), 1, "Tube Wrong Normal!");

        // TC05 : Base 2 center
        assertEquals(t1.getNormal(new Point(0, 0, 0)), t1.getAxisRay().getDir().scale(-1), "Tube Wrong Normal!");
        assertEquals(t1.getNormal(new Point(0, 0, 0)).length(), 1, "Tube Wrong Normal!");

    }
}