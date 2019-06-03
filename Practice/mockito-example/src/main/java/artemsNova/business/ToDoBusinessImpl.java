package artemsNova.business;

import java.util.ArrayList;
import java.util.List;

import artemsNova.data.api.ToDoService;

public class ToDoBusinessImpl {
	private ToDoService service;
	
	public ToDoBusinessImpl(ToDoService service) {
		this.service = service;
	}
	
	public List<String> retriveToDosRelatedToSpring(String user){
		List<String> filteredToDos = new ArrayList<String>();
		List<String> listOfToDos = service.retrieveTodos(user);
		
		for(String todo: listOfToDos) {
			if(todo.contains("Spring")) {
				filteredToDos.add(todo);
			}
		}
		
		return filteredToDos;
	}
}
