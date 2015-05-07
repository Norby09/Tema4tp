package banca;


import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Aceasta clasa reprezinta GUI (interfata grafica) a aplicatiei.
 * Utilizatorul va interactiona cu aplicatia folosind aceasta interfata. Aceasta contine
 * meniul cererii sale cu butoane si afiseaza rezultatele comenzilor utilizatorilor.
 * 
 * @author Norby09
 * 
 * 
 */
public class MyFrame extends JFrame implements ActionListener {

	private Account account = new Account();
	private Person person;
	private Bank bank = new Bank(10);
	private JTable table;
	private JMenuBar menu = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenu accountMenu = new JMenu("Account");

	private JMenuItem save = new JMenuItem("Save Data");
	private JMenuItem load = new JMenuItem("Load Data");

	private JMenuItem view = new JMenuItem("View Accounts");
	private JMenuItem newAccount = new JMenuItem("New Account");
	private JMenuItem removeAccount = new JMenuItem("Remove Account");
	private JMenuItem depositMoney = new JMenuItem("Deposit Money");
	private JMenuItem retireMoney = new JMenuItem("Retire Money");

	private JPanel panel = new JPanel();

	/**
         * Constructorul pentru obiectele de tip MyFrame.  
	 */
	public MyFrame() {
		super("Bank system");
		setSize(610, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel.setLayout(null);

		fileMenu.add(save);
		fileMenu.add(load);
		load.addActionListener(this);
		save.addActionListener(this);

		accountMenu.add(view);
		accountMenu.add(newAccount);
		accountMenu.add(removeAccount);
		accountMenu.add(depositMoney);
		accountMenu.add(retireMoney);
		view.addActionListener(this);
		newAccount.addActionListener(this);
		removeAccount.addActionListener(this);
		depositMoney.addActionListener(this);
		retireMoney.addActionListener(this);

		menu.setBounds(0, 0, 610, 20);
		menu.add(fileMenu);
		menu.add(accountMenu);

		this.add(menu);
		this.add(panel);
		setVisible(true);

	}

	/**
         * Aceasta metoda a interfetei ActionListener este definita aici.
         * 
	 * @param e
	 *             ActionEvent object trimis de actiunile utilizatorului
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == view) {
			int totalSize = 0;
			for (int i = 0; i < bank.getSize(); i++) {
				totalSize += bank.getAccounts().getLength(i);
			}
			Object[][] objects = new Object[totalSize][5];
			ArrayList<Account> entries = new ArrayList<Account>();
			Account current;
			int j = 0;
			int[] o = new int[100];
			for (int i = 0; i < bank.getSize(); i++) {
				entries = bank.getOrderedList(i);
				Iterator<Account> it = entries.iterator();
				while (it.hasNext()) {
					current = it.next();
					objects[j][0] = new Integer(current.getID());
					o[j] = current.getID();
					objects[j][1] = current.getPerson();
					if (current instanceof SpendingAccount) {
						objects[j][2] = "Spending";
						objects[j][3] = new Double(
								((SpendingAccount) current).getCharge());
					} else if (current instanceof SavingAccount) {
						objects[j][2] = "Saving";
						objects[j][3] = "0";
					}
					objects[j][4] = new Double(current.getAmount());
					if (j < totalSize - 1)
						j++;
				}
			}
			for (int i = 0; i < totalSize - 1; i++) {
				for (int m = i + 1; m < totalSize; m++) {
					if (o[i] > o[m]) {
						for (int k = 0; k < 5; k++) {
							int aux2 = o[i];
							o[i] = o[m];
							o[m] = aux2;
							Object[][] aux = new Object[totalSize][5];
							aux[i][k] = objects[i][k];
							objects[i][k] = objects[m][k];
							objects[m][k] = aux[i][k];
						}
					}
				}
			}
			String[] coln = { "ID", "OWNER", "TYPE", "CHARGE", "AMOUNT" };
			table = new JTable(objects, coln);
			panel.removeAll();
			JScrollPane sc = new JScrollPane(table);
			sc.setBounds(50, 50, 500, 300);
			table.setFillsViewportHeight(true);
			panel.add(sc);
			repaint();
		} else if (e.getSource() == newAccount) {
			String personName = "";
			String type = "";
			double charge = 0;
			double amount = 0;
			double minimumDeposit = 1000;
			Object[] possibilities = null;
			String s = (String) JOptionPane.showInputDialog(panel,
					"Enter the name of the person : \n", "",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "");
			if ((s != null) && (s.length() > 0)) {
				personName = s;
			}
			if (!personName.equals("")) {
				Object[] options = { "Spending Account", "Saving Account" };
				String s2 = (String) JOptionPane.showInputDialog(panel,
						"Please select the type of the account : \n", "",
						JOptionPane.PLAIN_MESSAGE, null, options, "");
				if ((s2 != null) && (s2.length() > 0)) {
					type = s2;
				}
				if (type.equals("Spending Account")) {
					String s3 = (String) JOptionPane.showInputDialog(panel,
							"Enter the charge for this account : \n", "",
							JOptionPane.PLAIN_MESSAGE, null, possibilities, "");
					if ((s3 != null) && (s3.length() > 0)) {
						charge = Double.parseDouble(s3);
					}
				} else if (type.equals("Saving Account")) {
					charge = 0;
				}
				if (!type.equals("")) {
					if (type.equals("Saving Account")) {
						String s4 = (String) JOptionPane
								.showInputDialog(
										panel,
										"Enter the amount of money (minimum 1000) for this account : \n",
										"", JOptionPane.PLAIN_MESSAGE, null,
										possibilities, "");
						if ((s4 != null) && (s4.length() > 0)) {
							amount = Double.parseDouble(s4);
						}
						if (amount >= 1000) {

							Person p = new Person(personName);
							int totalSize = 0;
							for (int i = 0; i < bank.getSize(); i++) {
								totalSize += bank.getAccounts().getLength(i);
							}
							Account newAccount = new SavingAccount(p,
									minimumDeposit);
							newAccount.deposit(amount);
							bank.addAccount(newAccount);
						} else {
							JOptionPane
									.showMessageDialog(panel,
											"The amount for the Saving Account must be at least 1000.");
						}
					} else if (type.equals("Spending Account")) {
						String s4 = (String) JOptionPane
								.showInputDialog(
										panel,
										"Enter the amount of money for this account : \n",
										"", JOptionPane.PLAIN_MESSAGE, null,
										possibilities, "");
						if ((s4 != null) && (s4.length() > 0)) {
							amount = Double.parseDouble(s4);
						}
						if (amount > 0) {
							Person p = new Person(personName);
							int totalSize = 0;
							for (int i = 0; i < bank.getSize(); i++) {
								totalSize += bank.getAccounts().getLength(i);
							}
							Account newAccount = new SpendingAccount(p, charge);
							newAccount.deposit(amount);
							bank.addAccount(newAccount);
						} else {
							JOptionPane
									.showMessageDialog(panel,
											"The amount for the Spending Account must be at greater than zero.");
						}
					}

				} else {
					JOptionPane.showMessageDialog(panel,
							"The type of the account can not be empty.");
				}

			} else {
				JOptionPane.showMessageDialog(panel,
						"The person name must not be empty.");
			}

		}

		else if (e.getSource() == removeAccount) {
			Object[] possibilities = null;
			int id = 0;
			String s = (String) JOptionPane.showInputDialog(panel,
					"Enter the id of the account to delete : \n", "",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "");
			if ((s != null) && (s.length() > 0)) {
				id = Integer.parseInt(s);
			}

			if (id > 0) {

				bank.removeAccount(id);
			} else {
				JOptionPane.showMessageDialog(panel,
						"The id of an account must be greater than zero.");
			}

		} else if (e.getSource() == depositMoney) {
			Object[] possibilities = null;
			int id = 0;
			double amount = 0;
			String s = (String) JOptionPane.showInputDialog(panel,
					"Enter the id of the account to deposit money : \n", "",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "");
			if ((s != null) && (s.length() > 0)) {
				id = Integer.parseInt(s);
			}
			if (id > 0) {
				String s1 = (String) JOptionPane.showInputDialog(panel,
						"Enter the amount of money to deposit : \n", "",
						JOptionPane.PLAIN_MESSAGE, null, possibilities, "");
				if ((s1 != null) && (s1.length() > 0)) {
					amount = Double.parseDouble(s1);
				}
				if (amount > 0) {
					bank.depositMoney(id, amount);
				} else {
					JOptionPane
							.showMessageDialog(panel,
									"The amount of money to deposit must be greater than zero.");
				}

			} else {
				JOptionPane.showMessageDialog(panel,
						"The id of an account must be greater than zero.");
			}

		} else if (e.getSource() == retireMoney) {
			Object[] possibilities = null;
			int id = 0;
			double amount = 0;
			String s = (String) JOptionPane.showInputDialog(panel,
					"Enter the id of the account to retire money : \n", "",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "");
			if ((s != null) && (s.length() > 0)) {
				id = Integer.parseInt(s);
			}
			if (id > 0) {
				String s1 = (String) JOptionPane.showInputDialog(panel,
						"Enter the amount of money to retire : \n", "",
						JOptionPane.PLAIN_MESSAGE, null, possibilities, "");
				if ((s1 != null) && (s1.length() > 0)) {
					amount = Double.parseDouble(s1);
				}
				if (amount > 0) {
					Account nou ;
					nou = bank.getOrderedList(id).get(id/bank.getSize());
					if(nou instanceof SpendingAccount){
						if(nou.getAmount() > amount + amount * ((SpendingAccount)nou).getCharge()){
							bank.retireMoney(id, amount);
						}else {
							JOptionPane
							.showMessageDialog(panel,
									"Insufficient founds.");
						}
					}else bank.retireMoney(id, amount);
				} else {
					JOptionPane
							.showMessageDialog(panel,
									"The amount of money to retire must be greater than zero.");
				}

			} else {
				JOptionPane.showMessageDialog(panel,
						"The id of an account must be greater than zero.");
			}

		} else if (e.getSource() == load) {
			int[] compare = new int[100];
			compare[0] = 0;
			int x = 1;
			int y = 0;
			try {
				Scanner in = new Scanner(new FileReader("saving.txt"));
				while (in.hasNext()) {
					String next = in.next();
					Scanner sin = new Scanner(next).useDelimiter("\\|");
					int accountID = sin.nextInt();
					compare[x] = accountID;
					if (compare[x - 1] > compare[x])
						compare[x] = compare[x - 1];
					String name = sin.next();
					double minDeposit = sin.nextDouble();
					double amount = sin.nextDouble();
					Person p = new Person(name);
					account = new SavingAccount(accountID, p, minDeposit,
							amount);
					bank.addAccount(account);
					x++;
				}
				in.close();
			} catch (Exception ex) {
				JOptionPane
						.showMessageDialog(panel,
								"The 'Saving Accounts' data has failed to load from file");
				System.err.println(ex);
			}
			y = x;
			try {
				Scanner in = new Scanner(new FileReader("spending.txt"));
				while (in.hasNext()) {
					String next = in.next();
					Scanner sc = new Scanner(next).useDelimiter("\\|");
					int accountID = sc.nextInt();
					compare[y] = accountID;
					if (compare[y - 1] > compare[y])
						compare[y] = compare[y - 1];
					String name = sc.next();
					double charge = sc.nextDouble();
					double amount = sc.nextDouble();
					Person p = new Person(name);
					Account account = new SpendingAccount(accountID, p, charge,
							amount);
					bank.addAccount(account);
					account.setNrOfAccounts(compare[y] + 1);
					y++;
				}
				in.close();
			} catch (Exception ex) {
				JOptionPane
						.showMessageDialog(panel,
								"The 'Spending Accounts' data has failed to load from file");
				System.err.println(ex);
			}
		} else if (e.getSource() == save) {
			try {
				BufferedWriter out1 = new BufferedWriter(new FileWriter(
						"spending.txt"));
				BufferedWriter out2 = new BufferedWriter(new FileWriter(
						"saving.txt"));
				for (int i = 0; i < bank.getSize(); i++) {
					for (Account a : bank.getAccounts().getList(i)) {
						if (a instanceof SpendingAccount) {
							out1.write(a.getID() + "|" + a.getPerson() + "|"
									+ ((SpendingAccount) a).getCharge() + "|"
									+ a.getAmount() + "|");
							out1.write("\n");
						} else if (a instanceof SavingAccount) {
							out2.write(a.getID() + "|" + a.getPerson() + "|"
									+ ((SavingAccount) a).getMinimumDeposit()
									+ "|" + a.getAmount() + "|");
							out2.write("\n");
						}
					}
				}
				out1.close();
				out2.close();
			} catch (IOException exception) {
				JOptionPane.showMessageDialog(panel,
						"The data has failed to save to file");

			}
		}

	}

	/**
	 *  
	 * @param args
	 *             arguments trimis aplicatiei
	 * 
	 */
	public static void main(String[] args) {
		new MyFrame();
	}
}
