require 'test/unit'
require_relative 'grid.rb'
require_relative 'point.rb'

class Grid_Test < Test::Unit::TestCase
	
	def Setup
	end
	
	def test_valid_location
		test_grid = Grid.new(9)
		test = Point.new(-1,-1)
		test2 = Point.new(9,9)
		
		assert(test_grid.valid_location?(test) == false, "Error: point out of bounds accepted")
		assert(test_grid.valid_location?(test2) == false, "Error: point out of bounds accepted")
	end
	
end
