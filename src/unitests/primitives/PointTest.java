package unitests.primitives;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import primitives.*;

/**
 * Unit tests for primitives.Point class
 * 
 * @author Achiya Yunyan & Harel Dar
 */

public class PointTest {
    /**
     * Test method for {@link primitives.Point#add(primitives.Point)}.
     */
    @Test
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Point p1 = new Point(1, 1, 1);
        assertEquals(p1.add(new Vector(1, -1, 0)), new Point(2, 0, 1), "Point Wrong Addition!");

        // =============== Boundary Values Tests ==================

        // TC02: Zero
        Point p2 = new Point(1, 2, 3);
        assertEquals(p2.add(new Vector(-1, -2, -3)), new Point(0, 0, 0), "Point Wrong Addition!");
    }

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Point p = new Point(1, 1, 1);
        assertEquals(p.subtract(new Point(1, -1, 0)), new Vector(0, 2, 1), "Point Wrong Subtraction!");

        // =============== Boundary Values Tests ==================

        // TC02: Zero
        Point p2 = new Point(2, 3, 4);
        assertEquals(p2.subtract(new Vector(1, 2, 3)), new Vector(1, 1, 1), "Point Wrong Subtraction!");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
     */
    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Point p1 = new Point(1, 1, 1);
        assertEquals(p1.distanceSquared(new Point(2, 2, 2)), 3, "Point Wrong Disnace Squared!");

        // =============== Boundary Values Tests ==================

        // TC02: Distance Squared between identical points
        Point p2 = new Point(0, 0, 0);
        assertEquals(p2.distanceSquared(new Point(0, 0, 0)), 0, "Point Wrong Disnace Squared!");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: There is a simple single test here
        Point p1 = new Point(1, 1, 1);
        assertEquals(p1.distance(new Point(2, 2, 2)), Math.sqrt(3), "Point Wrong Disnace!");

        // =============== Boundary Values Tests ==================

        // TC02: Distance between identical points
        Point p2 = new Point(0, 0, 0);
        assertEquals(p2.distance(new Point(0, 0, 0)), 0, "Point Wrong Disnace!");
    }
}