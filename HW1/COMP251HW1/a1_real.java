import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class a1_real {
	
	public static int silence(int[] positions) {
		
		HashMap<Integer,int[]> langTable = new HashMap<>();
		ArrayList<Integer> distances = new ArrayList<>();
		int n = positions.length;
		
		for (int i=0; i<n; i++) {
			int[] info  = langTable.get(positions[i]);
			if (info == null){
				int[] initial = new int[] {i,n};
				langTable.put(positions[i], initial);
			}else {
				int lastPosition = info[0];
				int newD = i-lastPosition;
				if (newD < info[1]) {
					info[0] = i;
					info[1] = newD;
					distances.add(newD);
				}
			}
		}
		
		int minD = n;
		for (int i=0; i<distances.size(); i++) {
			if (distances.get(i) < minD) minD = distances.get(i);
		}
		return minD;
		
	}

	public static void main(String[] args) {
		try {
			String path = args[0];
      		File myFile = new File(path);
      		Scanner sc = new Scanner(myFile);
      		int num_students = sc.nextInt();
      		int[] positions = new int[num_students];
      		for (int student = 0; student<num_students; student++){
				positions[student] =sc.nextInt();
      		}
      		sc.close();
      		int answer = silence(positions);
      		System.out.println(answer);
    	} catch (FileNotFoundException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}
  	}		
}