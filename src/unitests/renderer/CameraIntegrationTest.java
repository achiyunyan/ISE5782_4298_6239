package unitests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;
import renderer.*;

public class CameraIntegrationTest {
    int SumIntersections(Geometry geometry, Camera camera)
    {
        int sum = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
            {
                List<Point> l = geometry.findIntersections(camera.constructRay(3, 3, i, j));
                if (l != null)
                    sum += l.size();
            }
        return sum;
    }

    @Test
    void CameraSphereIntersections() {
        // TC01: Sphere is In Front of the View Plane (2 Intersections)
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1))
                .setVPSize(3, 3).setVPDistance(1);
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(2, SumIntersections(sphere, camera), "Wrong Amount of Intersections!");

        // TC02: View Plane is in the Sphere (All Rays Intersect 2 times)
        camera = new Camera(new Point(0, 0, 0.5), new Vector(0, 1, 0), new Vector(0, 0, -1))
                .setVPSize(3, 3).setVPDistance(1);
        sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(18, SumIntersections(sphere, camera), "Wrong Amount of Intersections!");

        // TC03: Part of the View Plane is in the Sphere
        sphere = new Sphere(new Point(0, 0, -2), 2);
        assertEquals(10, SumIntersections(sphere, camera), "Wrong Amount of Intersections!");

        // TC04: View Plane & Camera is in the Sphere (All Rays Intersect 1 time)
        sphere = new Sphere(new Point(0, -2, 0), 4);
        assertEquals(9, SumIntersections(sphere, camera), "Wrong Amount of Intersections!");

        // TC05: View Plane is behind the Camera
        sphere = new Sphere(new Point(0, 2, 0), 0.5);
        assertEquals(0, SumIntersections(sphere, camera), "Wrong Amount of Intersections!");
    }

    @Test
    void CameraPlaneIntersections() {
        // TC01: Plane is in front of the View Plane (Paralel)
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVPSize(3, 3).setVPDistance(1);
        Plane plane = new Plane(new Point(0, -3, 0), new Vector(0, -1, 0));
        assertEquals(9, SumIntersections(plane, camera), "Wrong Amount of Intersections!");

        // TC02: Plane is in front of the View Plane (Not Paralel)
        camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVPSize(3, 3).setVPDistance(1);
        plane = new Plane(new Point(0, -3, 0), new Vector(-0.5, -1, 0));
        assertEquals(9, SumIntersections(plane, camera), "Wrong Amount of Intersections!");

        // TC03: Plane is in front of the View Plane (Not Paralel but Intersect with the
        // View Plane)
        camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVPSize(3, 3).setVPDistance(1);
        plane = new Plane(new Point(0, -3, 0), new Vector(-1, -1, 0));
        assertEquals(6, SumIntersections(plane, camera), "Wrong Amount of Intersections!");
    }

    @Test
    void CameraTriangleIntersections() {
        // TC01:
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, -1))
                .setVPSize(3, 3).setVPDistance(1);
        Triangle triangle = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(1, SumIntersections(triangle, camera), "Wrong Amount of Intersections!");

        // TC02:
        triangle = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        assertEquals(2, SumIntersections(triangle, camera), "Wrong Amount of Intersections!");
    }
}