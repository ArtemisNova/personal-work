require_relative 'question.rb'

class DoubleDigit_add_sub < Question

	def initialize(operator = nil, x, y)
		random = Random.new()
		
		if(operator == nil)
			@i = random.rand(1..2)
		
			if(@i == 1)
				super('+', x, y)
			else
				super('-', x, y)
			end
		else
			super(operator, x, y)
		end
	end
end
