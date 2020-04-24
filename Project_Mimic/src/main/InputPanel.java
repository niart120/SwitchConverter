package main;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class InputPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener, KeyListener{
	private Robot rob;
	private Point mousepos;
	private Timer t;
	Command cmd;
	SerialConnection sc;
	private boolean W=false,A=false,S=false,D=false;

	/**
	 *
	 */

	private static final long serialVersionUID = -2902945983105394353L;

	public InputPanel() {
		this.setCursor((new Cursor(Cursor.CROSSHAIR_CURSOR)));
		this.setFocusable(true);
		try {
			rob = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		this.cmd = new Command();
		this.sc = new SerialConnection(cmd);

		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);

		this.t = new Timer(50, this);
		this.sc.start();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		switch (e.getButton()) {
			case MouseEvent.BUTTON3://右クリック
				cmd.pressButton(Command.ZR);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		switch (e.getButton()) {
		case MouseEvent.BUTTON3://右クリック
			cmd.releaseButton(Command.ZR);
	}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Point p = this.getLocationOnScreen();
		p.translate(this.getWidth()/2,this.getHeight()/2);
		rob.mouseMove(p.x, p.y);
		t.start();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(t.isRunning()) {
			Point p = this.getLocationOnScreen();
			p.translate(this.getWidth()/2,this.getHeight()/2);
			rob.mouseMove(p.x, p.y);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousepos = e.getPoint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Point delta = new Point(mousepos.x-this.getWidth()/2,mousepos.y-this.getHeight()/2);
		double r = Math.hypot(delta.x, delta.y);

		if (r>127){
			double theta = Math.atan2(delta.y, delta.x);
			int tx = (int)(127.0*Math.cos(theta));
			int ty = (int)(127.0*Math.sin(theta));
			delta.move(tx, ty);
			delta.translate(128, 128);
		}else {
			delta.move(delta.x+128, delta.y+128);
		}
		cmd.moveRightStick(delta.x, delta.y);

//		System.out.printf("%d,%d\n", delta.x,delta.y);
		Point p = this.getLocationOnScreen();
		p.translate(this.getWidth()/2,this.getHeight()/2);
		rob.mouseMove(p.x, p.y);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_C :
            	if (e.isControlDown() && t.isRunning()) {
            		t.stop();
            	}
                break;

        	case KeyEvent.VK_F:
        		cmd.pressButton(Command.A);
        		break;

        	case KeyEvent.VK_V:
        		cmd.pressButton(Command.B);
        		break;

        	case KeyEvent.VK_R:
        		cmd.pressButton(Command.Y);
        		break;

        	case KeyEvent.VK_E:
        		cmd.pressButton(Command.X);
        		break;

        	case KeyEvent.VK_SHIFT:
        		cmd.pressButton(Command.ZL);
        		break;

        	case KeyEvent.VK_SPACE:
        		cmd.pressButton(Command.B);
        		break;

        	case KeyEvent.VK_ESCAPE:
        		cmd.pressButton(Command.HOME);
        		break;

            case KeyEvent.VK_W :
            	cmd.moveLeftStickY(Command.SMIN);
            	W=true;
                break;

            case KeyEvent.VK_A :
            	cmd.moveLeftStickX(Command.SMIN);
            	A=true;
                break;

            case KeyEvent.VK_S :
            	cmd.moveLeftStickY(Command.SMAX);
            	S=true;
                break;

            case KeyEvent.VK_D :
            	cmd.moveLeftStickX(Command.SMAX);
            	D=true;
                break;
        }
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
        switch (key) {
        	case KeyEvent.VK_F:
        		cmd.releaseButton(Command.A);
        		break;

        	case KeyEvent.VK_V:
        		cmd.releaseButton(Command.B);
        		break;

        	case KeyEvent.VK_R:
        		cmd.releaseButton(Command.Y);
        		break;

        	case KeyEvent.VK_E:
        		cmd.releaseButton(Command.X);
        		break;

        	case KeyEvent.VK_SHIFT:
        		cmd.releaseButton(Command.ZL);
        		break;

        	case KeyEvent.VK_SPACE:
        		cmd.releaseButton(Command.B);
        		break;

        	case KeyEvent.VK_ESCAPE:
        		cmd.releaseButton(Command.HOME);
        		break;

            case KeyEvent.VK_W :

	        	if (S==true) {
	        		cmd.moveLeftStickY(Command.SMAX);
	        	}else {
	        		cmd.moveLeftStickY(Command.SCENTER);
	        	}
            	W=false;
                break;

            case KeyEvent.VK_A :
            	if (D==true) {
            		cmd.moveLeftStickX(Command.SMAX);
            	}else {
            		cmd.moveLeftStickX(Command.SCENTER);
            	}
            	A=false;
                break;

            case KeyEvent.VK_S :
            	if (W==true) {
            		cmd.moveLeftStickY(Command.SMIN);
            	}else {
            		cmd.moveLeftStickY(Command.SCENTER);
            	}
            	S=false;
                break;

            case KeyEvent.VK_D :
            	if (A==true) {
            		cmd.moveLeftStickX(Command.SMIN);
            	}else {
            		cmd.moveLeftStickX(Command.SCENTER);
            	}
            	D=false;
                break;

        }
	}
}
