require 'test/unit'
require_relative 'shop.rb'


class Shop_test < Test::Unit::TestCase

	def setup
		@shop_test = Shop.new
		@shop_test.read_file('shop.txt')
	end
	
	def test_average
		assert_equal(@shop_test.average_value, 68, 'Average fail')
	end
	
	def test_total
		assert_equal(@shop_test.total_value, 10600, 'total fail')
	end
end
