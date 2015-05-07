package banca;


/**

 * 
 * 
 * @author Norby09
 * 
 */
public class SavingAccount extends Account {
	// TODO: interest (dobanda)
	private double minimumDeposit;

	/**
	 * 
	 * 
	 * @param p
	 * @param minDeposit
	 * 					           
	 *            
	 */
	public SavingAccount(Person p, double minDeposit) {
		super(p);
		this.minimumDeposit = minDeposit;

	}
	
	/**
         * 
	 * @param id
	 * 			
	 * @param p
	 * 			
	 * @param minDeposit
	 * 					
	 * @param amount
	 * 				
	 */
	public SavingAccount(int id, Person p, double minDeposit, double amount) {
		setID(id);
		setPerson(p);
		this.minimumDeposit = minDeposit;
		setAmount(amount);
	}
	/**
	 * 
	 * @return 
	 */
	public double getMinimumDeposit() {
		return minimumDeposit;
	}

}
