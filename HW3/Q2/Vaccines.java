import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Vaccines {
	
	public class Country{
		private int ID;
		private int vaccine_threshold;
		private int vaccine_to_receive;
		private ArrayList<Integer> allies_ID;
		private ArrayList<Integer> allies_vaccine; 
		//private int flag = 0;
		
		public Country() {
			this.allies_ID = new ArrayList<Integer>();
			this.allies_vaccine = new ArrayList<Integer>();
			this.vaccine_threshold = 0;
			this.vaccine_to_receive = 0;
		}
		public int get_ID() {
			return this.ID;
		}
		public int get_vaccine_threshold() {
			return this.vaccine_threshold;
		}
		public ArrayList<Integer> get_all_allies_ID() {
			return allies_ID;
		}
		public ArrayList<Integer> get_all_allies_vaccine() {
			return allies_vaccine;
		}
		public int get_allies_ID(int index) {
			return allies_ID.get(index);
		}
		public int get_allies_vaccine(int index) {
			return allies_vaccine.get(index);
		}
		public int get_num_allies() {
			return allies_ID.size();
		}
		public int get_vaccines_to_receive() {
			return vaccine_to_receive;
		}
		public void set_allies_ID(int value) {
			allies_ID.add(value);
		}
		public void set_allies_vaccine(int value) {
			allies_vaccine.add(value);
		}
		public void set_ID(int value) {
			this.ID = value;
		}
		public void set_vaccine_threshold(int value) {
			this.vaccine_threshold = value;
		}
		public void set_vaccines_to_receive(int value) {
			this.vaccine_to_receive = value;
		}
		
	}
	
	public int vaccines(Country[] graph){
		//Computing the exceed in vaccines per country.
		Queue<Country> queue = new LinkedList<Country>();
		int[] shutdown = new int[graph.length]; // 0-not shut; 1-shut
		
		// shut the first country, push its allies to the queue to check whether they survive
		ArrayList<Integer> c1_allies = graph[0].allies_ID;
		for(int i=0; i<c1_allies.size(); i++) {
			int id = c1_allies.get(i);
			Country ally = graph[id-1];
			int give_ally = graph[0].allies_vaccine.get(i);
			ally.set_vaccines_to_receive(ally.get_vaccines_to_receive()-give_ally);
			queue.offer(ally);
		}
		graph[0].allies_ID.clear();
		graph[0].allies_vaccine.clear();
		
		while(!queue.isEmpty()) {
			Country c = queue.poll();
			// if the country cannot get enough vaccines, shut it down
			if(c.vaccine_to_receive < c.vaccine_threshold){	
				ArrayList<Integer> c_allies = c.allies_ID;		
				for(int i=0; i<c_allies.size(); i++) {
					int id = c_allies.get(i);
					Country ally = graph[id-1];	
					// push the ally to the queue if it has not been shutdown
					if(shutdown[ally.ID-1] == 0) {
						int give_ally = c.allies_vaccine.get(i);
						ally.set_vaccines_to_receive(ally.get_vaccines_to_receive()-give_ally);
						queue.offer(ally);				
					}
				}
				c.allies_ID.clear();
				c.allies_vaccine.clear();
				shutdown[c.ID-1] = 1;
			}	
		}
		// count how many countries survive
		int herd_immunity = 0;
		for(int i=1; i<shutdown.length; i++) {
			if(shutdown[i] == 0) herd_immunity++;
		}
		return herd_immunity;
	}

	public void test(String filename) throws FileNotFoundException {
		Vaccines hern = new Vaccines();
		Scanner sc = new Scanner(new File(filename));
		int num_countries = sc.nextInt();
		Country[] graph = new Country[num_countries];
		for (int i=0; i<num_countries; i++) {
			graph[i] = hern.new Country(); 
		}
		for (int i=0; i<num_countries; i++) {
			if (!sc.hasNext()) {
                sc.close();
                sc = new Scanner(new File(filename + ".2"));
            }
			int amount_vaccine = sc.nextInt();
			graph[i].set_ID(i+1);
			graph[i].set_vaccine_threshold(amount_vaccine);
			int other_countries = sc.nextInt();
			for (int j =0; j<other_countries; j++) {
				int neighbor = sc.nextInt();
				int vaccine = sc.nextInt();
				graph[neighbor -1].set_allies_ID(i+1);
				graph[neighbor -1].set_allies_vaccine(vaccine);
				graph[i].set_vaccines_to_receive(graph[i].get_vaccines_to_receive() + vaccine);
			}
		}
		sc.close();
		int answer = vaccines(graph);
		System.out.println(answer);
	}

	public static void main(String[] args) throws FileNotFoundException{
		Vaccines vaccines = new Vaccines();
		vaccines.test("02.in"); // run 'javac Vaccines.java' to compile, then run 'java Vaccines testfilename'
	}

}
