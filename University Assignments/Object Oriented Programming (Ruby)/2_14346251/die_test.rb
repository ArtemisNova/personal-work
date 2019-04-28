require 'test/unit'
require_relative 'die.rb'
require_relative 'constants.rb'

class Die_test < Test::Unit::TestCase

	def setup
		@test = Die.new(4, D4)
	end
	
	def test_roll
		roll = @test.roll
		
		assert(D4.include?(roll), "Output from roll out of range")
	end
end
