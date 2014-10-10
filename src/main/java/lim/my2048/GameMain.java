package lim.my2048;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GameMain {

	private static final int SIZE = 4;
	private static final int SMALL_NUMBER = 2;
	private static final int BIG_NUMBER = 4;
	private static final int REQUIRED = 2048;
	private static int steps = 0;

	public static void main(String[] args) {

		flush();

		Spielfeld spielfeld = initialize();

		spielfeld.setHighestNumber(findHighest(spielfeld));

		System.out
				.println("\n\n##########################\n\nWelcome to my2048 by lim \n\n##########################\n\n");

		System.out.println("Play: w, a, s or d + ENTER.\n"
				+ "Quit: x + ENTER\n\n");

		play(spielfeld);

	}

	private static void play(Spielfeld spielfeld) {

		printMap(spielfeld);

		boolean nextStepPossible = true;
		int highest = spielfeld.getHighestNumber();

		Scanner sc = new Scanner(System.in);

		while (nextStepPossible) {

			System.out.print("\n\nNext: ");

			String action = sc.next();

			if (action.equals("a")) {
				spielfeld = moveLeft(spielfeld);

			} else if (action.equals("d")) {
				spielfeld = moveRight(spielfeld);

			} else if (action.equals("w")) {
				spielfeld = moveUp(spielfeld);

			} else if (action.equals("s")) {
				spielfeld = moveDown(spielfeld);

			} else if (action.equals("x")) {
				sc.close();
				return;

			} else {
				flush();
				play(spielfeld);
			}

			if (spielfeld.isChanged()) {
				steps++;
				spielfeld = setRandom(spielfeld);
				spielfeld.setChanged(false);
			}

			flush();

			printMap(spielfeld);

			if (spielfeld.getFreeFields().size() == 0) {
				nextStepPossible = checkNextStep(spielfeld);
			}

			highest = findHighest(spielfeld);

			if (highest >= REQUIRED) {
				System.out
						.println("\n\n#####################\n\n      YOU WIN!!! \n\n#####################\n\n");
				System.out.println("Statistics:\nNumber of steps:   " + steps);
				sc.close();
				return;
			}

		}

		System.out
				.println("\n\n#####################\n\n    GAME OVER!!! \n\n#####################\n\n");
		System.out.println("Statistics:\nNumber of steps:   " + steps);

		sc.close();

	}

	private static void flush() {
		final String ANSI_CLS = "\u001b[2J";
		final String ANSI_HOME = "\u001b[H";
		System.out.print(ANSI_CLS + ANSI_HOME);
		System.out.flush();

	}

	private static Spielfeld moveRight(Spielfeld spielfeld) {

		spielfeld.setChanged(false);

		for (int y = 0; y < SIZE; y++) {
			for (int x = SIZE - 1; x >= 0; x--) {
				Key key = new Key(x, y);
				Feld f = spielfeld.getSpielfeld().get(key);

				if (!f.hasValue() && x > 0) {

					spielfeld = pullRight(spielfeld, x, y, f);

				}

				if (!f.hasValue()) {
					break;
				}

				if (x == 0) {
					break;
				}

				Key nextKey = new Key(x - 1, y);

				if (!spielfeld.getSpielfeld().get(nextKey).hasValue()) {
					spielfeld = pullRight(spielfeld, x - 1, y, spielfeld
							.getSpielfeld().get(nextKey));
				}

				Feld next = spielfeld.getSpielfeld().get(nextKey);

				if (f.getValue() == next.getValue()) {
					f.setValue(f.getValue() * 2);
					next.setValue(0);
					next.setHasValue(false);
					spielfeld.setChanged(true);
				}

			}
		}
		return spielfeld;
	}

	private static Spielfeld pullRight(Spielfeld spielfeld, int x, int y, Feld f) {

		for (int i = x - 1; i >= 0; i--) {
			Key possibleKey = new Key(i, y);
			if (spielfeld.getSpielfeld().get(possibleKey).hasValue()) {
				f.setValue(spielfeld.getSpielfeld().get(possibleKey).getValue());
				f.setHasValue(true);
				spielfeld.getSpielfeld().get(possibleKey).setValue(0);
				spielfeld.getSpielfeld().get(possibleKey).setHasValue(false);
				spielfeld.setChanged(true);
				return spielfeld;
			}
		}

		return spielfeld;

	}

	private static Spielfeld moveLeft(Spielfeld spielfeld) {

		spielfeld.setChanged(false);
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				Key key = new Key(x, y);
				Feld f = spielfeld.getSpielfeld().get(key);

				if (!f.hasValue()) {

					spielfeld = pullLeft(spielfeld, x, y, f);

				}

				if (!f.hasValue()) {
					break;
				}

				if (x == SIZE - 1) {
					break;
				}

				Key nextKey = new Key(x + 1, y);

				if (!spielfeld.getSpielfeld().get(nextKey).hasValue()) {
					spielfeld = pullLeft(spielfeld, x + 1, y, spielfeld
							.getSpielfeld().get(nextKey));
				}

				Feld next = spielfeld.getSpielfeld().get(nextKey);

				if (f.getValue() == next.getValue()) {
					f.setValue(f.getValue() * 2);
					next.setValue(0);
					next.setHasValue(false);
					spielfeld.setChanged(true);
				}

			}
		}
		return spielfeld;
	}

	private static Spielfeld pullLeft(Spielfeld spielfeld, int x, int y, Feld f) {

		for (int i = x + 1; i < SIZE; i++) {

			Key possibleKey = new Key(i, y);
			if (spielfeld.getSpielfeld().get(possibleKey).hasValue()) {
				f.setValue(spielfeld.getSpielfeld().get(possibleKey).getValue());
				f.setHasValue(true);
				spielfeld.getSpielfeld().get(possibleKey).setValue(0);
				spielfeld.getSpielfeld().get(possibleKey).setHasValue(false);
				spielfeld.setChanged(true);
				return spielfeld;
			}
		}

		return spielfeld;

	}

	private static Spielfeld moveDown(Spielfeld spielfeld) {

		spielfeld.setChanged(false);

		for (int x = 0; x < SIZE; x++) {

			for (int y = SIZE - 1; y >= 0; y--) {
				Key key = new Key(x, y);
				Feld f = spielfeld.getSpielfeld().get(key);

				if (!f.hasValue() && y > 0) {

					spielfeld = pullDown(spielfeld, x, y, f);

				}

				if (!f.hasValue()) {
					break;
				}

				if (y == 0) {
					break;
				}

				Key nextKey = new Key(x, y - 1);

				if (!spielfeld.getSpielfeld().get(nextKey).hasValue()) {
					spielfeld = pullDown(spielfeld, x, y - 1, spielfeld
							.getSpielfeld().get(nextKey));
				}

				Feld next = spielfeld.getSpielfeld().get(nextKey);

				if (f.getValue() == next.getValue()) {
					f.setValue(f.getValue() * 2);
					next.setValue(0);
					next.setHasValue(false);
					spielfeld.setChanged(true);
				}

			}
		}
		return spielfeld;
	}

	private static Spielfeld pullDown(Spielfeld spielfeld, int x, int y, Feld f) {

		for (int i = y - 1; i >= 0; i--) {

			Key possibleKey = new Key(x, i);
			if (spielfeld.getSpielfeld().get(possibleKey).hasValue()) {
				f.setValue(spielfeld.getSpielfeld().get(possibleKey).getValue());
				f.setHasValue(true);
				spielfeld.getSpielfeld().get(possibleKey).setValue(0);
				spielfeld.getSpielfeld().get(possibleKey).setHasValue(false);
				spielfeld.setChanged(true);
				return spielfeld;
			}
		}

		return spielfeld;

	}

	private static Spielfeld moveUp(Spielfeld spielfeld) {

		spielfeld.setChanged(false);

		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				Key key = new Key(x, y);
				Feld f = spielfeld.getSpielfeld().get(key);

				if (!f.hasValue()) {

					spielfeld = pullUp(spielfeld, x, y, f);

				}

				if (!f.hasValue()) {
					break;
				}

				if (y == SIZE - 1) {
					break;
				}

				Key nextKey = new Key(x, y + 1);

				if (!spielfeld.getSpielfeld().get(nextKey).hasValue()) {
					spielfeld = pullUp(spielfeld, x, y + 1, spielfeld
							.getSpielfeld().get(nextKey));
				}

				Feld next = spielfeld.getSpielfeld().get(nextKey);

				if (f.getValue() == next.getValue()) {
					f.setValue(f.getValue() * 2);
					next.setValue(0);
					next.setHasValue(false);
					spielfeld.setChanged(true);
				}

			}
		}
		return spielfeld;
	}

	private static Spielfeld pullUp(Spielfeld spielfeld, int x, int y, Feld f) {

		for (int i = y + 1; i < SIZE; i++) {

			Key possibleKey = new Key(x, i);
			if (spielfeld.getSpielfeld().get(possibleKey).hasValue()) {
				f.setValue(spielfeld.getSpielfeld().get(possibleKey).getValue());
				f.setHasValue(true);
				spielfeld.getSpielfeld().get(possibleKey).setValue(0);
				spielfeld.getSpielfeld().get(possibleKey).setHasValue(false);
				spielfeld.setChanged(true);
				return spielfeld;
			}
		}

		return spielfeld;

	}

	private static boolean checkNextStep(Spielfeld spielfeld) {

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {

				Key k = new Key(x, y);

				if (x == SIZE - 1) {
					break;
				}
				Key kNext = new Key(x + 1, y);

				Feld f = spielfeld.getSpielfeld().get(k);
				Feld fNext = spielfeld.getSpielfeld().get(kNext);

				if (f.getValue() == fNext.getValue()) {
					return true;
				}

			}

		}

		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {

				Key k = new Key(x, y);

				if (y == SIZE - 1) {
					break;
				}
				Key kNext = new Key(x, y + 1);

				Feld f = spielfeld.getSpielfeld().get(k);
				Feld fNext = spielfeld.getSpielfeld().get(kNext);

				if (f.getValue() == fNext.getValue()) {
					return true;
				}

			}

		}

		return false;
	}

	public static void printMap(Spielfeld spielfeld) {

		StringBuffer sb = new StringBuffer();

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				Key key = new Key(x, y);
				Feld f = spielfeld.getSpielfeld().get(key);

				int value = f.getValue();
				if (value != 0) {
					sb.append("[");

					if (value < 10) {
						sb.append("  ");
						sb.append(value);
						sb.append(" ");
					} else if (value < 100) {
						sb.append(" ");
						sb.append(value);
						sb.append(" ");
					} else if (value < 1000) {
						sb.append(" ");
						sb.append(value);
					} else {
						sb.append(value);
					}

					sb.append("]");
				} else {
					sb.append("[    ]");
				}

				if (x == SIZE - 1) {
					sb.append("\n");
				}
			}
		}

		System.out.print(sb.toString());

	}

	public static Spielfeld initialize() {

		Map<Key, Feld> spielfeldMap = new HashMap<Key, Feld>();

		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				Key k = new Key(x, y);
				Feld f = new Feld(0, false);
				spielfeldMap.put(k, f);
			}
		}

		Spielfeld spielfeld = new Spielfeld(spielfeldMap, 0, false);

		spielfeld = setRandom(spielfeld);
		spielfeld = setRandom(spielfeld);

		return spielfeld;
	}

	private static Spielfeld setRandom(Spielfeld spielfeld) {

		List<Key> freeFields = new ArrayList<Key>();

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {

				Key k = new Key(x, y);
				Feld f = spielfeld.getSpielfeld().get(k);

				if (!f.hasValue()) {
					freeFields.add(k);
				}
			}

		}

		if (freeFields.size() == 0) {
			return spielfeld;
		}
		int keyNumber = getRandom(0, freeFields.size());

		Key k = freeFields.get(keyNumber);

		int number = getRandom(0, 2);
		int numberToAdd;

		if (number == 0) {
			numberToAdd = SMALL_NUMBER;
		} else {
			numberToAdd = BIG_NUMBER;
		}

		spielfeld.getSpielfeld().get(k).setValue(numberToAdd);
		spielfeld.getSpielfeld().get(k).setHasValue(true);

		freeFields.remove(keyNumber);
		spielfeld.setFreeFields(freeFields);

		return spielfeld;
	}

	private static int findHighest(Spielfeld spielfeld) {

		int highest = 0;

		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				Key key = new Key(x, y);
				Feld f = spielfeld.getSpielfeld().get(key);

				if (f.hasValue() && f.getValue() > highest) {
					highest = f.getValue();
				}

			}

		}

		return highest;

	}

	public static int getRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}

}
