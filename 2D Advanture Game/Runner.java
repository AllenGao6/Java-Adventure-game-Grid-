import javax.swing.JFrame;

public class Runner
{
    public static void main(String [] args)
    {
    /*
        Screen game = new Screen();
		JFrame frame = new JFrame("Screen");
		
		frame.add(game);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);*/
        
        JFrame f = new JFrame();
        f.getContentPane().add(new Screen());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
        Screen a = new Screen();
        a.animate();

    }



}