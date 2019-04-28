/// @description Drawing menu
// Scott Kelly, 14346251

draw_set_halign(fa_left);
draw_set_valign(fa_middle);
draw_set_font(fnt_menu);
draw_set_color(c_white);

for(i = 0; i < array_length_1d(menu); i++){
	draw_text(x + padding, y + (i * padding), string(menu[i]));
	
}

draw_sprite(spr_arrow, 0, x + 16, y + menu_pos * padding);