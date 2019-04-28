require_relative 'employee.rb'
require_relative 'manager.rb'


John = Employee.new('John', 40, 8)
Aoife = Manager.new('Aoife', 50, 12, 100)
puts John.to_s
puts Aoife.to_s
