# simple point class to hold x and y value
# and allow addition and equality of points
class Point

	def initialize(x, y)
		@pos_x = x
		@pos_y = y
	end

	attr_reader :pos_x
	attr_reader :pos_y
	
	def ==(other)
		if(@pos_x == other.pos_x && @pos_y == other.pos_y)
			return true
		else
			return false
		end
	end
	
	def +(other)
		return Point.new(@pos_x + other.pos_x, @pos_y + other.pos_y)
	end
	
	def to_s
		return "(#{pos_x},#{pos_y})"
	end
end
