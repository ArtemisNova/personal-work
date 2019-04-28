package observer;

public class Person implements Observer{

	public Person(String a_name, AlarmClock clock){
		name = a_name;
		alarmClock = clock;
		alarmTime = alarmClock.getAlarmTime();
		alarmClock.addObserver(this);
	}

	public void goToBed(){
		System.out.println(name + " is going to bed...");
	}

	public void wakeUp(){
		System.out.println(name + " has woken up! ");
	}
	
	public void updateAlarmTime() {
		alarmTime = alarmClock.getAlarmTime();
		System.out.println(name + "'s alarm time has been set to " + alarmTime);
	}

	@Override
	public void update(int state) {
		switch(state) {
		case 0:
			wakeUp();
			break;
		case 1:
			updateAlarmTime();
			break;
		}
	}
	
	private String name, alarmTime;
	private AlarmClock alarmClock = new AlarmClock();
}
