require 'test/unit'
require_relative 'rollstats.rb'

class RollStats_test < Test::Unit::TestCase
	
	def test_calc_percentage
		test_stats = RollStats.new(nil, 0)
		test1 = 10
		test2 = 100
		
		result = test_stats.calc_percentage(10,100)
		
		assert_equal(result, 10, 'Error in calculating percentage')
	end
	
	
end
