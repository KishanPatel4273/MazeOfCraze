package Render;

public class test {

	public static void main(String[] args) {
		int[][] b = {{1,2,3,2345},
					 {4,5,6,3234567},
					 {7,8,9,34*23456789},
					 {234,456,782134*567890}};
		System.out.println(biggestRow(b));//b[y][x] or b[r][c]
	}
	
	public static int biggestRow(int[][] a) {
		int[] f = new int[a.length];
		int index = 0;
		for(int r = 0; r < a.length; r++) {
			for(int c = 0; c < a[r].length; c++) {
				f[r] += a[r][c];
			}
			if(f[index] < f[r]) {
				index = r;
			}
		}
		return index;
	}
	
}
