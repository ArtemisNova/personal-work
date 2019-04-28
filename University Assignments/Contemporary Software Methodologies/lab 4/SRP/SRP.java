package SRP;

public class SRP {
	public static void main(String[] args) {
	    Human turner = new Human("Turner");
	    Dog hooch = new Dog("Hooch");
	}
}

class Human {
	public Human(String name) {
		_name = name;
	}
	
	public void throwStick(){
	    System.out.println(_name + "is throwing a stick");
	}
	
	public void walk(){
	    System.out.println(_name + " is walking");
	}
	
	String _name;
}

class Dog {
	public Dog(String name) {
		_name = name;
	}
	
	public void fetchStick(){
	    System.out.println(_name + " fetching a stick");
	}
	
	public void walk(){
		System.out.println(_name + " is walking");
	}
	
	public void bark(){
	    System.out.println("Woof! ");
	}
	 
	String _name;
	
}
