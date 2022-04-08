package unitests.renderer;

import org.junit.jupiter.api.Test;

import primitives.*;
import renderer.*;

public class ImageWriterTest {
    @Test
    void testWriteToImage() {
        ImageWriter iw = new ImageWriter("TC01", 800, 500);
        for (int i = 0; i < iw.getNx(); i++)
            for (int j = 0; j < iw.getNy(); j++)
            {
                if (i % 50 == 0 || j % 50 == 0)
                    iw.writePixel(i, j, new Color(255, 0, 0));
                else
                    iw.writePixel(i, j, new Color(255, 255, 0));
            }
        iw.writeToImage();        
    }
}