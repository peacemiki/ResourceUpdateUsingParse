package kr.co.schoolholic.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {
	public static File makeDirectory(String dir_path) {
		File dir = new File(dir_path);
		if (!dir.exists()) {
			dir.mkdirs();
		} else {
		}

		return dir;
	}

	public static File makeFile(File dir, String file_path) {
		File file = null;
		if (dir.isDirectory()) {
			file = new File(file_path);
			if (file != null && !file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
				}
			} else {
			}
		}
		return file;
	}


	/**
	 * 파일에 내용 쓰기
	 * 
	 * @param file
	 * @param str
	 * @return
	 */
	public static boolean writeFile(File file, String str) {
		boolean result;
		FileOutputStream fos;
		if (file != null && file.exists() && str != null) {
			try {
				fos = new FileOutputStream(file);
				try {
					BufferedWriter buw = new BufferedWriter(new OutputStreamWriter(fos, "UTF8"));
					buw.write(str);
					buw.close();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Write a byte array to the given file. Writing binary data is
	 * significantly simpler than reading it.
	 */
	public static void write(File file, byte[] aInput) {
		try {
			OutputStream output = null;
			try {
				output = new BufferedOutputStream(new FileOutputStream(file));
				output.write(aInput);
			} finally {
				output.close();
			}
		} catch (FileNotFoundException ex) {
			// log("File not found.");
		} catch (IOException ex) {
			// log(ex);
		}
	}

	public static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		is.close();

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}

		return bytes;
	}

	/**
	 * 파일 복사
	 * 
	 * @param file
	 * @param save_file
	 * @return
	 */
	public static boolean copyFile(File file, String save_file) {
		boolean result;
		if (file != null && file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream newfos = new FileOutputStream(save_file);
				int readcount = 0;
				byte[] buffer = new byte[1024];
				while ((readcount = fis.read(buffer, 0, 1024)) != -1) {
					newfos.write(buffer, 0, readcount);
				}
				newfos.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public static void unzip(String zipFile, String location) throws IOException {
		int BUFFER_SIZE = 4000;
		int size;
		byte[] buffer = new byte[BUFFER_SIZE];

		try {
			if (!location.endsWith("/")) {
				location += "/";
			}
			File f = new File(location);
			if (!f.isDirectory()) {
				f.mkdirs();
			}

			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE));
			try {
				ZipEntry ze = null;
				while ((ze = zin.getNextEntry()) != null) {
					String path = location + ze.getName();
					File unzipFile = new File(path);

					if (ze.isDirectory()) {
						if (!unzipFile.isDirectory()) {
							unzipFile.mkdirs();
						}
					} else {
						// check for and create parent directories if they don't
						// exist
						File parentDir = unzipFile.getParentFile();
						if (null != parentDir) {
							if (!parentDir.isDirectory()) {
								parentDir.mkdirs();
							}
						}

						// unzip the file
						FileOutputStream out = new FileOutputStream(unzipFile, false);
						BufferedOutputStream fout = new BufferedOutputStream(out, BUFFER_SIZE);
						try {
							while ((size = zin.read(buffer, 0, BUFFER_SIZE)) != -1) {
								fout.write(buffer, 0, size);
							}

							zin.closeEntry();
						} finally {
							fout.flush();
							fout.close();
						}
					}
				}
			} finally {
				zin.close();
			}
		} catch (Exception e) {
		}
	}
}
