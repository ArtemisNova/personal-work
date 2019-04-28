require_relative 'person.rb'


class Employee < Person

	def initialize(name, hours_per_week, pay_per_hour)
		super(name)
		@hours_per_week = hours_per_week
		@pay_per_hour = pay_per_hour
	end
	
	def weekly_pay()
		@hours_per_week * @pay_per_hour
	end
	
	def to_s()
		return "#{@name} earns €#{weekly_pay()} a week"
	end

end
