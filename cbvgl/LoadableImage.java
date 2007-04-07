package cbvgl;

import java.awt.Dimension;
import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LoadableImage {
	private String imageFile;
	private Image i = null;
	private InputStream stream;
	
	public LoadableImage(String file) {
		imageFile = file;
	}
	
	public LoadableImage(String name, InputStream s) {
		imageFile = name;
		stream = s;
	}
	
	public void draw() {
		if (i == null) {
			load();
		}
		i.draw();
	}
	
	public Image getImage() {
		if (i == null) {
			load();
		}
		return i;
	}
	
	public void load() {
		if (i == null) {
			System.out.println("Loading Image " + imageFile);
			try {
				if (stream != null) {
					i = new Image(stream, imageFile, true);
				} else {
					i = new Image(imageFile);
				}
				if ((float)i.getHeight() * (800f/(float)i.getWidth()) <= 1280) {
					System.out.println("Scaling by width: " + 800f/(float)i.getWidth());
					i = i.getScaledCopy(800f/(float)i.getWidth());
				} else if ((float)i.getWidth() * (1280f/(float)i.getHeight()) <= 800) {
					System.out.println("Scaling by height: " + 1280f/(float)i.getHeight());
					i = i.getScaledCopy(1280f/(float)i.getHeight());
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
}
