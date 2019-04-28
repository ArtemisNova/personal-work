require_relative 'cell.rb'

class BaseStation
	
	def initialize(name, x, y)
		@name = name
		@cell = Cell.new(x,y)
	end
	
	attr_reader :name
	attr_reader :cell
	
	def to_s
		"#{@name} #{@cell.to_s} "
	end
	
	def update_location(new_cell)
		@cell = new_cell
	end

end
