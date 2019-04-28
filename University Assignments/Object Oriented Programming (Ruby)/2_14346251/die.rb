# class which does the RNG of the dice as well as holding the rolling stats

require_relative 'constants.rb'
require_relative 'rollstats.rb'

class Die
	
	def initialize(sides, possible_directions)
		@directions = possible_directions
		@sides = sides
		@random = Random.new
		@num_rolls = Hash.new
		@total_throws = 0
		
		# adding each die side as a new entry in the table
		for i in 0..@sides-1
			@num_rolls[@directions[i]] = 0
		end
		
	end
	
	def roll
		result = @random.rand(0..@sides-1)
		record_result(result)
		@total_throws += 1
		return @directions[result]
	end
	
	def record_result(result)
		if(result > -1 && result < @sides)
			@num_rolls[@directions[result]] = @num_rolls[@directions[result]] + 1 # incrementing the hash's value
		end
	end
	
	def stats_as_string
		die_stats = RollStats.new(@num_rolls, @total_throws)
		return die_stats.to_s
	end
end
