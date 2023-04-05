package controller;

import common.Menu;
import exception.InputNotMatchException;

public class PhoneBook {

	public static void main(String[] args) {
		Menu menu = new Menu();

		try {
			menu.showMenu();
		} catch (InputNotMatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
