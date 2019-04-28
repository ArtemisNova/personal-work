require 'test/unit'
require_relative 'baseStation.rb'

class BaseStation_test < Test::Unit::TestCase
	
	def setup
		@station = BaseStation.new('Alpha', 3, 4)
	end
	
	def test_initialize
		assert_equal(@station.name, 'Alpha', "Error in storing name")
		assert_equal(@station.cell, Cell.new(3,4), "Error in storing location cell")
	end
end
