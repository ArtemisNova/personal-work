require 'test/unit'
require_relative 'baseStations.rb'

class BaseStations_test < Test::Unit::TestCase

	def setup
		@test = BaseStations.new
	end
	
	def test_initialize
		assert_equal(@test.num_stations, 0, "Error: baseStations not initialized with no stations")
	end
end
