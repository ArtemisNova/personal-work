/// @description Moving the cursor
// Scott Kelly, 14346251

move = 0;

if(keyboard_check_pressed(vk_up) || keyboard_check_pressed(ord("W"))) move -= 1;
if(keyboard_check_pressed(vk_down) || keyboard_check_pressed(ord("D"))) move += 1;

if(move != 0){
	audio_play_sound(snd_cursor_move, 10, false);
	menu_pos += move;
	if(menu_pos < 0) menu_pos = array_length_1d(menu)-1;
	if(menu_pos > array_length_1d(menu)-1) menu_pos = 0;
}

if(keyboard_check_released(vk_space) || keyboard_check_released(vk_enter)){
	audio_play_sound(snd_cursor_select, 10, false);
	scr_menu();
}