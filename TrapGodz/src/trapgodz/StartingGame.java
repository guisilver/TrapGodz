package trapgodz;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import trapgoz.framework.Animations;

@SuppressWarnings("serial")
public class StartingGame extends Applet implements Runnable, KeyListener {

	private Hero hero;
	private Heliboy hb, hb2;
	private Image image, character, character2, character3, charwalk1,
			charwalk2, characterDown, characterJumped, background,
			currentSprite, heliboy, heliboy2, heliboy3, heliboy4, heliboy5;;
	private Animations anim, anim2, hanim;
	private URL base;
	private Graphics second;
	private static Background bg1, bg2;

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

	@Override
	public void init() {
		setSize(1024, 720);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Trap God");
		try {
			base = getDocumentBase();
		} catch (Exception e) {
		}

		character = getImage(base, "../resources/images/character.png");
		character2 = getImage(base, "../resources/images/character2.png");
		character3 = getImage(base, "../resources/images/character3.png");

		charwalk1 = getImage(base, "../resources/images/characterwalk1.png");
		charwalk2 = getImage(base, "../resources/images/characterwalk2.png");

		characterDown = getImage(base, "../resources/images/down.png");
		characterJumped = getImage(base, "../resources/images/jumped.png");

		heliboy = getImage(base, "../resources/images/heliboy.png");
		heliboy2 = getImage(base, "../resources/images/heliboy2.png");
		heliboy3 = getImage(base, "../resources/images/heliboy3.png");
		heliboy4 = getImage(base, "../resources/images/heliboy4.png");
		heliboy5 = getImage(base, "../resources/images/heliboy5.png");

		background = getImage(base, "../resources/images/background.png");

		anim = new Animations();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);

		hanim = new Animations();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		anim2 = new Animations();
		anim2.addFrame(charwalk1, 500);
		anim2.addFrame(charwalk2, 500);

		currentSprite = anim.getImage();
		System.out.println("Teste");
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		hero = new Hero();
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void stop() {
		// super.stop();
	}

	@Override
	public void destroy() {
		// super.destroy();
	}

	@Override
	public void run() {
		while (true) {
			hero.update();
			if (hero.isJumped()) {
				currentSprite = characterJumped;
			} else if (hero.isJumped() == false && hero.isDucked() == false
					&& hero.isMovingRight() == false) {
				currentSprite = anim.getImage();
			} else if (hero.isMovingRight() == true) {
				currentSprite = anim2.getImage();
			}
			hb.update();
			hb2.update();
			bg1.update();
			bg2.update();

			animate();
			repaint();
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void animate() {
		anim.update(10);
		anim2.update(50);
		hanim.update(50);
	}

	public void update(Graphics g) {
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);
		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		g.drawImage(currentSprite, hero.getCenterX() + 61,
				hero.getCenterY() + 63, this);
		g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
				hb.getCenterY() - 48, this);
		g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
				hb2.getCenterY() - 48, this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;
		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if (hero.isJumped() == false) {
				hero.setDucked(true);
				hero.setSpeedX(0);
			}
			break;

		case KeyEvent.VK_LEFT:
			hero.moveLeft();
			hero.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			hero.moveRight();
			hero.setMovingRight(true);
			break;

		case KeyEvent.VK_SPACE:
			hero.jump();
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = anim.getImage();
			hero.setDucked(false);
			break;

		case KeyEvent.VK_LEFT:
			hero.setMovingLeft(false);
			hero.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			hero.setMovingRight(false);
			hero.stopRight();
			break;

		case KeyEvent.VK_SPACE:
			break;

		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
}
