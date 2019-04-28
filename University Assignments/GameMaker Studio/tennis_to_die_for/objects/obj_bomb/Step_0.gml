/// @description bomb moving
// Scott Kelly, 14346251
x += lengthdir_x(spd, dir);
y += lengthdir_y(spd, dir);
if(global.hard) image_angle += 30;
else image_angle += 10;

if(place_meeting(x,y,obj_ground) || place_meeting(x,y,obj_player) || !point_in_rectangle(x, y, 0,-50,1230,768)){
	if(place_meeting(x,y,obj_player)) global.player_hit = true;
	instance_destroy();
	instance_create_layer(x,y,layer,obj_boom);
	instance_create_layer(starting_x[index],starting_y,layer,obj_bomb);
}

