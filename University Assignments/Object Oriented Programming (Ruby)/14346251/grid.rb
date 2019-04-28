require_relative 'baseStations.rb'
require_relative 'cell.rb'

class Grid

	def initialize(dimension, base_stations)
		@dim = dimension-1
		@base_stations = base_stations
	end
	
	attr_reader :base_stations
	
	def valid_location?(cell)
		if((cell.x < @dim && cell.y < @dim) && (cell.x > -1 && cell.y > -1))
			true
		else
			false
		end
	end
	
	def process_grid_coverage
		
		num_strong = 0
		num_medium = 0
		num_weak = 0
		weak_cells = []
		
		for x in 0..@dim do
			
			for y in 0..@dim do
				current_cell = Cell.new(x,y)
				coverage = current_cell.coverage(@base_stations)
				
				case coverage
					
					when :strong then num_strong += 1
					when :medium then num_medium += 1
					when :weak then
						num_weak += 1
						weak_cells << current_cell
					end
			end
		end
		
		output = "number of cells with weak coverage: #{num_weak}\nnumber of cells with medium coverage: #{num_medium}\nnumber of cells with strong coverage: #{num_strong}\n"
		output += "WEAK CELLS: "
		weak_cells.each { |cell| output += " #{cell.to_s} " }
		
		return output
	end
	
	
	def process_route(route_cells)
	
		output = ''
		total_coverage = 0.0
		
		route_cells.each do |cell|
			output += "#{cell.coverage(@base_stations)} "
			total_coverage += cell.coverage_value
		end
		
		average_coverage = total_coverage / route_cells.length.to_f
		
		output += "\naverage coverage along route: #{average_coverage}"
		return output
	end
	
	def total_coverage_quality
		total_coverage = 0.0
		num_cells = (@dim+1)*(@dim+1)
		
		for x in 0..@dim do
			
			for y in 0..@dim do
				current_cell = Cell.new(x,y)
				coverage = current_cell.coverage(@base_stations)
				total_coverage += current_cell.coverage_value
			end
		end
		
		average = total_coverage / num_cells
		return average
	end
	
	def improve_coverage_quality
		num_stations_finished = 0
		current_stations = []
		count = 0
		
		for i in 0..@base_stations.num_stations do
			current_stations[i] = 0
		end
		
		for count in 0..@base_stations.num_stations do
			@base_stations.each_station do |station|
				num_stations_finished = 0
				x = station.cell.x - 1
				y = station.cell.y - 1
				old_cell = station.cell
				current_TCQ = total_coverage_quality
				num_fails = 0
					
				for i in x..x+2 do
					for j in y..y+2 do
						cell = Cell.new(i,j)
							
						if(!(cell == station.cell) && valid_location?(cell))
							station.update_location(cell)
							
							if(total_coverage_quality < current_TCQ)
								station.update_location(old_cell)
							else
								break
							end
						end
					end
				end
			end
		end
	end
end
