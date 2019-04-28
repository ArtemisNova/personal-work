require_relative 'employee.rb'

class Manager < Employee

	def initialize(name, hours_per_week, pay_per_hour, bonus)
		super(name, hours_per_week, pay_per_hour)
		@bonus = bonus
	end
	
	def weekly_pay()
		super() + @bonus
	end
	
	def to_s()
		return "#{super()} , which includes a bonus of €#{@bonus}"
	end
	
end
