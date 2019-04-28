require 'test/unit'
require_relative 'arithmeticTutor.rb'

class ArithmeticTutor_Test < Test::Unit::TestCase

	def setup
	end
	
	def test_generate_question
		test = ArithmeticTutor.new('Scott', 10, 1234)
		length = test.questionList.length
		assert_not_equal(test.generate_question, false, "ERROR: QUESTION NOT GENERATED - ARRAY SUPPOSEDLY FULL")
		assert_equal(length+1, test.questionList.length, "ERROR IN QUESTION GENERATION: NUM OF QUESTIONS NOT INCREASED")
	end

end
