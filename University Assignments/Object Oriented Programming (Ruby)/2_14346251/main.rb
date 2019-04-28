require_relative 'kangaroo.rb'
require_relative 'zigzagaroo.rb'

input = 0
# Part 1
loop do
	puts 'Enter The Dimension of the grid (Part I) (>= 1):'
	input = gets
	input.chomp
	input = input.to_i
	break if(input >=1)
end

skippy = Kangaroo.new(4, D4, input)

loop do
	puts skippy.move
	break if skippy.reached_goal?
end

puts skippy.get_results

# Part 2
num_runs = 0

loop do
	puts "\n\nEnter The Dimension of the grid (Part II) (>=1):"
	input = gets
	input.chomp
	input = input.to_i
	puts 'Enter number of runs for part II (>= 1):'
	num_runs = gets
	num_runs.chomp
	num_runs = num_runs.to_i
	break if input >=1 && num_runs >=1
end

skippy = Kangaroo.new(4, D4, input)
zigzag = Zigzagaroo.new(8, D8, input)

skippy_hops = []
zigzag_hops = []

# Running the loop for comparison
for i in 0..num_runs-1 do
	
	loop do
		skippy.move
		break if skippy.reached_goal?
	end
	skippy_hops[i] = skippy.num_hops
	
	loop do
		zigzag.move
		break if zigzag.reached_goal?
	end
	zigzag_hops[i] = zigzag.num_hops
	
	puts "Run #{i+1}: Kangaroo #{skippy_hops[i]} hops, Zigzagaroo #{zigzag_hops[i]} hops"
end

skippy_average = skippy_hops.inject { |sum, el| sum + el }.to_f / skippy_hops.size

zigzag_average = zigzag_hops.inject { |sum, el| sum + el }.to_f / zigzag_hops.size

puts "\n\nOn average Kangaroo took #{skippy_average} hops, Zigzagaroo took #{zigzag_average} hops"
