package Premier_Paquet;
import java.util.*;
import java.io.*; 

public class Grid_Folding {
	static public void fold(Map<Integer, int[][]> map, int order, String operation) {
		if(order == 8) return;
		int layers = 2 << order;   // layers: from 2 to 256
		int preRow = map.get(layers / 2 - 1).length;
		int preCol = map.get(layers / 2 - 1)[0].length;
		char operator = operation.charAt(order);
		if(operator == 'T') {
			int row = preRow / 2, col = preCol;
			for(int i = layers / 2; i < layers; ++i) {
				int[][] layer = new int[row][col];
				int[][] mirrorLayer = map.get(layers - i - 1);
				for(int j = 0; j < row; ++j) {
					for(int k = 0; k < col; ++k) {
						layer[row - j - 1][k] = mirrorLayer[j][k];
					}
				}
				map.put(i, layer);
			}
			for(int i = 0; i < layers / 2; ++i) {
				int[][] layer = map.get(i);
				for(int j = row; j < preRow; ++j) {
					for(int k = 0; k < preCol; ++k) {
						layer[j - row][k] = layer[j][k];
					}
				}
				map.put(i, layer);
			}
		}
		else if(operator == 'B') {
			int row = preRow / 2, col = preCol;
			for(int i = layers / 2; i < layers; ++i) {
				int[][] layer = new int[row][col];
				int[][] mirrorLayer = map.get(layers - i - 1);
				for(int j = 0; j < row; ++j) {
					for(int k = 0; k < col; ++k) {
						layer[j][k] = mirrorLayer[preRow - j - 1][k];
					}
				}
				map.put(i, layer);
			}
		}
		else if(operator == 'L') {
			int row = preRow, col = preCol / 2;
			for(int i = layers / 2; i < layers; ++i) {
				int[][] layer = new int[row][col];
				int[][] mirrorLayer = map.get(layers - i - 1);
				for(int j = 0; j < row; ++j) {
					for(int k = 0; k < col; ++k) {
						layer[j][col - k - 1] = mirrorLayer[j][k];
					}
				}
				map.put(i, layer);
			}
			for(int i = 0; i < layers / 2; ++i) {
				int[][] layer = map.get(i);
				for(int j = 0; j < preRow; ++j) {
					for(int k = col; k < preCol; ++k) {
						layer[j][k - col] = layer[j][k];
					}
				}
				map.put(i, layer);
			}
		}
		else if(operator == 'R') {
			int row = preRow, col = preCol / 2;
			for(int i = layers / 2; i < layers; ++i) {
				int[][] layer = new int[row][col];
				int[][] mirrorLayer = map.get(layers - i - 1);
				for(int j = 0; j < row; ++j) {
					for(int k = 0; k < col; ++k) {
						layer[j][k] = mirrorLayer[j][preCol - k - 1];
					}
				}
				map.put(i, layer);
			}
		}
		fold(map, order + 1, operation);
	}
	
	static public boolean isLegal(String operation) {   // detect invalid inputs
		if(operation.length() != 8) return false;
		for(int i = 0; i < 7; ++i) {
			char ith = operation.charAt(i), sufith = operation.charAt(i + 1);
			if(ith != 'L' && ith != 'R' && ith != 'T' && ith != 'B') return false;
			if((ith == 'L' || ith == 'R')
			&& (sufith == 'L' || sufith == 'R'))
				return false;
			if((ith == 'T' || ith == 'B')
			&& (sufith == 'T' || sufith == 'B'))
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader buf = new BufferedReader (new InputStreamReader(System.in));  
		String operation = buf.readLine();   // input the operation string
		if(isLegal(operation) == false) {
			System.out.println("It is an invalid input!");   // invalid input string
			return;
		}
		Map<Integer, int[][]> map = new HashMap<Integer, int[][]>();
		int[][] nums = new int[16][16];
		for(int i = 0; i < 16; ++i) {   // initiate the grid using 1-256
			for(int j = 0; j < 16; ++j) {
				nums[i][j] = 16 * i + j + 1;
			}
		}
		map.put(0, nums);
		fold(map, 0, operation);
		int[] answer = new int[256];
	    for(int i = 0; i < 256; ++i) {
	    	answer[i] = map.get(255 - i)[0][0];
	    	System.out.println(answer[i]);   // output the answer in an array
	    }
	    buf.close();
	}
}
