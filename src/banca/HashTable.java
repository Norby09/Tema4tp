package banca;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @invariant isWellFormed()
 * @author Norby
 */
public class HashTable {

    private ArrayList<Account>[] table;
    private int size;

    /**
     * Acesta este contructorul pentru obiecte de tip HashTable.Hash table este de fapt un array
     * de Array List.Numarul de array list este reprezentat de campul size.In acest constructor
     * noul hash table este creat si array lists sunt initializate.Va contine obiecte de tip Account.
     * 
     * @param dimension
     *            		numarul de array list continut de hash table .
     */
    public HashTable(int dimension) {
        this.size = dimension;
        table = new ArrayList[size];
        for (int i = 0; i < size; i++) {
            table[i] = new ArrayList<Account>();
        }

    }

    /**
     * Aceasta metoda este folosita pentru a returna marimea hash table-ului, care este
     * echivalenta cu numarul de array list continute.
     * 
     * @return dimensiunea hash table-ului .
     */
    public int getSize() {
        return size;
    }

    /**
     * Aceasta metoda este folosita pentru a adauga un obiect de tip Account in hash table
     *
     * @pre value != null
     * @pre key > 0
     * @post getWholeSize() = getWholeSize()@pre + 1
     * @post value == table[hash].get(table[hash].size() - 1)
     * 
     */
    public void addValue(Account value, int key) {
        assert isWellFormed();
        assert value != null;
        assert key > 0;

        //ArrayList ar =new ArrayList();
        int firstSize = getWholeSize();
        int hash = (key % size);
        if (table[hash] != null) {
            ArrayList ar = getList(hash);
            ar.add(value);
        } else {
            ArrayList ar = new ArrayList();
            setArrayList(ar, hash);
            ar.add(value);
        }


        table[hash].add(value);
        int secondSize = getWholeSize();




        assert secondSize == firstSize + 1;
        assert value == table[hash].get(table[hash].size() - 1);
        assert isWellFormed();
    }

    /**
     * Aceasta metoda este folosita pentru a sterge un obiect de tip Account
     * din hash table .
     * 
     * @pre getWholeSize() > 0
     * @pre isContained(id)
     * @post size() == size()@pre - 1;
     * @post !isContained(id)
     */
    public void remove(int id) {
        assert isWellFormed();
        assert getWholeSize() > 0;
        assert isContained(id);

        int firstSize = getWholeSize();
        int hash = id % size;
        int pos = id / size;
        ArrayList ar = getList(hash);
        for (int i = 0; i < ar.size(); i++) {
            Account ac = (Account) ar.get(i);
            if (ac.getID() == id) {
                ar.remove(i);
            }
        }
        
        int secondSize = getWholeSize();

        assert firstSize == secondSize - 1;
        assert !isContained(id);
        assert isWellFormed();
    }

    /**
     * Metoda este folosita pentru a seta toate valorile din array list 
     * la o valoare specifica, continuta intr-un array list.Metoda inlcuieste
     * un anumit array list din hash table cu un alt array list.
     * 
     * @param Arr 
     * 				
     * @param pos
     * 				
     */
    public void setArrayList(ArrayList<Account> Arr, int pos) {
        table[pos] = Arr;
    }

    /**
     * Aceasta metoda este folosita pentru a returna lungimea unui array list
     * care se gaseste la o anumita pozitie.
     * 
     * @param key
     * 			pozitia array list .
     * @return	lungimea array list .
     */
    public int getLength(int key) {


        int length = 0;
        Account current;
        if (table[key] == null) {
            return 0;
        } else {
            Iterator<Account> it = table[key].iterator();
            while (it.hasNext()) {
                current = it.next();
                length++;
            }
        }
        return length;
    }

    /**
     * Metoda este folosita pentru a returna intreaga dimensiune a hash table-ului
     * 
     * @return toata dimensiunea hash table-ului .
     */
    public int getWholeSize() {
        int wholeSize = 0;
        for (int i = 0; i < size; i++) {
            wholeSize += getLength(i);
        }
        return wholeSize;
    }

    /**
     * Aceasta metoda este folosita pentru a returna un array list amplasat la o anumita
     * pozitie in hash table
     * 
     * @param key
     * 			folosit pentru a calcula pozitia unui array list in hash table .
     * @return	array list amplasat la pozitia calculata .
     */
    public ArrayList<Account> getList(int key) {
        int hash = key % size;
        ArrayList<Account> list = new ArrayList<Account>();
        Iterator<Account> iterator = table[hash].iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    /**
     * Este o metoda de test, pentru a vedea daca hash table-ul este construit
     * bine sau nu.Testeaza cateva caracteristici de baza ale hash table-ului, si 
     * daca nu sunt erori va returna true.
     * 
     * @return true in caz ca hash table-ul este construit corect si false daca nu .
     */
    public boolean isWellFormed() {
        int dimension = 0;
        boolean ok = true;
        Account current;
        for (int i = 0; i < size; i++) {
            dimension += table[i].size();
        }
        for (int i = 0; i < size; i++) {
            Iterator<Account> it = table[i].iterator();
            while (it.hasNext()) {
                current = it.next();
                if (current.getID() % size != i) {
                    ok = false;
                }
            }
            if (dimension != getWholeSize()) {
                ok = false;
            }
        }
        return ok;
    }

    /**
     * Aceasta metoda este folosita pentru a verifica daca un cont dat de id-ul
     * lui este continut sau nu de hash table.
     * 
     * @param id
     * 			id-ul contului cautat .
     * @return true daca hash table-ul contine contul cautat si false daca nu .
     */
    public boolean isContained(int id) {
        Account current;
        int hash = id % size;
        boolean ok = false;
        if (table[hash] != null) {
            ArrayList<Account> entries = table[hash];
            Iterator<Account> it = entries.iterator();
            while (it.hasNext()) {
                current = it.next();
                if (current.getID() == id) {
                    ok = true;
                }
            }
        }
        return ok;
    }

    /**
     * 
     * Aceasta metoda este folosita in scopul de a verifica daca hash table-ul este gol sau nu.
     * 
     * @return true in cazul in care este gol si false in cazul in care nu este gol .
     */
    public boolean isEmpty() {
        boolean ok = true;
        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                ok = false;
            }
        }
        return ok;
    }
}
