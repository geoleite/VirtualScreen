import javax.swing.event.ChangeListener;
   import javax.swing.event.ChangeEvent;
   import java.awt.image.BufferedImage;
   import java.awt.image.AffineTransformOp;
   import java.awt.geom.AffineTransform;

   public class ScaleExample implements 
           ChangeListener {
     ExampleFramework framework;
     private BufferedImage original;

     ScaleExample() {
       framework = new ExampleFramework("Scale");
       framework.setChangeListener(this);
       original = framework.getOriginal();
       setScaleFactor(1.0);
     }

     public void stateChanged(ChangeEvent changeEvent) {
       setScaleFactor((double) 
               (framework.getSlideValue())/25);
     }

     public void setScaleFactor(double multiple) {
         System.out.println("multiple " + multiple);
       AffineTransformOp op = new AffineTransformOp(
         AffineTransform.getScaleInstance(multiple,
                 multiple),
                 AffineTransformOp.TYPE_BILINEAR);
       BufferedImage tempImage = 
               op.filter(original, null);
       framework.setFilteredImage(tempImage);
     }

     public static void main(String[] args) {
       new ScaleExample();
       
     }
   }
