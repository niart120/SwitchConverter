package main;

public class Command {
	public static final int Y = 0x0001;
	public static final int B = 0x0002;
	public static final int A = 0x0004;
	public static final int X = 0x0008;

	public static final int L = 0x0010;
	public static final int R = 0x0020;
	public static final int ZL = 0x0040;
	public static final int ZR = 0x0080;

	public static final int MINUS = 0x0100;
	public static final int PLUS = 0x0200;

	public static final int LS = 0x0400;
	public static final int RS = 0x0800;
	public static final int HOME = 0x1000;
	public static final int CAP = 0x2000;

	public static final int UP = 0x00;
	public static final int UPRIGHT = 0x01;
	public static final int RIGHT = 0x02;
	public static final int DOWNRIGHT = 0x03;
	public static final int DOWN = 0x04;
	public static final int DOWNLEFT = 0x05;
	public static final int LEFT = 0x06;
	public static final int UPLEFT = 0x07;
	public static final int CENTER = 0x08;

	public static final int SMIN = 0;
	public static final int SCENTER = 128;
	public static final int SMAX = 255;

	int[] cmd = {0x0000,CENTER,SCENTER,SCENTER,SCENTER,SCENTER};

	public Command() {

	}

	public void pressButton(int key) {
		cmd[0] |= key;
	}

	public void releaseButton(int key) {
		cmd[0] &= (0xFFFF^key);
	}

	public void moveHat(int hat) {
		cmd[1] = hat;
	}

	public void moveLeftStick(int lx, int ly) {
		cmd[2]=lx;
		cmd[3]=ly;
	}

	public void moveLeftStickX(int lx) {
		cmd[2]=lx;
	}

	public void moveLeftStickY(int ly) {
		cmd[3]=ly;
	}

	public void moveRightStick(int rx, int ry) {
		cmd[4]=rx;
		cmd[5]=ry;
	}

}
