import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Util {
    public static GraphicsConfiguration config;
    public static BufferedImage CHICKEN, BAT, NEST, CHICKEN_RESIZE, MASSIVE_BAT, EGG, MASSIVE_CHICKEN, MASSIVE_EGG,
            MASSIVE_CHICKEN_RESIZE, WOODPECKER, WOODPECKER_RESIZE, END_GAME, CROW, ROCK, CROW_RESIZE,BIRD_DEFENSE;

    static{
        config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        CHICKEN = getBufferedImage("chicken.png");
        BAT = getBufferedImage("bat.png");
        NEST = getBufferedImage("nest.png");
        END_GAME = getBufferedImage("endgame.png");
        BIRD_DEFENSE = getBufferedImage("bird_defense.png");
        CHICKEN_RESIZE = resize(CHICKEN, 85, 85);
        MASSIVE_BAT = resize(BAT, 200, 100);
        EGG = getBufferedImage("egg.png");
        MASSIVE_CHICKEN = resize(CHICKEN, 204, 204);
        MASSIVE_EGG = resize(EGG, 104, 104);
        MASSIVE_CHICKEN_RESIZE = resize(CHICKEN, 125, 125);
        WOODPECKER = getBufferedImage("woodpecker.png");
        WOODPECKER_RESIZE = resize(WOODPECKER, 85, 85);
        CROW = getBufferedImage("crow.png");
        ROCK = getBufferedImage("rock.png");
        CROW_RESIZE = resize(CROW, 85, 85);
    }
    public static BufferedImage getBufferedImage(String path){
        BufferedImage image = null;
        try{
            image = ImageIO.read(Util.class.getResource(path));
        }
        catch(Exception e){
            System.out.println(e);
        }

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
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    public static boolean withinBounds(double num, double min, double max){
        return num >= min && num <= max;
    }
    public static boolean withinBounds(Vector vec, Vector topLeft, Vector bottomRight){
        return withinBounds(vec.x, topLeft.x, bottomRight.x) && withinBounds(vec.y, topLeft.y, bottomRight.y);
    }
}
