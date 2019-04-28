/// @description Setting up variables
// Scott Kelly, 14346251
menu[0] = "Start Easy Mode";
menu[1] = "Start Hard Mode";
menu[2] = "How to Play";
menu[3] = "Quit";

padding = 64;
menu_pos = 0;

if(!audio_is_playing(snd_menu)) audio_play_sound(snd_menu,10,true);