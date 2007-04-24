package cbvgl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.newdawn.slick.Image;

public class ImageList {
	private List<LoadableImage> il;
	private String comicFile;
	private int curImage;
	public int splitStatus;
	
	public ImageList(String comic) {
		comicFile = comic;
		il = new ArrayList<LoadableImage>();
		loadZip(comic);
	}
	
	private void loadZip(String file) {
		File f = new File(file);
		System.out.println(f.getAbsolutePath());
		if (f.exists() && f.isFile() && f.canRead()) {
			try {
				ZipFile zf = new ZipFile(f);
				Enumeration<? extends ZipEntry> pages = zf.entries();
				while (pages.hasMoreElements()) {
					ZipEntry z = pages.nextElement();
					System.out.println("   " + z.getName());
					if (!z.isDirectory() && z.getName().toLowerCase().endsWith("jpg")) {
						LoadableImage i = new LoadableImage(z.getName(), zf.getInputStream(z));
						il.add(i);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void loadDir(String dir) {
		File f = new File(dir);
		if (f.exists() && f.isDirectory() && f.canRead()) {
			String[] pages = f.list();
			System.out.println("Loading files:");
			for (String s : pages) {
				System.out.println("   " + s);
			}
			for (String s : pages) {
				LoadableImage i = new LoadableImage(f.getAbsolutePath() + "/" + s);
				il.add(i);
			}
		}
	}
	
	public String getFileName() {
		return comicFile;
	}
	
	public int size() {
		return il.size();
	}
	
	public int getPageNumber() {
		return curImage;
	}
	
	public Image getCurrent() {
		return il.get(curImage).getImage();
	}
	
	public boolean isCurrentSplit() {
		return il.get(curImage).isSplit();
	}
	
	public void preload() {
		if (curImage < il.size()-1) {
			il.get(curImage+1).getImage();
		}
	}
	
	public void next() {
		if (isCurrentSplit()) {
			if (splitStatus <= 1) {
				splitStatus++;
			} else {
				if (curImage < il.size()-1) {
					splitStatus = 0;
					curImage++;
				}
			}
		} else {
			if (curImage < il.size()-1) {
				curImage++;
			}
		}
	}
	
	public void previous() {
		if (splitStatus > 0) {
			splitStatus--;
		} else if (curImage > 0) {
			curImage--;
			if (isCurrentSplit()) {
				splitStatus = 2;
			}
		}
	}
}
