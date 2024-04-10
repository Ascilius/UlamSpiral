import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class UlamSpiralPanel extends JPanel {
	
	// screen stuff
	private int screenWidth, screenHeight;
	private double scale = 16;
	// input
	private InputHandler inputHandler;
	
	private ArrayList<Point> prime_points = new ArrayList<Point>(); // array holding prime points

	UlamSpiralPanel(double screenWidth, double screenHeight) {
		this.screenWidth = (int) screenWidth;
		this.screenHeight = (int) screenHeight;
		this.inputHandler = new InputHandler();
		addKeyListener(this.inputHandler);
		setFocusable(true);

		int x = 1, y = 0, i = 2, sideTimes = 1, sideLength = 1, sideTraversal = 0;
		int direction = 1; // 0 - right, 1 - up, 2 - left, 3 - down
		while (prime_points.size() < 128) {
			// prime checker
			boolean prime = true;
			for (int j = 2; j < i; j++) {
				if (i % j == 0) {
					prime = false;
					break;
				}
			}
			if (prime == true) {
				prime_points.add(new Point(x, y));
			}
			i++;

			// next point
			if (direction == 0) {
				x++;
			} else if (direction == 1) {
				y++;
			} else if (direction == 2) {
				x--;
			} else if (direction == 3) {
				y--;
			}
			sideTraversal++;
			if (sideTraversal == sideLength) {
				sideTimes++;
				sideTraversal = 0;
				if (sideTimes == 2) {
					sideLength++;
					sideTimes = 0;
				}
				direction++;
				if (direction == 4) {
					direction = 0;
				}
			}
		}
	}
	
	// drawing sprial
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenWidth, screenHeight);

		g.translate(screenWidth / 2, screenHeight / 2);

		g.setColor(Color.WHITE);
		for (Point prime_point : prime_points) {
			g.fillOval((int) (prime_point.getX() * scale) - 1, (int) (prime_point.getY() * scale) - 1, 2, 2);
		}
	}
	
	// zoom scale functions
	public void zoomIn() {
		scale *= 2;
		repaint();
	}

	public void zoomOut() {
		scale /= 2;
		repaint();
	}
	
	// generating more prime points
	public void more() {
		int newSize = prime_points.size() * 2;
		prime_points.clear(); // TOFIX: generate new primes without regenerating already generated primes
		int x = 1, y = 0, i = 2, sideTimes = 1, sideLength = 1, sideTraversal = 0;
		int direction = 1; // 0 - right, 1 - up, 2 - left, 3 - down
		while (prime_points.size() < newSize) {
			// prime checker
			boolean prime = true;
			for (int j = 2; j < i; j++) {
				if (i % j == 0) {
					prime = false;
					break;
				}
			}
			if (prime == true) {
				prime_points.add(new Point(x, y));
			}
			i++;
			
			// next point
			if (direction == 0) {
				x++;
			} else if (direction == 1) {
				y++;
			} else if (direction == 2) {
				x--;
			} else if (direction == 3) {
				y--;
			}
			sideTraversal++;
			if (sideTraversal == sideLength) {
				sideTimes++;
				sideTraversal = 0;
				if (sideTimes == 2) {
					sideLength++;
					sideTimes = 0;
				}
				direction++;
				if (direction == 4) {
					direction = 0;
				}
			}
		}
		repaint();
	}
	
	// trimming prime point array
	public void less() {
		int newSize = prime_points.size() / 2;
		while (prime_points.size() > newSize) {
			prime_points.remove(prime_points.size() - 1);
		}
		repaint();
	}
	
	// input control
	class InputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				zoomIn();
			} else if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
				zoomOut();
			} else if (e.getKeyCode() == KeyEvent.VK_PERIOD) {
				more();
			} else if (e.getKeyCode() == KeyEvent.VK_COMMA) {
				less();
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
		}

		public void keyTyped(KeyEvent e) {
		}
	}
}
