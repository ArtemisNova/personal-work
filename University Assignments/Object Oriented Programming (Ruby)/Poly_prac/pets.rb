# Part 1
# The Pets do not need a common superclass as there is no types
# in Java, you have to as to put them in the same array they must inherit from same class

# Part 2
# we use inheritance cos it cuts duplicate code in Gerbil, and allows fox to work
# without having to be fully implemented

class Pet
	def speak
		puts 'noise'
	end
	
	def daily_routine
		puts "\n\nI am a #{self.class.name}"
		puts "I eat #{eat}"
		
		for i in 0..2 do
			speak
		end
		
		puts "I sleep in my #{sleep}"
	end
end

class Cat < Pet
	def speak
		puts 'Meeow!'
	end
	
	def eat
		return 'fish'
	end
	
	def sleep
		return 'fluffy bed'
	end
end

class Dog < Pet
	def speak
		puts 'Woof!'
	end
	
	def eat
		return 'meat'
	end
	
	def sleep
		return 'kennel'
	end
end

class Mouse < Pet
	def speak
		puts 'squeak!'
	end
	
	def eat
		return 'cheese'
	end
	
	def sleep
		return 'cage'
	end
end

class Gerbil < Mouse
	
	def eat
		return 'seeds'
	end
end

class Fox < Pet
	def eat
		return 'rabbits :('
	end
	
	def sleep
		return 'den'
	end
end

class PetLover

	def initialize
		rand_num = Random.new
		@pets = []
		
		for i in 0..19 do
			num = rand_num.rand(0..4)
			@pets <<
			case num
			when 0 then Cat.new
			when 1 then Dog.new
			when 2 then Mouse.new
			when 3 then Gerbil.new
			when 4 then Fox.new
			else puts 'RAND NUM OUT OF RANGE'
			end
		end
	end
	
	def pets_speak
		@pets.each { |p| p.speak }
	end
	
	def daily_routine
		@pets.each { |p| p.daily_routine }
	end
end



Jim = PetLover.new

Jim.daily_routine
