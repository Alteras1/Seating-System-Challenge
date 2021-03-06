package com.seating.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

/**
 * Service class for Part 2 of challenge
 * 
 * @author Steven Chang
 *
 */
@Service
@Getter
@Setter
public class SeatingService2 {
	private ArrayList<ArrayList<Character>> currentLayout = new ArrayList<ArrayList<Character>>();

	/**
	 * Repeats round() until an unchanging state is found
	 * 
	 * @param tolerance
	 * @return total occupied seats
	 */
	public ArrayList<ArrayList<Character>> stablize(int tolerance) {
		System.out.println("Running rounds");
		while (round(tolerance))
			;
//		System.out.println("Final State");
//		currentLayout.forEach(System.out::println);
		System.out.println("Complete");
		return currentLayout;
	}

	/**
	 * Execute one round
	 * 
	 * @param tolerance level
	 * @return true if changed, false if no change
	 */
	public boolean round(int tolerance) {
		ArrayList<ArrayList<Character>> temp = new ArrayList<>(currentLayout.size());
		currentLayout.forEach(sublist -> temp.add(new ArrayList<Character>(sublist))); // deep copy currentLayout
		for (int r = 0; r < currentLayout.size(); r++) { // for every element
			for (int c = 0; c < currentLayout.get(0).size(); c++) {
				if (currentLayout.get(r).get(c) == '.')
					continue;
				int count = getSurroundings(r, c);
				if (count == 0)
					temp.get(r).set(c, '#');
				if (count >= tolerance)
					temp.get(r).set(c, 'L');

			}
		}
		boolean change = !(currentLayout.equals(temp));
		setCurrentLayout(temp);
		return change;
	}

	/**
	 * Get count of occupied surrounding seats of seat[row][col] using special Part
	 * 2 rules
	 * 
	 * @param row
	 * @param col
	 * @return count
	 */
	private int getSurroundings(int row, int col) {
		int count = 0;
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				if (x == 0 && y == 0)
					continue;
				else if (explorer(row, col, x, y)) count++;
			}
		}
		return count;
	}
	
	/**
	 * Will explore arrayList by add x, y to current index until an occupied seat is found
	 * @param x
	 * @param y
	 * @return true if occupied seat found, false if no occupied seats found
	 */
	private boolean explorer(int row, int col, int x, int y) {
		boolean found = false;
		row += x; col += y;
		for (;;row += x, col += y) {
			if (row < currentLayout.size() && row >= 0 && col < currentLayout.get(0).size() && col >= 0) {				
				if (currentLayout.get(row).get(col) == '#') {
					found = true;
					break;
				}
				if (currentLayout.get(row).get(col) == 'L') {
					found = false;
					break;
				}
			} else {
				break;
			}
		}
		return found;
	}
	
	/**
	 * Get total amount of occupied seats
	 * 
	 * @return int
	 */
	public int countOccupied() {
		int count = 0;
		for (int r = 0; r < currentLayout.size(); r++) // for every element
			for (int c = 0; c < currentLayout.get(0).size(); c++)
				if (currentLayout.get(r).get(c) == '#')
					count++;
		return count;
	}
}
