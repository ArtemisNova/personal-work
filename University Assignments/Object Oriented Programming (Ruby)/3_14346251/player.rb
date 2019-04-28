# Class to hold player name and answers to question
# the index of the answer array i corresponds to the
# i'th questions

require_relative 'questionList.rb'

class Player

	def initialize(name)
		@answers = []
		@name = name
		@score = 0
	end
	
	attr_accessor :answers
	attr_reader :name
	attr_accessor :score
end
