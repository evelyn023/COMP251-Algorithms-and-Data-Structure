import java.util.*;
import java.lang.*;
import java.io.*;

public class Midterm {
	private static int[][] dp_table;
	private static int[] penalization;
	private static int tempMin; // temporary minimum lost marks

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int chairs;
		try {
			chairs = Integer.valueOf(reader.readLine());
			penalization = new int[chairs];
			for (int i=0; i< chairs; i++) {
				penalization[i] = Integer.valueOf(reader.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int answer = lost_marks(penalization);
		System.out.println(answer);
	}
	
	
	private static void fill(int[] p, int index, int jump, int marks) {
		// base case
		if (index > p.length-1 || index < 0 || jump > dp_table[0].length) return;
		
		int toFill = marks + p[index];
		if (dp_table[index][jump-1] < toFill) return;
		if (toFill > tempMin) return;
		// if a process of jump already lose marks greater than tempMin before reaching the end,
		// skip this process
		if (index == p.length-1) {
			tempMin = toFill;
		} // update tempMin if reaching the end
			
		// recursion
		dp_table[index][jump-1] = toFill;	
		fill(p, index+jump+1, jump+1, toFill);
		fill(p, index-jump, jump, toFill);
	}
	
	public static int lost_marks(int[] penalization) {
		//To Do => Please start coding your solution here
		int n = penalization.length;
		dp_table = new int[n][n-1];
		tempMin = 500000;
		for (int a=0; a<dp_table.length;a++) {
			for (int j=0; j<dp_table[0].length; j++) {
				dp_table[a][j] = 500000;	
			}
		}
		fill(penalization,1,1,0);
		int min = 500000;
		for (int i=0; i<n-1; i++) {
			if (dp_table[n-1][i]<min) {
				min = dp_table[n-1][i];
			}
		}
		return min; // placeholder
	}
}
