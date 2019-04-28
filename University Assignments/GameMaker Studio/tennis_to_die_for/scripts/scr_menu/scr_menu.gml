// Scott Kelly, 14346251

switch(menu_pos){
	case 0:
		for(i = 0; i < 1000000; i++){} // loop added so you can hear sound
		audio_stop_all();
		global.hard = false;
		room_goto(room0);
		break;
	case 1:
		for(i = 0; i < 1000000; i++){} // loop added so you can hear sound
		audio_stop_all();
		global.hard = true;
		room_goto(room0);
		break;
	case 2:
		room_goto(room2);
		break;
	case 3:
		for(i = 0; i < 1000000; i++){} // loop added so you can hear sound
		game_end();
		break;
	default:
		break;
}