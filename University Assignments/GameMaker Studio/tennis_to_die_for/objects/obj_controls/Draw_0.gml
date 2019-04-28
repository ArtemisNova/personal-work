/// @description Drawing the text
// Scott Kelly, 14346251

draw_set_halign(fa_left);
draw_set_valign(fa_left);
draw_set_font(fnt_controls);
draw_set_color(c_white);

for(i = 0; i < array_length_1d(text)-1; i++){
	draw_text(x + padding, y + (i * padding), string(text[i]));
}

draw_text(x + padding, y + (i * padding) + 30, string(text[menu_pos]));

