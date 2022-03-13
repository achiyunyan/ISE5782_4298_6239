package unitests.geometries;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

public class PlaneTest {
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Plane p = new Plane(new Point(0, 0, 0), new Vector(0, 0, 1));
        
    }
}