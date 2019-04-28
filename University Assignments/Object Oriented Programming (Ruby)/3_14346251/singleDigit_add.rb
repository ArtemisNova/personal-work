require_relative 'question.rb'

class SingleDigit_add < Question

	def initialize(x, y)
		super('+', x, y)
	end
	
	def calculate_answer
		@x + @y
	end
end
