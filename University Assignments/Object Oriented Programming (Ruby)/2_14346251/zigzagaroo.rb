# class that inherits from kangaroo, just implements an 8 sided dice

require_relative 'kangaroo.rb'

class Zigzagaroo < Kangaroo
	
	def initialize(sides, dice, grid_size)
		super(sides, dice, grid_size)
	end
	
	def direction_shift(direction)
		case direction
		when :North
			moving = Point.new(1, 0)
		when :South
			moving = Point.new(-1, 0)
		when :East
			moving = Point.new(0, 1)
		when :West
			moving = Point.new(0,-1)
		when :NorthEast
			moving = Point.new(1,1)
		when :NorthWest
			moving = Point.new(1,-1)
		when :SouthEast
			moving = Point.new(-1,1)
		when :SouthWest
			moving = Point.new(-1,-1)
		else
			return 'ERROR IN ZIGZAGAROO.MOVE: Unknown direction'
		end
		
		return moving
	end

end
