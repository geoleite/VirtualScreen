import javax.swing.JFrame;
   import javax.swing.JPanel;
   import javax.swing.ImageIcon;
   import javax.swing.JSlider;
   import javax.swing.event.ChangeListener;
   import java.awt.Graphics;
   import java.awt.Image;
   import java.awt.BorderLayout;
   import java.awt.image.BufferedImage;

   public class ExampleFramework extends JPanel {
     private BufferedImage originalImage;
     private BufferedImage filteredImage;
     private JSlider slide = new JSlider(1, 50, 25);
     private int height, width;

     ExampleFramework(String titlebar) {
       createBufferedImages();
       setUpJFrame(titlebar);
     }

     private void createBufferedImages() {
       Image image = 
               new ImageIcon("IMAGEM.JPG").getImage();
       height = image.getHeight(null);
       width = image.getWidth(null);
       originalImage = 
               new BufferedImage(width, height,
               BufferedImage.TYPE_INT_RGB);
       filteredImage = new BufferedImage(width, height,
               BufferedImage.TYPE_INT_RGB);
       Graphics g = originalImage.createGraphics();
       g.drawImage(image, 0, 0, null);
       g.dispose();
     }

     private void setUpJFrame(String titlebar) {
       JFrame frame = new JFrame(titlebar);
       frame.setSize(filteredImage.getWidth(),
               filteredImage.getHeight());
       frame.getContentPane().setLayout
               (new BorderLayout());
       frame.getContentPane().add
               (this, BorderLayout.CENTER);
       frame.getContentPane().add
               (slide, BorderLayout.SOUTH);
       frame.setResizable(true);
       frame.setDefaultCloseOperation
               (JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
     }

     // accessor methods for plugging in filters

     public void setChangeListener(ChangeListener cl) {
       slide.addChangeListener(cl);
     }

     public void setFilteredImage
             (BufferedImage image) {
       filteredImage = image;
       repaint();
     }

     public int getSlideValue() {
       return slide.getValue();
     }

     public BufferedImage getOriginal() {
       return originalImage;
     }

     public void paintComponent(Graphics g) {
       g.clearRect(0, 0, width, height);
       g.drawImage(filteredImage, 0, 0, this);
     }
     public static void main(String[] param) {
         ExampleFramework ef = new ExampleFramework("teste");
         ef.setSize(400,400);
         ef.setVisible(true);
     }
   }
