import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class UlamSpiralDriver {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Ulam Spiral");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		UlamSpiralPanel panel = new UlamSpiralPanel(screenSize.getWidth(), screenSize.getHeight());
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.setVisible(true);
	}
}
