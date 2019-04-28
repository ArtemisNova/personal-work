/// @description destroy ball if stops moving
// Scott Kelly, 14346251

if(phy_position_x == phy_position_xprevious || 
	!point_in_rectangle(x, y, 0,0,1230,768)) delay += 1;
if(delay >= 10){
	instance_destroy();
	instance_create_layer(starting_x,starting_y[index],layer,obj_ball);
}

if(place_meeting(phy_position_x, phy_position_y, obj_trophy)){
	trophy_hit = true;
	audio_play_sound(snd_trophy_hit,10,false);
	physics_apply_force(phy_position_x, phy_position_y, -9999999999, -9999999999);	
}

if(place_meeting(phy_position_x, phy_position_y, obj_smash)) 
	physics_apply_force(phy_position_x, phy_position_y, 9999999999, 9999999999);
