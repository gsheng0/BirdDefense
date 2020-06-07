import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Util {
    public static GraphicsConfiguration config;
    static{
        config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }
    public static BufferedImage getBufferedImage(String path){
        BufferedImage image = null;
        try{
            image = ImageIO.read(Util.class.getResource(path));
        }
        catch(Exception e){}
        return image;
    }
    public static BufferedImage rotate(BufferedImage image, double angle){

        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = image.getWidth(), h = image.getHeight();
        int neww = (int)Math.floor(w*cos+h*sin), newh = (int) Math.floor(h * cos + w * sin);
        BufferedImage result = config.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();
        g.translate((neww - w) / 2, (newh - h) / 2);
        g.rotate(angle, w / 2.0, h / 2.0);
        g.drawRenderedImage(image, null);
        g.dispose();
        return result;
    }
    public static BufferedImage rotateDegrees(BufferedImage image, int ang){
        double angle = Math.toRadians(ang);
        angle += 90;
        return rotate(image, angle);
    }
}
