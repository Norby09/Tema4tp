package banca;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Aceasta clasa este folosit pentru a crea un obiect de tip Bank. Acest obiect a
 * un obiect de tip Hash tabel asociate acestuia. Acesta are doua atribute:  hash tabel
 * si dimensiunea tabelului hash.
 * 
 * 
 * @author Norby
 * 
 */
public class Bank implements BankProc {

    private HashTable accounts;
    private int size;
    //private Account a;

    /**
     * Acesta este constructorul pentru obiectele de tip Bank. instanta acestei
     * clase este creata doar o singura data. Atunci cand obiectul este creat,
     * o dimensiune este atribuita continutului hash. 
     * 
     * @param size
     *            dimensiunea hash table .
     */
    public Bank(int size) {
        this.size = size;
        accounts = new HashTable(size);
    }

    /**
     * Metoda folosita pentru a returna dimensiunea hash tabel
     * 
     * @return dimensiunea hash tabel .
     */
    public int getSize() {
        assert accounts.isWellFormed();

        return size;
    }

    /**
     * Aceasta metoda este folosita in scopul de a adauga un cont in tabelul hash. Se calculeaza
     * pozitia in cazul in care contul va fi adaugat, si in cazul in care exista alte elemente
     * deja adaugat la aceasta pozitie, adauga cont nou la sfarsitul unui array
     * lista.
     * 
     * @param a
     * 		contul va fi adaugat in hash tabel .
     * 
     */
    public void addAccount(Account a) {
        assert a != null;
        assert !accountExists(a.getID());

        int id = a.getID();
        accounts.addValue(a, id);

        assert accountExists(a.getID());
    }

    /**
     * Metoda folosita pentru a returna hash table
     * 
     * @return hash cu obiecte de tip account .
     */
    public HashTable getAccounts() {
        assert accounts.isWellFormed();

        return accounts;
    }

    /**
     * Aceasta metoda este folosita pentru a verifica daca un cont este stocat sau nu in hash table.
     * Daca este stocat returneaza true, altfel returneaza false .
     * 
     * @param id
     * 			id-ul contului care va fi cautat daca exista sau nu
     * @return true in caz ca, contul este gasit, false in caz ca nu este gasit . 
     * 
     */
    public boolean accountExists(int id) {
        Account current;
        boolean ok = false;
        if (accounts.getList(id) != null) {
            ArrayList<Account> entries = accounts.getList(id);
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
     * Aceasta metoda este folosita pentru a obtine un array list sortat,
     * dintr-o pozitie a hash tabel care este calculata
     * 
     * @param id	
     * 			id-ul contului care va fi folosit cand se va calcula pozitia 
     * listei in hash table .
     * 
     * @return array list de la pozitia calculata avand elementele sortate dupa id
     */
    public ArrayList<Account> getOrderedList(int id) {
        assert accounts.isWellFormed();

        ArrayList<Account> preOrder = accounts.getList(id);
        if (!preOrder.isEmpty()) {
            int s = preOrder.size();
            for (int i = 0; i < s - 1; i++) {
                for (int m = i + 1; m < s; m++) {
                    if (preOrder.get(i).getID() > preOrder.get(m).getID()) {
                        Account aux = preOrder.get(i);
                        preOrder.set(i, preOrder.get(m));
                        preOrder.set(m, aux);
                    }
                }
            }
        }
        return preOrder;
    }

    /**
     * Aceasta metoda este folosita pentru a sterge un cont din hash table,
     * care este dat prin id.
     * 
     * @param id
     * 			id-ul contului care va fi sters .
     * 
     */
    public void removeAccount(int id) {

        assert accountExists(id);

        ArrayList<Account> current;
        current = getOrderedList(id);
        int pos = id / size;
        current.remove(pos);
        int poz = id % size;
        accounts.setArrayList(current, poz);

        assert !accountExists(id);
    }

    /**
     * Aceasta metoda este folosita pentru a depune o anumita suma de bani 
     * intr-un cont dat de id.
     * 
     * @param id
     * 			id-ul contului in care banii vor fi depusi
     * @param money 
     * 				suma de bani care va fi depusa in cont .
     */
    public void depositMoney(int id, double money) {

        assert accountExists(id);
        assert money > 0;

        ArrayList<Account> current;
        current = getOrderedList(id);
        int pos = id / size;
        current.get(pos).deposit(money);
    }

    /**
     * Aceasta metoda este folosita pentru a retrage o anumita suma de bani 
     * intr-un cont dat de id.
     * 
     * @param id
     * 			id-ul contului din care suma de bani va fi retrasa .
     * @param money
     * 				suma de bani care va fi retrasa din cont .
     */
    public void retireMoney(int id, double money) {

        assert accountExists(id);
        assert money > 0;

        ArrayList<Account> current;
        current = getOrderedList(id);
        int pos = id / size;
        if (current.get(pos) instanceof SpendingAccount) {
            current.get(pos).retireSpending(
                    ((SpendingAccount) current.get(pos)).getCharge(), money);
        } else if (current.get(pos) instanceof SavingAccount) {
            current.get(pos).retire(money);
        }
    }
//    public boolean isWellFormed() {
//       boolean ok;
//       if(a.getAmount()>0)
//           ok=false;
//       else
//           ok=true;
//        return ok;
//    }
}
