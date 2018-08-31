package com.sthiec.nettankwar;

import java.awt.*;

public class Explode extends Livable {
	
	private static final Image[] images = {
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode01.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode02.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode03.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode04.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode05.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode06.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode07.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode08.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode09.gif")),
		tk.getImage(Explode.class.getClassLoader().getResource("images/explode10.gif"))
	};
	private static final int[] diameter = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 6, 3, 0 };
	
	private int index = -1;
	private Image image;
	
	public Explode(TankClient tc, int x, int y) {
		super(tc, x, y);
		image = images[0];
	}
	
	@Override
	public void move() {
		if (++index >= diameter.length) dead();
		else image = images[diameter[index]];
	}

	@Override
	public void draw(Graphics g) {
		drawImage(g, image);
	}
}
