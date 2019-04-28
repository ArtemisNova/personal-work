class Cell
	
	def initialize(x,y)
		@x = x
		@y = y
		@coverage_value = 0.0
	end
	
	attr_reader :x, :y, :coverage_value
	
	def to_s
		"(#{@x},#{@y})"
	end
	
	def coverage(stations)
		best_coverage = 0.0
		
		stations.each_station do |station|
		
			if(best_coverage == 0.0)
				best_coverage = 1/(1 + distance(station.cell))
			else
				current_coverage = 1/(1 + distance(station.cell))
				
				if(best_coverage < current_coverage)
					best_coverage = current_coverage
				end
			end
		end
		
		@coverage_value = best_coverage
		
		if(best_coverage > 0.3)
			coverage = :strong
		end
		if(best_coverage <= 0.3 && best_coverage > 0.1)
			coverage = :medium
		end
		if(best_coverage <= 0.1)
			coverage = :weak
		end
		
		return coverage
	end
	
	def distance(other_cell)
		
		x1 = @x.to_f + 0.5
		x2 = other_cell.x.to_f + 0.5
		y1 = @y.to_f + 0.5
		y2 = other_cell.y.to_f + 0.5
		
		a = (x2 - x1) * (x2 - x1)
		b = (y2 - y1) * (y2 - y1)
		
		return Math.sqrt(a + b)
	end
	
	def ==(other)
		if(@x == other.x && @y == other.y)
			true
		else
			false
		end
	end
end
