# class to calculate and format dice roll stats

class RollStats
	
	def initialize(data, num_rolls)
		@data = Hash.new
		@data = data
		@number_rolls = num_rolls
		@percent_data = Hash.new
	end
	
	def calc_percentage(num, total)
		return (num.to_f / total.to_f) * 100
	end	
	
	def calc_data
		
		@data.each do |key, value|
			percentage = calc_percentage(value.to_i, @number_rolls)
			@percent_data[key] = percentage.round(2).to_s + '%'
		end
		
	end
	
	def to_s
			calc_data()
			output = "\nDie Statistics\n"
			output += "Total #throws: #{@number_rolls}\n"
			
			@percent_data.each do |key, value|
				output += "#{key}: #{value} "
			end
			
			return output
	end
end
