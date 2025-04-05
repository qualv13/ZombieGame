package lab13;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class CrossHair implements MouseMotionListener, MouseListener {

    private final DrawPanel parent;
    private final List<CrossHairListener> listeners = new ArrayList<CrossHairListener>();
    private int x; // x-coordinate of the crosshair
    private int y; // y-coordinate of the crosshair
    private boolean activated = false; // Whether the crosshair is "active" after a shot
    private final Timer timer = new Timer("CrossHairTimer");

    public CrossHair(DrawPanel parent) {
        this.parent = parent;
        parent.addMouseMotionListener(this);
        parent.addMouseListener(this);
    }

    // Method to add listeners for shot events
    public void addCrossHairListener(CrossHairListener listener) {
        listeners.add(listener);
    }

    // Notify all listeners when a shot is fired
    private void notifyListeners() {
        for (var listener : listeners) {
            listener.onShotsFired(x, y);
        }
    }

    // Draw the crosshair
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (activated) {
            g2d.setColor(Color.RED);
        } else {
            g2d.setColor(Color.WHITE);
        }

        // Draw crosshair lines
        int size = 20; // Length of crosshair lines
        g2d.drawLine(x - size, y, x + size, y); // Horizontal line
        g2d.drawLine(x, y - size, x, y + size); // Vertical line
    }

    // MouseMotionListener: Update the crosshair position when the mouse moves
    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        parent.repaint(); // Trigger repaint to update the crosshair position
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Handle mouse drag as movement
        mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    // MouseListener: Handle shooting when the mouse is clicked
    @Override
    public void mousePressed(MouseEvent e) {
        activated = true;
        parent.repaint(); // Update the crosshair color to red
        notifyListeners(); // Notify listeners of the shot

        // Schedule deactivation of the crosshair after 300ms
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                activated = false;
                parent.repaint();
            }
        }, 300);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}