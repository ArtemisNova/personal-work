require 'test/unit'
require_relative 'product.rb'

class Product_test < Test::Unit::TestCase

	def setup
		@test = Product.new('Scarf', 39.99, 15)
	end
	
	def	test_name
		name = @test.instance_variable_get(:@name)
		assert(name == 'Scarf', "Error in Name: expected Scarf, got #{name}")
	end
	
	def	test_price
		price = @test.instance_variable_get(:@net_price)
		assert(price == 39.99, "Error in net_price: expected 39.99, got #{price}")
	end
	
	def	test_count
		count = @test.instance_variable_get(:@count)
		assert(count == 15, "Error in count: expected 15, got #{count}")
	end
	
end
