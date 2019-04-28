require 'test/unit'
require_relative 'kangaroo.rb'
require_relative 'point.rb'
require_relative 'grid.rb'

class Kangaroo_test < Test::Unit::TestCase
	
	def setup
		test_grid = Grid.new(3)
		@test = Kangaroo.new(4, D4, test_grid)
	end
	
	def test_destination
		dest = Point.new(3,3)
		@test.set_destination(dest)
		assert(@test.instance_variable_get(:@destination) == dest, "Error in Set_Destination")
	end
	
	def test_moving
		current = @test.instance_variable_get(:@location)
		current2 = @test.move
		assert(current = current2, "Error in Move Class")
	end
end
