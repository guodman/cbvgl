package cbvgl;

import java.io.InputStream;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;

public class LoadableImage implements Comparable<LoadableImage> {
	private final float resWidth;
	private final float resHeight;
	private String imageFile;
	private Image i = null;
	private InputStream stream;
	private boolean splitPage = false;

	public LoadableImage(String file) {
		imageFile = file;
		resHeight = Core.resHeight;
		resWidth = Core.resWidth;
	}

	public LoadableImage(String name, InputStream s) {
		imageFile = name;
		stream = s;
		resHeight = Core.resHeight;
		resWidth = Core.resWidth;
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

	public void cleanMem() {
		try {
			if (i != null) {
				GraphicsFactory.releaseGraphicsForImage(i);
				i = null;
			}
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
				float proposedHeight = (float) i.getHeight()
						* (resHeight / (float) i.getWidth());
				if (proposedHeight <= resWidth && proposedHeight > resWidth / 2) {
					System.out.println("Scaling by width: " + resHeight
							/ (float) i.getWidth());
					i = i.getScaledCopy(resHeight / (float) i.getWidth());
				} else if ((float) i.getWidth()
						* (resWidth / (float) i.getHeight()) <= resHeight) {
					System.out.println("Scaling by height: " + resWidth
							/ (float) i.getHeight());
					i = i.getScaledCopy(resWidth / (float) i.getHeight());
				} else if (proposedHeight <= resWidth / 2) {
					System.out.println("Scaling by width x2: " + resHeight
							/ (float) (i.getWidth() / 2));
					i = i.getScaledCopy(resHeight / (float) (i.getWidth() / 2));
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
