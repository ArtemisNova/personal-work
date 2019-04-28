require 'test/unit'
require_relative 'cell.rb'

class Cell_test < Test::Unit::TestCase

	def setup
		@cell = Cell.new(5,4)
	end
	
	def test_initialize
		
		assert_equal(@cell.x, 5, "ERROR in storing x value")
		assert_equal(@cell.y, 4, "ERROR in storing y value")
	end

end
