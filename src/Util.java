import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
        double rotationRequired = angle;
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        return op.filter(image, null);
    }
    public static BufferedImage rotateDegrees(BufferedImage image, int ang){

        double angle = Math.toRadians(ang);
        return rotate(image, angle);
    }
}
