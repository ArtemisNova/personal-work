# Scott Kelly, 14346251
# Product class creates and holds data in relation to one product, as well
# as the quantity of the product
# can show net price as well as calculate gross price

VAT = 0.2 # constant as VAT does not change

class Product

	def initialize(name, price, count)
		@name = name
		@net_price = price
		@count = count
	end
	
	attr_accessor :count
	attr_accessor :name
	attr_accessor :net_price
	
	def gross_price
		@net_price + @net_price * VAT
	end
	
	def print
		puts sprintf "#{@name}\t€%.2f", gross_price
	end
end
