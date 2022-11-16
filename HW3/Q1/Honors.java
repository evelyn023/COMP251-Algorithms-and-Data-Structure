import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Honors {
	
	public static int min_moves(int[][] board) {
		int row = board.length;
		int col = board[0].length;
		// it stores the distance of the cells from (0,0)
		int[][] dis_board = new int[row][col];

		// queue stores the cells which have been visited but not done
		Queue<int[]> queue = new LinkedList<int[]>();
		int[] c = new int[]{0,0} ;
		queue.offer(c);
		
		while(!queue.isEmpty()) {
			int[] cur = queue.poll();
			// an array list storing the coordinates which can be accessed from the current cell
			int num = board[cur[0]][cur[1]];
			ArrayList<int[]> adj = new ArrayList<>();
			if(cur[0]+num < row) {
				adj.add(new int[] {cur[0]+num, cur[1]});
			}
			if(cur[0]-num >= 0) {
				adj.add(new int[] {cur[0]-num, cur[1]});
			}
			if(cur[1]+num < col) {
				adj.add(new int[] {cur[0], cur[1]+num});
			}
			if(cur[1]-num >= 0) {
				adj.add(new int[] {cur[0], cur[1]-num});
			}
			// go to next cell		
			for(int i=0; i<adj.size(); i++) {
				int[] v = adj.get(i);			
				if(dis_board[v[0]][v[1]] == 0) {
					dis_board[v[0]][v[1]] = dis_board[cur[0]][cur[1]] + 1;
					
					if (v[0] == row-1 && v[1] == col-1) {
						return dis_board[v[0]][v[1]]; // reach the end, return the result
					}else if(board[v[0]][v[1]] == 0) {
						continue; // skip the cell if it contains value 0
					}
					queue.offer(v);
				}	
			}			
		}
		return -1; // placeholder
	}	

	public void test(String filename) throws FileNotFoundException{
		Scanner sc = new Scanner(new File(filename));
		int num_rows = sc.nextInt();
		int num_columns = sc.nextInt();
		int [][]board = new int[num_rows][num_columns];
		for (int i=0; i<num_rows; i++) {
			char line[] = sc.next().toCharArray();
			for (int j=0; j<num_columns; j++) {
				board[i][j] = (int)(line[j]-'0');
			}
			
		}
		sc.close();
		int answer = min_moves(board);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Honors honors = new Honors();
		honors.test(args[0]); // run 'javac Honors.java' to compile, then run 'java Honors testfilename'
		
//		// or you can test like this
//		 int [][]board = {{3,4,4,4,0,1,4,1,2,3},{0,3,2,2,4,2,5,0,0,0},{0,1,2,3,2,1,4,1,4,0}}; // not actual example
//		 //int [][]board = {{1,1},{1,1}};
//		 int answer = min_moves(board); 
//		 System.out.println(answer);
	}

}