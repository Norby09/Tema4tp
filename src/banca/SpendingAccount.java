package banca;


/**

 * 
 * 
 * @author Norby09
 * 
 */
public class SpendingAccount extends Account {
	private double charge;
	/*
	 * 
	 * @param p
	 *            
	 * @param charge
	 * 				          
	 *            
	 */
	public SpendingAccount(Person p, double charge) {
		super(p);
		setCharge(charge);
	}
	/**
	 * @param id
	 * 			
	 * @param p
	 * 			
	 * @param charge
	 * 					
	 * @param amount
	 * 				
	 */
	public SpendingAccount(int id, Person p, double charge, double amount) {
		setID(id);
		setPerson(p);
		setAmount(amount);
		setCharge(charge);
	}

	/*
	 * 
	 * @param charge
	 * 				
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}
	
	/**
	 * 
	 * 
	 * @return the value of the charge field for this account .
	 * 
	 */
	public double getCharge() {
		return charge;
	}
}
