require 'test/unit'
require_relative 'point.rb'

class Point_Test < Test::Unit::TestCase
	
	def Setup
	end
	
	def test_equality
		one = Point.new(5,5)
		two = Point.new(5,5)
		
		assert_equal(one, two, "Error: Equality operator failed")
		
	end
	
	def test_addition
		one = Point.new(3,2)
		two = Point.new(2,3)
		three = Point.new(5,5)
		
		assert_equal(three, one + two, "Error: Addition operator Failed")
	end
		
end
