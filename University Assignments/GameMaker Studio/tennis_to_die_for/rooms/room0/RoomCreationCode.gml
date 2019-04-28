if(!global.hard) audio_play_sound(snd_game_music,10,true);
else{ audio_play_sound(sound8,10,true); }
instance_create_layer(1270,570,"ball", obj_ball);
instance_create_layer(740,-30,"ball", obj_bomb);

if(global.hard){
	instance_create_layer(1270,400,"ball", obj_ball);	
}

global.num_trophies = 3;
global.player_hit = false;