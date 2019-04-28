package part2;

public class Triangle {
	
	public int[] sides;
	
	public Triangle(int one, int two, int three) {
		sides = new int[] {one,two,three};
	}
	
	public String typeOf() {
		
		if( (sides[0]+sides[1]<sides[2]) ||  (sides[0]+sides[2]<sides[1]) || (sides[1]+sides[2]<sides[0])) {
			return "Invalid"; 
		}
		else if(sides[0] == sides[1] && sides[1] == sides[2])
			return "Equilateral";
		else if(sides[0] == sides[1] || sides[0] == sides[2] || sides[1] == sides[2])
			return "Isoceles";
		else
			return "Scalene";
	}
}
