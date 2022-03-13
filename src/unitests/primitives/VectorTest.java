package unitests.primitives;

import primitives.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for primitives.Vector class
 * 
 * @author Yunyan & Dar
 */
public class VectorTest {
    /**
     * Test method for {@link primitives.Vector#add(primitives.Point)}.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Vector p = new Vector(1, 1, 1);
        assertEquals(p.add(new Vector(1, -1, 0)), new Vector(2, 0, 1), "Vector Wrong Subtraction!");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Point)}.
     */
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Vector p = new Vector(1, 1, 1);
        assertEquals(p.subtract(new Vector(1, -1, 0)), new Vector(0, 2, 1), "Vector Wrong Subtraction!");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared(primitives.Point)}.
     */
    @Test
    public void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Vector v = new Vector(0, 0, 1);
        assertEquals(v.lengthSquared(), 1, "Vector Wrong Length Squared!");
    }

    /**
     * Test method for {@link primitives.Vector#length(primitives.Point)}.
     */
    @Test
    public void testLength() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Vector v = new Vector(0, 0, 1);
        assertEquals(v.length(), 1, "Vector Wrong Length!");
    }
}