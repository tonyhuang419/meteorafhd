package gcodeJam.watersheds;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class MyWatersheds {

	public static void main(String[] args) {
		new MyWatersheds().run();
	}

	private Scanner cin;
	private PrintStream output = System.out;
	private int map[][];
	private int dir[][];
	private char mk[][];
	private int h, w;

	private void mkDir() {
		for (int i = 1; i <= h; ++i) {
			for (int j = 1; j <= w; ++j) {
				int low = 0, val = map[i-1][j];
				if (map[i][j-1] < val) {
					val = map[i][j-1];
					low = 1;
				}
				if (map[i][j+1] < val) {
					val = map[i][j+1];
					low = 2;
				}
				if (map[i+1][j] < val) {
					val = map[i+1][j];
					low = 3;
				}
				if (val >= map[i][j]) {
					continue;
				}
				switch (low) {
				case 0:
					dir[i-1][j] |= 2; break;
				case 1:
					dir[i][j-1] |= 1; break;
				case 2:
					dir[i][j] |= 1; break;
				case 3:
					dir[i][j] |= 2; break;
				}
			}
		}
	}
	
	private void mkMk(int i, int j, char sg) {
		mk[i][j] = sg;
		if (mk[i-1][j] == 0 && (dir[i-1][j] & 2) != 0) {
			mkMk(i-1, j, sg);
		}
		if (mk[i][j-1] == 0 && (dir[i][j-1] & 1) != 0) {
			mkMk(i, j-1, sg);
		}
		if (mk[i][j+1] == 0 && (dir[i][j] & 1) != 0) {
			mkMk(i, j+1, sg);
		}
		if (mk[i+1][j] == 0 && (dir[i][j] & 2) != 0) {
			mkMk(i+1, j, sg);
		}
	}
	
	private void process() {
		h = cin.nextInt();
		w = cin.nextInt();
		map = new int[h+2][w+2];
		dir = new int[h+2][w+2];
		mk = new char[h+2][w+2];
		for (int j = 0; j <= w+1; ++j) {
			map[0][j] = map[h+1][j] = 20000;
		}
		for (int i = 1; i <= h; ++i) {
			map[i][0] = map[i][w+1] = 20000;
			for (int j = 1; j <= w; ++j) {
				map[i][j] = cin.nextInt();
			}
		}
		for (int i = 1; i <= h; ++i) {
			for (int j = 1; j <= w; ++j) {
				dir[i][j] = 0;
				mk[i][j] = 0;
			}
		}
		mkDir();
		char sg = 'a';
		for (int i = 1; i <= h; ++i) {
			String tmp = "";
			for (int j = 1; j <= w; ++j) {
				if (mk[i][j] == 0) {
					mkMk(i, j, sg);
					sg++;
				}
				tmp += (" " + mk[i][j]);
			}
			output.println(tmp.substring(1));
		}
	}
	
	private void run() {
		InputStream input = System.in;
		try {
			input = new FileInputStream("src/main/java/gcodeJam/watersheds/test.in");
//			output = new PrintStream("src/main/java/gcodeJam/watersheds/test.out");
		} catch (Exception e) {
			e.printStackTrace();
		}
		cin = new Scanner(input);
		int cas = cin.nextInt();
		for (int t = 1; t <= cas; ++t) {
			output.printf("Case #%d:\n", t);
			process();
		}
	}

}