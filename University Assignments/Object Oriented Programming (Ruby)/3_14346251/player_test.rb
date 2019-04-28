require 'test/unit'
require_relative 'player.rb'
require_relative 'question.rb'

class Player_test < Test::Unit::TestCase

	def setup
		@operator = '+'
		@test_player = Player.new('John')
	end
	
	def test_accessor
		question_list = QuestionList.new(1234)
		
		question_list.questions << SingleDigit_add.new(1, 3)
		@test_player.answers << 4
		
		question_list.questions << SingleDigit_add.new(6, 6)
		@test_player.answers << 12
		
		assert_equal(@test_player.answers[0], 4, "ERROR IN ANSWER STORAGE")
		assert_equal(@test_player.answers[1], 12, "ERROR IN ANSWER STORAGE")
	end
end
