package observer;

public class Cron implements Observer{

	public Cron(AlarmClock clock) {
		_clock = clock;
		actionTime = clock.getAlarmTime();
		_clock.addObserver(this);
	}
	
	@Override
	public void update(int state) {
		switch(state) {
		case 0:
				doAction();
				break;
		case 1:
				updateTime();
				break;
		}
	}
	
	public void doAction() {
		System.out.println("Action time has been reached. Cron is doing something!");
	}
	
	public void updateTime() {
		actionTime = _clock.getAlarmTime();
		System.out.println("Cron's action time has been set to " + actionTime);
	}
	
	
	private String actionTime;
	private AlarmClock _clock;
}
