# Class to hold array of list objects
# depending on level of game passed in, generates
# a different type of question 

require_relative 'singleDigit_add.rb'
require_relative 'doubleDigit_add_sub.rb'
require_relative 'singleDigit_mult_div.rb'


class QuestionList
	
	def initialize(seed)
		@questions = []
		@random = Random.new(seed)
	end
	
	attr_reader :questions

	def generate_question(question_type, x = nil, y = nil)
		
		case question_type
			when 1 then	
				x ||= @random.rand(1..9)
				y ||= @random.rand(1..9)
				@questions << SingleDigit_add.new(x, y) 
			when 2 then
				x ||= @random.rand(10..99)
				y ||= @random.rand(10..99)
				@questions << DoubleDigit_add_sub.new(x, y)
			when 3 then
				x ||= @random.rand(1..9)
				y ||= @random.rand(1..9)
				@questions << SingleDigit_mult_div.new(x, y)
			else
				return nil
		end
	end
	
	def get_question(index)
		@questions[index]
	end
	
	def length
		@questions.length
	end
end
