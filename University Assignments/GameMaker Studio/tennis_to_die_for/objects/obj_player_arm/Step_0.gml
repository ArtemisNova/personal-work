// make the arm follow the player sprite's movements
phy_position_x = obj_player.x + 7;
phy_position_y = obj_player.y + 3;

// rotate on key press
if((keyboard_check(vk_up) || keyboard_check(ord("W"))) && phy_rotation > -90){
	phy_rotation -= 10;
	smash_x_offset -= 5;
	smash_y_offset -= 12;
}

if((keyboard_check(vk_down) || keyboard_check(ord("S"))) && phy_rotation < 90){
	phy_rotation += 10;
	smash_x_offset += 5;
	smash_y_offset += 12;
}

if((keyboard_check(vk_space) || keyboard_check(vk_enter)) && !instance_exists(obj_smash)){
	instance_create_layer(x+smash_x_offset,y+smash_y_offset,"ball",obj_smash);	
}