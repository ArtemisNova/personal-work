# grid class to hold dimension and disallow invalid moves

class Grid
	
	def initialize(dimension)
		@dimension = dimension
	end
	
	attr_accessor :dimension
	
	def valid_location?(location)
		if(location.pos_x < 0 || location.pos_y < 0 || location.pos_x > @dimension - 1 || location.pos_y > @dimension - 1)
			false
		else
			true
		end
	end
end
