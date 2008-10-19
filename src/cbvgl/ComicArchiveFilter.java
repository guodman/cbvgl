package cbvgl;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ComicArchiveFilter extends FileFilter {

	public boolean accept(File f) {
		return f.getName().toLowerCase().endsWith(".cbz")
				|| f.getName().toLowerCase().endsWith(".zip")
				|| f.isDirectory();
	}

	public String getDescription() {
		return "Zipped Comic Book Archive";
	}

}
