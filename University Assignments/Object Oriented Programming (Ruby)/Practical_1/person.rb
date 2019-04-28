
class Person
	
	def initialize(name)
		@name = name
		@age = 0
	end
	
	def to_s
		puts "#{@name} is #{@age} years old"
	end
	
	def increment_age
		@age += 1
		if @age == 13
			puts "I'm a teenager!"
		elsif @age == 18
			puts "I'm an adult!"
		end
	end
		
end	


Jim = Person.new('Jim')

20.times do
	Jim.to_s
	Jim.increment_age
end
