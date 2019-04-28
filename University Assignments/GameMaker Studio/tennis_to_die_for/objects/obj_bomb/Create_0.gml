/// @description Go to position of player
// Scott Kelly, 14346251

dest_x = 0;
dest_y = 0;
starting_y = -30;
y = starting_y;
starting_x[0] = 100;
starting_x[1] = 400;
starting_x[2] = 800;
starting_x[3] = 1000;
starting_x[4] = 1200;
index = random_range(0,4);
x = starting_x[index];
if(instance_exists(obj_player)){
	dest_x = obj_player.x;
	dest_y = obj_player.y
	dir = point_direction(x,y,dest_x, dest_y);
}

if(global.hard) spd = 10;
else spd = 8;
