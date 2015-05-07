package banca;

/**
 * Aceasta clasa este utilizata in scopul de a crea un obiect de tipul person, care va fi
 * asociat intr-un cont.
 * 
 * @author Norby09
 *
 */
public class Person {
	private String name;
	private static long nr = 0;
	private int id;

	/**
	 * 
         * Atribuie fiecarei persoane nou create un nume si un id.
         * ID-ul este obtinut prin incrementarea variabilei nr.
	 * 
	 * @param nume
	 * 				numele care va fi atribuit persoanei .
	 */
	public Person(String nume) {
		nr++;
		this.id = (int) nr;
		this.name = nume;
	}

	/**
	 * 
	 * 
	 * @return the name of the person .
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return 
	 */
	public int getID() {
		return this.id;
	}
	/**
	 * 
	 * @return 
	 */
	public long getNrOfPersons() {
		return nr;
	}
	
	/**
	 * 
	 * @param i
	 * 			
	 */
	public void setNrOfPersons(long i) {
		nr = i;
	}
}
