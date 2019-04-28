require_relative 'baseStation.rb'
require_relative 'baseStations.rb'
require_relative 'grid.rb'


base_stations = BaseStations.new()
grid = Grid.new(21, base_stations)
route_cells = []

IO.foreach('base_stations.txt') do |line|
	data =line.split(' ' || '\t')
	name = data[0]
	x = data[1].to_i
	y = data[2].to_i
	station = BaseStation.new(name, x, y)
	grid.base_stations << station
end
		
IO.foreach('route.txt') do |line|
	data =line.split(' ' || '\t')
	x = data[0].to_i
	y = data[1].to_i
	route_cells << Cell.new(x,y)
end

puts "Part 1: List of Base Stations:\n"
puts grid.base_stations.to_s
puts "\nPart 2: Processing coverage for the whole grid\n"
puts grid.process_grid_coverage
puts "\nPart 3: Processing the route outlined in route.txt\n"
puts grid.process_route(route_cells)
puts "\nPart 4: Total Coverage Quality"
puts "Initial TCQ: #{grid.total_coverage_quality}"
grid.improve_coverage_quality
puts "  Final TCQ: #{grid.total_coverage_quality}"
puts "Final Base Station locations :\n #{grid.base_stations.to_s}"


