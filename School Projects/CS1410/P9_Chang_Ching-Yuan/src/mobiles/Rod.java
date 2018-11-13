package mobiles;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

/**
 * A Rod is a kind of Mobile. A Rod consists of a horizontal rod that has one
 * Mobile hanging from its left end and another Mobile hanging from its right
 * end. The rod is hanging from a vertical wire. The distance along the rod from
 * the vertical wire to the left end is called the left length, and the distance
 * from the vertical wire to the right end is called the right length.
 * 
 * Here's a diagram:
 * 
 * <pre>
 *                        _____|__________
 *                        |              |
 *                        L              R
 * </pre>
 * 
 * The left length is 5 and the right length is 10. L and R are the left and
 * right Mobiles, respectively.
 */
public class Rod implements Mobile {
	// The left and right lengths, and the left and right Mobiles.
	private int leftLength;
	private int rightLength;
	private Mobile left;
	private Mobile right;

	/**
	 * Creates a Rod of the given configuration.
	 */
	public Rod(int leftLength, int rightLength, Mobile left, Mobile right) {
		this.leftLength = leftLength;
		this.left = left;
		this.rightLength = rightLength;
		this.right = right;
	}

	/**
	 * Returns the left length of this Rod.
	 */
	public int getLeftLength() {
		return leftLength;
	}

	/**
	 * Returns the right length of this Rod.
	 */
	public int getRightLength() {
		return rightLength;
	}

	/**
	 * Returns the left Mobile of this Rod.
	 */
	public Mobile getLeft() {
		return left;
	}

	/**
	 * Returns the right Mobile of this Rod.
	 */
	public Mobile getRight() {
		return right;
	}

	/**
	 * Draws this Rod on g, beginning at point (x,y).
	 */
	@Override
	public void display(Graphics2D g, double x, double y) {
		// Get the widths of the labels
		FontMetrics fm = g.getFontMetrics();
		int leftLabelWidth = fm.stringWidth(leftLength + "");
		int rightLabelWidth = fm.stringWidth(rightLength + "");

		// Show the mobile askew according to the degree of imbalance
		double leftTorque = left.weight() * leftLength;
		double rightTorque = right.weight() * rightLength;
		double theta = (rightTorque - leftTorque) / 100 * Math.PI / 6;
		theta = Math.min(theta, Math.PI / 6);
		theta = Math.max(theta, -Math.PI / 6);

		// Draw the vertical wire
		g.draw(new Line2D.Double(x, y, x, y + WIRE));

		// Compute the endpoints of the rod
		double leftX = x - Math.cos(theta) * (leftLength * UNIT);
		double leftY = y + WIRE - Math.sin(theta) * (leftLength * UNIT);
		double rightX = x + Math.cos(theta) * (rightLength * UNIT);
		double rightY = y + WIRE + Math.sin(theta) * (rightLength * UNIT);

		// Compute the rotation
		AffineTransform at = new AffineTransform();
		at.translate(x, y + WIRE);
		at.rotate(theta);
		g.setTransform(at);

		// Draw the rod and display the text
		g.draw(new Line2D.Double(-leftLength * UNIT, 0, rightLength * UNIT, 0));
		g.drawString(leftLength + "", (float) (-leftLength * UNIT / 2 - leftLabelWidth / 2), (float) -GAP);
		g.drawString(rightLength + "", (float) (rightLength * UNIT / 2 - rightLabelWidth / 2), (float) -GAP);

		// Cancel the rotation
		at.setToRotation(0);
		g.setTransform(at);

		// Display the left and right Mobiles
		left.display(g, leftX, leftY);
		right.display(g, rightX, rightY);
	}

	@Override
	public int weight() {
		return left.weight() + right.weight();
	}

	@Override
	public boolean isBalanced() {
		if (leftLength * left.weight() == rightLength * right.weight() && left.isBalanced()&&right.isBalanced()) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
	}

	@Override
	public int depth() {
		return 1 + Math.max(left.depth(), right.depth());
	}

	@Override
	public int bobCount() {
		return left.bobCount()+right.bobCount();
	}

	@Override
	public int rodCount() {
		return 1 + (left.rodCount()+right.rodCount());
	}

	@Override
	public int longestRod() {
		return Math.max(leftLength, rightLength);
	}

	@Override
	public int heaviestBob() {
		return Math.max(left.heaviestBob(), right.heaviestBob());
	}
}
