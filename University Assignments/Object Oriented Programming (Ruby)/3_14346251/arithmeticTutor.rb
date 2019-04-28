# Class where main "game" data is handled
# holds the player and questionList class
# deals with the game level, and printing
# out the final results 

require_relative 'questionList.rb'
require_relative 'player.rb'


class ArithmeticTutor

	def initialize(name, num_questions, seed)
		@level_of_game = 1
		@questions_right = 0
		@num_questions = num_questions
		@player = Player.new(name)
		@questionList = QuestionList.new(seed)
	end
	
	attr_reader :num_questions
	attr_reader :questionList
	
	
	def generate_question
		if(@questionList.length > @num_questions-1)
			return false
		end
		i = 0
		@questionList.generate_question(@level_of_game)
	end
	
	
	
	def to_s
		output = "\nResults!!:\n\nCORRECT ANSWERS:\n"
		correct = ''
		incorrect = "\nINCORRECT ANSWERS:\n"
		
		i = -1
		@questionList.questions.each do |question|
			i = i + 1
			if(question.calculate_answer == @player.answers[i])
				correct += question.to_s
				correct += "  CORRECT\n"
			else	
				incorrect += question.to_s
				incorrect += "  INCORRECT; Answer: #{question.calculate_answer}, Entered: #{@player.answers[i]}\n"
			end
		end
		
		output += correct + incorrect
		output += "\n#{@player.name}'s final score is: #{@player.score}"
		return output
	end
	
	
	
	def record_answer(answer)
		@player.answers << answer
		
		if(answer == @questionList.questions[@questionList.length-1].calculate_answer)
			
			update_player_score
			
			if(@questions_right < 0)
				@questions_right = 0
			end
			@questions_right += 1
		else
			if(@questions_right > 0)
				@questions_right = 0
			end
			@questions_right -= 1
		end
		
		set_level_of_game(answer)
	end
	
	
private
	
	def update_player_score
		@player.score += @level_of_game
	end
	
	
	
	def set_level_of_game(answer)
		output = ''
		
		if(@questions_right > 1 && @level_of_game < 3)
			@level_of_game += 1
			@questions_right = 0
			output = "\nPROMOTED TO LEVEL #{@level_of_game}\n\n"
		end
		if(@questions_right < -1 && @level_of_game > 1)
			@level_of_game -= 1
			@questions_right = 0
			output = "\nDEMOTED TO LEVEL #{@level_of_game}\n\n"
		end
		
		return output
	end
	

end
