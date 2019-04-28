package observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
	
	public void addObserver(Observer o) {
		observers.add(o);
	}
	
	public Observer deleteObserver(int index) {
		if(index > 0 && index < observers.size())
			return observers.remove(index);
		else
			return null;
	}
	
	public void notifyObservers(int state) {
		for(Observer current: observers)
			current.update(state);
	}
	
	private List<Observer> observers = new ArrayList<Observer>();
}
