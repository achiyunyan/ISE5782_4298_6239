package unitests.renderer;

import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;

import lighting.AmbientLight;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.ReadXmlDomParser;
import scene.Scene;
import static java.awt.Color.*;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class RenderTests {

	/**
	 * Produce a scene with basic 3D model and render it into a png image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(255, 191, 191), //
						new Double3(1, 1, 1))) //
				.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
				new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up
																												// left
				new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down
																												// left
				new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down
																												// right
		Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setImageWriter(new ImageWriter("base render test", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));

		camera.renderImage();
		camera.printGrid(100, new Color(YELLOW));
		camera.writeToImage();
	}

	// For stage 6 - please disregard in stage 5
	/**
	 * Produce a scene with basic 3D model - including individual lights of the
	 * bodies and render it into a png image with a grid
	 */
	@Test
	public void basicRenderMultiColorTest() {
		Scene scene = new Scene("Test scene")//
				.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2))); //

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -100), 50),
				// up left
				new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
						.setEmission(new Color(GREEN)),
				// down left
				new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
						.setEmission(new Color(RED)),
				// down right
				new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
						.setEmission(new Color(BLUE)));

		Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500) //
				.setImageWriter(new ImageWriter("color render test", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));

		camera.renderImage();
		camera.printGrid(100, new Color(WHITE));
		camera.writeToImage();
	}

	/**
	 * Test for XML based scene - for bonus
	 */
	@Test
	public void basicRenderXml() {
		Scene scene = ReadXmlDomParser.parse("XML Test scene");

		Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPDistance(100) //
				.setVPSize(500, 500).setImageWriter(new ImageWriter("xml render test", 1000, 1000))
				.setRayTracer(new RayTracerBasic(scene));
		camera.renderImage();
		camera.printGrid(100, new Color(YELLOW));
		camera.writeToImage();
	}

	@Test
	void testRotateCamera() {
		Scene scene = new Scene("Test scene"); //

		Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
				.setEmission(new Color(BLUE).reduce(2)) //
				.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));

		scene.geometries.add(sphere);
		scene.lights.add(new SpotLight(new Color(800, 0, 0), new Point(50, 50, -25), new Vector(-1, -1, 0.5)).setKl(0.001).setKq(0.0001));
		scene.lights.add(new PointLight(new Color(0, 500, 0), new Point(-50, 50, -25)).setKl(0.001).setKq(0.0002));
		scene.lights.add(new DirectionalLight(new Color(350, 400, 0), new Vector(1, 1, -0.5)));

		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150) //
				.setVPDistance(1000)
				.RotateCamera(new Vector(0, 1, 0), 3);

		ImageWriter imageWriter = new ImageWriter("camera rotate test", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage(); //
	}
}
