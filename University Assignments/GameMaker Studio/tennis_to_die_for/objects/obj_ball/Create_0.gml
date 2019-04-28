/// @description Insert description here
// You can write your code in this editor
delay = 0;
starting_x = 1250;
starting_y[0] = 500;
starting_y[1] = 350;
starting_y[2] = 300;
starting_y[3] = 550;
starting_y[4] = 600;
trophy_hit = false;

x_force = random_range(-10000, -15000);
y_force = random_range(-2000, -5000);

x = starting_x;
index = random_range(0,4);
y = starting_y[index];
physics_apply_force(phy_position_x, phy_position_y, x_force,y_force);

