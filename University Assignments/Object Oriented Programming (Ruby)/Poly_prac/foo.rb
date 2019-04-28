class Test
	def foo
		puts 'foo'
		foobar
	end
	
	def foobar
		puts 'foobar'
	end
end

class NoFoobar < Test
	def foobar
	end
end


test = NoFoobar.new

test.foo
