# Scott Kelly, 14346251
# Test class for the functionality of shop and product class
# reads in a file to create a shop using the data

require_relative 'shop.rb'
require_relative 'product.rb'


my_shop = Shop.new()
my_shop.read_file('shop.txt')
	
my_shop.print

