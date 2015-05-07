package banca;


import java.util.ArrayList;
/**
 * Aceasta este interfata pusa in aplicare de catre clasa Bank. Toate
 * metodele abstracte din aceasta categorie trebuie sa fie redefinite in clasa Bank.
 * 
 * @author Norby
 */
public interface BankProc {

	/**
	 * @pre a != null
	 * @pre !accountExists(a.getID())
	 * @post !isEmpty()
	 * @post a.size() = a.@pre.size()+1
	 * @post accountExists(a.getID())
	 */
	public void addAccount(Account a);

	/**
	 * @pre !isEmpty()
	 * @pre accountExists(a.getID())
	 * @post Account.size() = Account@pre.size()-1
	 * @post !accountExists(a.getID())
	 */
	public void removeAccount(int id);

	/**
	 * @pre !isEmpty()
	 * 
	 */

	public HashTable getAccounts();

	/**
	 * @pre !isEmpty()
	 * @pre accountExists(id)
	 * @pre money > 0
	 * @post account.getAmount() == account@pre.getAmount() + amount
	 */
	public void depositMoney(int id, double money);

	/**
	 * @pre !isEmpty()
	 * @pre accountExists(id)
	 * @pre money > 0
	 * @post account.getAmount() == account@pre.getAmount() - amount
	 */
	public void retireMoney(int id, double money);

	public boolean accountExists(int id);

	/**
	 * @pre !isEmpty()
	 * @pre accountExists(id)
	 * 
	 */
	public ArrayList<Account> getOrderedList(int id);

}
