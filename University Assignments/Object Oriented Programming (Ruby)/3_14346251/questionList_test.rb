require 'test/unit'
require_relative 'questionList.rb'

class QuestionList_Test < Test::Unit::TestCase

	def setup
		@test = QuestionList.new(1234)
	end
	
	def test_generate_question
		
		@test.generate_question(1, 4, 4)
		first = @test.get_question(0)
		
		assert_equal(first.calculate_answer, 8, "ERROR IN GENERATING QUESTION")
	end
end


