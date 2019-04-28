package observer;

public class AlarmClock extends Observable{

	public AlarmClock(){
		hours=0; minutes=0; seconds=0;
		alarmHours=0; alarmMinutes=0; alarmSeconds=0;
	}

	public void setTime(int hrs, int mins, int secs){
		hours=hrs; minutes=mins; seconds=secs;
	}

	public void setAlarmTime(int hrs, int mins, int secs){
		alarmHours=hrs; 
		alarmMinutes=mins; 
		alarmSeconds=secs;
		notifyObservers(ALARM_TIME_UPDATED);
	}

	public void tick() {
		if(alarmReached()) notifyObservers(ALARM_TRIGGERED);

		if ((seconds += 1) == 60) {
			seconds = 0;
			if ((minutes += 1) == 60) {
				minutes = 0;
				if ((hours += 1) == 24) {
					hours = 0;
				}
			}
		}

	}

	public String getTime(){
		return Integer.toString(hours)+":"+Integer.toString(minutes)+":"+Integer.toString(seconds);
	}
	
	public String getAlarmTime(){
		return Integer.toString(alarmHours)+":"+Integer.toString(alarmMinutes)+":"+Integer.toString(alarmSeconds);
	}

	public boolean alarmReached(){
		return (hours==alarmHours&&minutes==alarmMinutes&&seconds==alarmSeconds);
	}

	private int hours, minutes, seconds;
	private int alarmHours, alarmMinutes, alarmSeconds;
	private static final int ALARM_TRIGGERED = 0;
	private static final int ALARM_TIME_UPDATED = 1;
}