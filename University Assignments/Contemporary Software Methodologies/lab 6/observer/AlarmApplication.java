package observer;

public class AlarmApplication {

  public static void main(String[] args) {
    AlarmClock alarmClock = new AlarmClock();
    Person jack = new Person("Jack", alarmClock);
    Person sam = new Person("Sam", alarmClock);
    Person carl = new Person("Carl", alarmClock);
	Cron cron = new Cron(alarmClock);
    
    alarmClock.setAlarmTime(7, 30, 0);
    jack.goToBed();
    sam.goToBed();
    carl.goToBed();
    
    for (int i=1; i<=SECONDS_IN_DAY; i++){
      alarmClock.tick();
    }
  }
  
  public static final int SECONDS_IN_DAY = 86400;
}