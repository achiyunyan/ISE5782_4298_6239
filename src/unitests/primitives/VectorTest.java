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
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Vector v1 = new Vector(1, 1, 1);
        assertEquals(v1.add(new Vector(1, -1, 0)), new Vector(2, 0, 1), "Vector Wrong Subtraction!");

        // =============== Boundary Values Tests ==================

        // TC02: Zero
        Vector v2 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class,
                () -> v2.add(new Vector(-1, -2, -3)),
                "Vector Zero!");
    }

    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    public void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Vector v = new Vector(1, 1, 1);
        assertEquals(v.subtract(new Vector(1, -1, 0)), new Vector(0, 2, 1), "Vector Wrong Subtraction!");

        // =============== Boundary Values Tests ==================

        // TC02: Zero
        Vector v2 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> v2.subtract(new Vector(1, 2, 3)), "Vector Zero!");
    }

    /**
     * Test method for {@link primitives.Vector#scale(primitives.Vector)}.
     */
    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Positive Scalar
        Vector v1 = new Vector(1, 1, 1);
        assertEquals(v1.scale(2), new Vector(2, 2, 2), "Vector Wrong Scale!");

        // TC02: Negative Scalar
        Vector v2 = new Vector(3, 2, 1);
        assertEquals(v2.scale(-2), new Vector(-6, -4, -2), "Vector Wrong Scale!");

        // =============== Boundary Values Tests ==================

        // TC03: Zero
        Vector v3 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> v3.scale(0), "Can't scale by Zero!");
    }

    @Test
    void testDotProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Zero
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(v1.dotProduct(new Vector(0, 3, -2)), 0, "Vector Wrong Dot Product!");

        // TC02: There is a simple single test here
        Vector v2 = new Vector(1, 2, 3);
        assertEquals(v2.dotProduct(new Vector(-2, -4, -6)), -28, "Vector Wrong Dot Product!");
    }

    @Test
    void testCrossProduct() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Checks if the length of the Cross-Product Vector equals to the summary
        // of its Vector's lengths.
        Vector v1 = new Vector(1, 2, 3);
        double number = v1.crossProduct(new Vector(0, 3, -2)).length() - v1.length() * new Vector(0, 3, -2).length();
        assertEquals(Util.isZero(number), true,
                "Vector Wrong Cross Product!");

        // =============== Boundary Values Tests ==================

        // TC02: Zero
        Vector v2 = new Vector(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> v2.crossProduct(new Vector(-2, -4, -6)), "Vector Zero!");
    }

    /**
     * Test method for {@link primitives.Vector#lengthSquared(primitives.Vector)}.
     */
    @Test
    public void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Vector v = new Vector(1, 2, 3);
        assertEquals(v.lengthSquared(), 14, "Vector Wrong Length Squared!");
    }

    /**
     * Test method for {@link primitives.Vector#length(primitives.Vector)}.
     */
    @Test
    public void testLength() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Positive Length
        Vector v1 = new Vector(0, 3, 4);
        assertEquals(v1.length(), 5, "Vector Wrong Length!");
    }

    @Test
    void testNormalize() {
        Vector v = new Vector(0, 3, 4);
        Vector n = v.normalize();
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(1d, n.lengthSquared(), 0.00001, "wrong normalized vector length");
        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(n), //
                "normalized vector is not in the same direction");
        assertEquals(new Vector(0, 0.6, 0.8), n, "wrong normalized vector");

    }
}