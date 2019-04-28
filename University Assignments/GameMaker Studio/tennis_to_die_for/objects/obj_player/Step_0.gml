//moving
if(keyboard_check(vk_right) || keyboard_check(ord("D"))){ 
	if(!place_meeting(x,y, obj_net)) x += 15;
}
if(keyboard_check(vk_left) || keyboard_check(ord("A"))) x -=15;

x=clamp(x, 0, room_width);
y=clamp(y, 0, room_height);


if(global.num_trophies == 0 || global.player_hit == true){
	audio_stop_all();
	room_goto(room3);
}