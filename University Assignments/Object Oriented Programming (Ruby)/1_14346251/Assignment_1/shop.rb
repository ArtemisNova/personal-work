# Scott Kelly, 14346251
# Class that creates a shop environment
# Holds the products the shop has in an array, and can add new ones and 
# formats and prints the shop data 


require_relative 'product.rb'

class Shop
	
	def initialize()
		@products = []
	end
	
	def read_file(file)
		
		IO.foreach(file) do |line|
			data =line.split(' ' || '\t')
			name = data[0]
			price = data[1].to_f
			count = data[2].to_i
			@products.push(Product.new(name, price, count))
		end
	end
	
	def total_value
		total = 0
		@products.each { |product| total += product.net_price * product.count}
		return total
	end
	
	def average_value
		average = 0
		@products.each { |product| average += product.gross_price }
		average /= @products.length
		return average
	end
	
	def print # formats and prints data from the shop
		
		average = 0
		i = 0
		
		@products.sort! { |a,b| a.name <=> b.name}
		@products.each { |product| product.print }
		
		puts sprintf "\nTotal value in shop: €%.2f", total_value
		puts sprintf "Average product price: €%.2f", average_value
		puts "\n"
		
	end
	
end
