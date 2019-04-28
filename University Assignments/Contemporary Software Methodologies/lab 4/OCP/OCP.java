package OCP;

public class OCP {
	  public static void main(String[] args) {
	    Square square = new Square(10.1);
	    Circle circle = new Circle(5);
	    PostageStamp squareStamp = new PostageStamp(square);
	    PostageStamp circleStamp = new PostageStamp(circle);
	    System.out.println(squareStamp.toString());
	    System.out.println(circleStamp.toString());
	  }
}

class PostageStamp{
	public PostageStamp(Shape shape){
		this.shape = shape;
	}
	public String toString(){
	  return "stamp, contained in a " + shape.toString();
	}
	Shape shape; 
}

class Square implements Shape {
	public Square(double d){
		length = d;
	}
	public String toString(){
		return "square, side of length " + length;
	}
	private double length;
}

class Circle implements Shape {
	public Circle(double d) {
		length = d;
	}
	public String toString() {
		return "circle, radius of length " + length;
	}
	private double length;
}
	
