package banca;


/**
 * Aceasta clasa este Aceasta pentru a creea un obiect de tip Account.Acest obiect 
 * are un obiect de tip Person asociat lui.Sunt trei atribute: persoana, suma de bani si un id. 
 *  
 * @author Norby09
 * 
 */
public class Account {
	private Person person;
	private int id;
	private static int nr = 1;
	private double amount;

	/**
         * Acesta este contructorul pentru obiecte de tip Account.Fiecare cont
         * va primi un id si un proprietar, folosind acest constructor.
         * Id-ul este obtinut prin incrementarea unei variabile.
	 * 
	 * @param p
         *            persoana care va fi asociata noului cont.
	 */
	public Account(Person p) {
		generateID();
		setPerson(p);
	}
	/**
         * Al doilea constructor este folosit pentru ca cele doua clase care
         * mostenesc clasa trebuie sa isi defineasca propriul constructor pentru 
         * a fi mai explicit.
	 */
	public Account() {

	}
	/**
         * Aceasta metoda este folosita pentru a genera id pentru conturile nou create si 
         * este apelata in primul constructor al clasei
	 */
	public void generateID() {
		this.id = (int) nr++;
	}
	/**
         * Aceasta metoda este folosita pentru a returna numarul de conturi 
         * care sunt create si puse in hash table.
	 * 
	 * @return numarul total de conturi care sunt in banca
	 */
	public static int getNrOfAccounts() {
		return (int) nr;
	}

	/**
         * Aceasta metoda este folosita pentru a seta numarul de conturi de la banca la 
         * o anumita valoare, care este reprezentata de catre i parametrul.

	 * @param i             valoarea la care numarul de conturi va fi setat
	 * 			
	 */
	public static void setNrOfAccounts(int i) {
		nr = i;
	}
	/**
         * Aceasta metoda este folosita pentru a seta ID-ul de cont pentru o
         * anumita valoare, care este reprezentat de parametrul id.
	 * 
	 * @param id
	 * 			valoarea specifica pentru care id-ul de cont va fi stabilit.
	 */
	public void setID(int id) {
		this.id = id;
	}
	
	/**
	 * Aceasta metoda este folosita pentru a seta proprietarul contului, ca fiind persoana
         * data ca parametru p.
	 * 
	 * @param p
	 * 			persoana care va fi asociata cu acest cont.
	 */
	public void setPerson(Person p) {
		this.person = p;
	}
	

	/**
	 * Aceasta metoda este folosita pentru a returna id-ul contului .
	 * 
	 * @return id-ul contului.
	 */
	public int getID() {
		return id;
	}

	/**
	 * Aceasta metoda este folosita pentru a depune bani in cont. Cantitatea de
         * bani care va fi depusa este reprezentata de parametrul value.
	 * 
	 * @param value
	 * 				cantitatea de bani care va fi depusa in cont.
	 */
	public void deposit(double value) {
		this.amount += value;
	}
	/**
	 * Aceasta metoda este folosita pentru a returna suma de bani care 
         * este depusa in cont.
	 * 
	 * @return suma de bani continuta de cont .
	 */
	public double getAmount() {
		return this.amount;
	}
	
	/**
	 * Aceasta metoda este folosita pentru a obtine numele
         * persoanei careia ii este asociat contul.
	 * 
	 * @return numele persoane properietar a contului
	 */
	public String getPerson() {
		return person.getName();
	}
	/**
         * Aceasta metoda este folosita pentru a stabili suma de bani din acest cont
         * la o anumita valoare, care este dat ca parametru.
	 * @param money
	 * 				valoarea la care suma de bani din cont va fi setata
	 */
	public void setAmount(double money) {
		this.amount = money;
	}
	/**
         * Aceasta metoda este folosita in scopul de a retrage bani dintr-un cont de tip "Saving Account"
         * Suma de bani care va fi retrasa este reprezentata de parametrul amount.
         * Metoda returneaza true in caz de succes si false in cazul in care suma retrasa este 
         * mai mare decat suma de bani disponibila in cont.
	 * 
	 * @param amount
	 * 				suma de bani care este retrasa din cont .
	 * @return	true in caz de succes si false in caz de insucces .
	 */
	public boolean retire(double amount) {
		boolean sufficient = true;
		if (amount < getAmount()) {
			setAmount(getAmount() - amount);
		} else {
			sufficient = false;
		}
		return sufficient;
	}
	
	/**
	 * Aceasta metoda este folosita in scopul de a retrage bani dintr-un cont de tip "Spending Account"
         * Suma de bani care va fi retrasa este reprezentata de parametrul amount.
         * Metoda returneaza true in caz de succes si false in cazul in care suma retrasa este 
         * mai mare decat suma de bani disponibila in cont.
	 * 
	 * @param amount
	 * 				suma de bani care este retrasa din cont .
	 * @return	true in caz de succes si false in caz de insucces .
	 */
	public boolean retireSpending(double charge, double amount) {
		boolean sufficient = true;
		if (amount < getAmount()-amount*charge) {
			setAmount(getAmount() - amount - amount * charge);
		} else {
			sufficient = false;
		}
		return sufficient;
	}

}
