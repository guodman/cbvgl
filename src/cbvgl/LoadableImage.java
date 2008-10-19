package cbvgl;

import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class LoadableImage implements Comparable<LoadableImage> {
	private String imageFile;
	private Image i = null;
	private InputStream stream;
	private boolean splitPage = false;
	
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
	
	public String getName() {
		return imageFile;
	}
	
	public boolean isSplit() {
		return splitPage;
	}
	
	public void load() {
		if (i == null) {
			System.out.println("Loading Image " + imageFile);
			try {
				if (stream != null) {
					i = new Image(stream, imageFile, false);
				} else {
					i = new Image(imageFile);
				}
				float proposedHeight = (float)i.getHeight() * (800f/(float)i.getWidth());
				if (proposedHeight <= 1280 && proposedHeight > 1280/2) {
					System.out.println("Scaling by width: " + 800f/(float)i.getWidth());
					i = i.getScaledCopy(800f/(float)i.getWidth());
				} else if ((float)i.getWidth() * (1280f/(float)i.getHeight()) <= 800) {
					System.out.println("Scaling by height: " + 1280f/(float)i.getHeight());
					i = i.getScaledCopy(1280f/(float)i.getHeight());
				} else if (proposedHeight <= 1280/2) {
					System.out.println("Scaling by width x2: " + 800f/(float)(i.getWidth()/2));
					i = i.getScaledCopy(800f/(float)(i.getWidth()/2));
					splitPage = true;
				}
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

	public int compareTo(LoadableImage o) {
		return imageFile.compareTo(o.getName());
	}
}
