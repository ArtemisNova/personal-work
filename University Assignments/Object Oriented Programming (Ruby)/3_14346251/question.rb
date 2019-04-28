# Class to represent basic question to inherit 
# specific type of question 

class Question

	def initialize(operator, operand1, operand2)
		@x = operand1
		@y = operand2
		@operator = operator
	end
	
	def calculate_answer
		@x.send(@operator, @y)
	end
	
	def to_s
		"What is #{@x} #{@operator} #{@y}? " 
	end
	
end

