package Demeter;

public class Demeter {
	static final String CURRENCY_SYMBOL = "€";	
	public static void main(String[] args) {
		ShopKeeper john = new ShopKeeper();
		Wallet wallet = new Wallet(95);
		System.out.println("Initial value in wallet is: " + CURRENCY_SYMBOL + wallet.getTotalMoney());
		Customer aoife = new Customer(wallet, "Aoife");
		john.chargeCustomer(aoife, 50);
		System.out.println("Value in wallet after purchase is: " + CURRENCY_SYMBOL + wallet.getTotalMoney());
	}
}

class Customer {
	public Customer(Wallet wallet, String name){
		this.name=name; //unused
		myWallet = wallet;
	}
	public String getName() {
		return name;
	}
	public  float giveMoney(float amount) {
		if(myWallet.getTotalMoney() >= amount) {
			myWallet.subtractMoney(amount);
			return amount;
		}
		else {
			return 0;
		}
	}
	private String name;
	private Wallet myWallet;
}

class Wallet {
	public  Wallet(float newValue) {
		value = newValue;
	}
	public float getTotalMoney() {
		return value;
	}
	public void addMoney(float deposit) {
		value += deposit;
	}
	public void subtractMoney(float debit) {
		value -= debit;
	}
	private float value;
}

class ShopKeeper {
	// ...
	public void chargeCustomer(Customer cust, float amount){
		float chargedAmount = cust.giveMoney(amount);
		if(chargedAmount == 0) {
			// get the baseball bat...
		}
	}
	// ...
}
