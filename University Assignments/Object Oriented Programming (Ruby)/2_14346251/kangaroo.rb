# Class to implement movement of kangaroo over the grid


require_relative 'die.rb'
require_relative 'point.rb'
require_relative 'constants.rb'
require_relative 'grid.rb'

class Kangaroo
	
	def initialize(sides, dice, grid)
		@grid = grid
		@dice = Die.new(sides, dice)
		@location = Point.new(0,0)
		set_destination(Point.new(@grid.dimension - 1, @grid.dimension - 1))
		@num_hops = 0
	end
	
	attr_reader :num_hops
	
	def set_destination(dest)
		@destination = dest
	end
	
	def reached_goal?
		if(@location == @destination)
			true
		else
			false
		end
	end
	
	def get_results
		return @dice.stats_as_string
	end
	
	# creates a point to simulate a direction shift if added to current position
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
		else
			return 'ERROR IN KANGAROO.MOVE: Unknown direction'
		end
		
		return moving
	end
	
	def	move
		result = @dice.roll
		
		moving = direction_shift(result)
		
		temp = @location + moving 
		
		if(@grid.valid_location?(temp))
			@location = temp
			output = "Hopped #{result} to: #{temp.to_s}"
			@num_hops += 1
			if(temp == @destination)
				output += "\nFinished in #{@num_hops} hops!"
			end
		else
			output = "Oops, hit the boundary: #{temp.to_s}"
		end

		return output
	end
	
	
end
