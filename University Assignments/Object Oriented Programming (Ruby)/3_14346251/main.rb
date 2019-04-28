require_relative 'arithmeticTutor.rb'

lines = []
File.open("tutor.dat", "r") do |f|
	f.each_line do |line|
		lines << line
	end
end

tutor = ArithmeticTutor.new(lines[0].chomp, lines[1].chomp.to_i, lines[2].chomp.to_i)

puts "Welcome to Arithmetic Tutor, #{lines[0].chomp}!"
puts "You will be asked #{tutor.num_questions} questions here today.\n\n"


for i in 0..tutor.num_questions-1 do
	tutor.generate_question
	print tutor.questionList.get_question(i).to_s
	begin
		input = gets
		input.chomp!
		input = Integer(input)
	rescue
		puts "\nPlease enter an integer: "
		retry
	end
	print tutor.record_answer(input)
end

puts tutor.to_s
