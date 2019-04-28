/// @description Insert description here
// You can write your code in this editor
if(keyboard_check_released(vk_space) || keyboard_check_released(vk_enter)){
	audio_play_sound(snd_menu_return, 10, false);
	room_goto(room1);
}