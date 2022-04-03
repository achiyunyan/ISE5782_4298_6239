package unitests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class PolygonTests {

	/**
	 * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		try {
			new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		} catch (IllegalArgumentException e) {
			fail("Failed constructing a correct polygon");
		}

		// TC02: Wrong vertices order
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
				"Constructed a polygon with wrong order of vertices");

		// TC03: Not in the same plane
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
				"Constructed a polygon with vertices that are not in the same plane");

		// TC04: Concave quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0.5, 0.25, 0.5)), //
				"Constructed a concave polygon");

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
				"Constructed a polygon with vertix on a side");

		// TC11: Last point = first point
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				"Constructed a polygon with vertice on a side");

		// TC12: Co-located points
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
				"Constructed a polygon with vertice on a side");

	}

	/**
	 * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
	}

	@Test
	void testFindIntsersections() {
		// ============ Equivalence Partitions Tests ==============
		// only if the ray intersects with the plane :

		Polygon polygon = new Polygon(new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 0, 0), new Point(0, -1, 0));
		Plane plane = new Plane(new Point(1, 0, 0), new Point(-1, 0, 0), new Point(0, 1, 0));

		// TC01: The Ray intersects with the triangle
		assertEquals(List.of(new Point(0, 0, 0)),
				polygon.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0, 0, 1))),
				"Bad polygon intersection");
		// TC02: The Ray intersects with the plane but not with the triangle(between two
		// lines)
		assertEquals(List.of(new Point(2, 0, 0)),
				plane.findIntersections(new Ray(new Point(0, 0, -2), new Vector(2, 0, 2))),
				"Must be intersection with the plane");
		assertNull(polygon.findIntersections(new Ray(new Point(0, 0, -2), new Vector(2, 0, 2))),
				"Bad triangle intersection");
		// TC03: The Ray intersects with the plane but not with the triangle(not between
		// two lines)
		assertEquals(List.of(new Point(0.5, 1.5, 0)),
				plane.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0.5, 1.5, 1))),
				"Must be intersection with the plane");
		assertNull(polygon.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0.5, 1.5, 1))),
				"Bad triangle intersection");
		// =============== Boundary Values Tests ==================

		// TC01: The Ray intersects with the plane and on an edge of the polygon
		assertNull(polygon.findIntersections(new Ray(new Point(0, 0, -1), new Vector(1, 0, 1))),
				"Bad triangle intersection");
		// TC02: The Ray intersects with the plane and on the side of the polygon
		assertNull(polygon.findIntersections(new Ray(new Point(0, 0, -1), new Vector(0.5, 0.5, 1))),
				"Bad triangle intersection");
		// TC03: The Ray intersects with the plane but not with the polygon(on a line)
		assertEquals(List.of(new Point(2, 1, 0)),
				plane.findIntersections(new Ray(new Point(0, 0, -1), new Vector(2, 1, 1))),
				"Must be intersection with the plane");
		assertNull(polygon.findIntersections(new Ray(new Point(0, 0, -1), new Vector(2, 1, 1))),
				"Bad triangle intersection");
	}
}