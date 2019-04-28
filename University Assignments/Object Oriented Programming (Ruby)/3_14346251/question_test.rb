require 'test/unit'
require_relative 'singleDigit_add.rb'
require_relative 'doubleDigit_add_sub.rb'
require_relative 'singleDigit_mult_div.rb'

class Question_Test < Test::Unit::TestCase

	def setup
	end
	
	
	def test_addition
		question_test = SingleDigit_add.new(1, 2)
		assert_equal(question_test.calculate_answer, 3, "ERROR IN CALCULATE ANSWER")
		
		question_test = DoubleDigit_add_sub.new('+', 11, 29)
		assert_equal(question_test.calculate_answer, 40, "ERROR IN CALCULATE ANSWER")
		
		question_test = DoubleDigit_add_sub.new('-', 19, 11)
		assert_equal(question_test.calculate_answer, 8, "ERROR IN CALCULATE ANSWER")
		
		question_test = SingleDigit_mult_div.new('*', 3, 5)
		assert_equal(question_test.calculate_answer, 15, "ERROR IN CALCULATE ANSWER")
	end
end


