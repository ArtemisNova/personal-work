
class BaseStations

	def initialize
		@stations = []
	end
	
	def num_stations
		@stations.length
	end
	
	def to_s
		output = ''
		@stations.each { |station| output += station.to_s }
		return output
	end
	
	def <<(station)
		@stations << station
	end
	
	def each_station
		@stations.each { |station| yield station }
	end

end
