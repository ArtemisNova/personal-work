/// @description Behaviour for trophy on each step
// You can write your code in this editor

if(place_meeting(x,y,obj_ball)) {
	hit = true;
}

if(hit){
	y += 7;
	image_angle += 30;
}

if(!point_in_rectangle(x, y, 0,0,1230,768)){
	delay += 1;
	if(delay > 20){
		global.num_trophies -= 1;
		instance_destroy();	
	}
}