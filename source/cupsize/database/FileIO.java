package cupsize.database;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class FileIO {
	private List<FileDataStruct> data;
	
	public FileIO() {
		inputDatabase("CharacterData.dat");
	}
	
	public void debug() {
		if (data == null) {
			System.out.println("No data");
			return;
		}
		for (FileDataStruct fds : data) {
			System.out.println(fds.name);
		}
	}
	
	public List<FileDataStruct> getList() {
		return data;
	}
	
	public List<FileDataStruct> getIndexList() {
		List<FileDataStruct> list = new ArrayList<>();
		
		for (int i = 0; i < data.size(); i++) {
			FileDataStruct d = data.get(i);
			d.index = i;
			list.add(d);
		}
		return list;
	}
	
	public FileDataStruct get(int i) {
		return data.get(i);
	}
	
	public void add(FileDataStruct fds) {
		data.add(fds);
	}
	
	public void set(int i, FileDataStruct fds) {
		data.set(i, fds);
	}
	
	public void remove(int i) {
		data.remove(i);
	}
	
	public boolean inputDatabase(String openFile) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(openFile));
			data = readFile(br);
			br.close();
		} catch (IOException e) {
			data = new ArrayList<>();
			return false;
		}
		return true;
	}
	
	private static String getStackTrace(Exception e) {
		StackTraceElement[] list = e.getStackTrace();
		StringBuilder b = new StringBuilder();
		b.append(e.getClass()).append(":").append(e.getMessage()).append("\n");
		for( StackTraceElement s : list ) {
			b.append(s.toString()).append("\n");
		}
		return b.toString();
	}
	
	public boolean outputDatabase(String saveFile) {
		boolean res = true;
		try {
			FileWriter filewriter = new FileWriter(saveFile);
			PrintWriter pw = new PrintWriter(new BufferedWriter(filewriter));
			for (FileDataStruct fds : data) {
				writeFile(pw, fds);
			}
			pw.close();
		} catch (IOException e) {
			res = false;
		}
		return res;
	}
	
	private List<FileDataStruct> readFile(BufferedReader br) throws IOException {
		String str;
		List<FileDataStruct> list = new ArrayList<>();
		while((str = br.readLine()) != null) {
			String[] res = str.split(",");
			list.add(new FileDataStruct(res));
        }
        return list;
	}
	
	private void writeFile(PrintWriter pw, FileDataStruct fds) throws IOException {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Status s : Status.values()) {
			if (i > 0) sb.append(",");
			sb.append(fds.getString(s));
			i++;
		}
		pw.println(sb.toString());
	}
	
	private boolean isWindows() {
		return System.getProperty("os.name").toLowerCase().startsWith("windows");
	}
}