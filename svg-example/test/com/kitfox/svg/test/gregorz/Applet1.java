package com.kitfox.svg.test.gregorz;

import java.awt.Graphics;

import javax.swing.JApplet;

public class Applet1 extends JApplet{

	@Override
	public void init() {
		setSize(500,500);
		TestPanel1 tP1 = new TestPanel1();
		tP1.setSize(500, 500);
		add(tP1);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	
}
