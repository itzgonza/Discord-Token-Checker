package net.ranktw.discord.util;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.zip.ZipFile;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileUtil {

	public static String chooseDirectory(File currFolder, Component parent) {
		JFileChooser chooser = new JFileChooser(currFolder);
		chooser.setFileSelectionMode(1);
		if (chooser.showOpenDialog(parent) == 0) {
			return chooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}

	public static String chooseDirectoryOrFile(File currFolder, Component parent) {
		JFileChooser chooser = new JFileChooser(currFolder);
		chooser.setFileSelectionMode(2);
		if (chooser.showOpenDialog(parent) == 0) {
			return chooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}

	public static String chooseFile(File currFolder, Component parent) {
		JFileChooser chooser = new JFileChooser(currFolder);
		if (chooser.showOpenDialog(parent) == 0) {
			return chooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}

	public static String chooseFile(File currFolder, Component parent, FileFilter filter) {
		JFileChooser chooser = new JFileChooser(currFolder);
		chooser.setFileFilter(filter);
		if (chooser.showOpenDialog(parent) == 0) {
			return chooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}

	public static String chooseFileToSave(File currFolder, Component parent, FileFilter filter) {
		JFileChooser chooser = new JFileChooser(currFolder);
		chooser.setFileFilter(filter);
		if (chooser.showSaveDialog(parent) == 0) {
			return chooser.getSelectedFile().getAbsolutePath();
		}
		return null;
	}

	public static boolean checkZip(String file) {
		try {
			new ZipFile(file).entries();
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	public static boolean checkFileExist(String file) {
		try {
			return new File(file).exists();
		} catch (Throwable e) {
			return false;
		}
	}

	public static boolean checkFileExistOrCreate(File file) {
		try {
			return file.exists() || file.createNewFile();
		} catch (Throwable e) {
			return false;
		}
	}

	public static void copyFileUsingChannel(File source, File dest) throws IOException {
		try (FileChannel sourceChannel = new FileInputStream(source).getChannel();
				FileChannel destChannel = new FileOutputStream(dest).getChannel();) {
			destChannel.transferFrom(sourceChannel, 0L, sourceChannel.size());
		}
	}

	public static class TxtFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			String name = f.getName();
			return f.isDirectory() || name.endsWith(".txt");
		}

		@Override
		public String getDescription() {
			return "Text Document (*.txt)";
		}
	}

	public static class JarFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			String name = f.getName();
			return f.isDirectory() || name.endsWith(".jar") || name.endsWith(".zip");
		}

		@Override
		public String getDescription() {
			return "Java Archives (*.jar/*.zip)";
		}
	}
}