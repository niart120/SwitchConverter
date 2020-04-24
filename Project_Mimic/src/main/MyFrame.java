package main;

import javax.swing.JFrame;

public class MyFrame extends JFrame{

	/**
	 *
	 */
	private static final long serialVersionUID = -573964303682758677L;

	public MyFrame(String title){
		super(title);
		this.setSize(600, 600);
	    this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    this.add(new InputPanel());
		this.setVisible(true);
	}

}
