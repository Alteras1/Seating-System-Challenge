package com.seating.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class SeatingService {
	private ArrayList<ArrayList<Character>> currentLayout = new ArrayList<ArrayList<Character>>();
	
	/**
	 * Repeats round() until an unchanging state is found
	 * @return total occupied seats
	 */
	public ArrayList<ArrayList<Character>> stablize() {
		System.out.println("Running rounds");
		while(round());
//		System.out.println("Final State");
//		currentLayout.forEach(System.out::println);
		System.out.println("Complete");
		return currentLayout;
	}
	
	/**
	 * Execute one round
	 * @return true if changed, false if no change
	 */
	public boolean round() {
		ArrayList<ArrayList<Character>> temp = new ArrayList<>(currentLayout.size());
		currentLayout.forEach(sublist -> temp.add(new ArrayList<Character>(sublist)));	//deep copy currentLayout
		for(int r = 0; r < currentLayout.size(); r ++) {		//for every element
			for (int c = 0; c < currentLayout.get(0).size(); c ++) {
				if (currentLayout.get(r).get(c) == '.') continue;
				int count = getSurroundings(r, c);
				if (count == 0) temp.get(r).set(c, '#');
				if (count >= 4) temp.get(r).set(c, 'L');
				
			}
		}
		boolean change = !(currentLayout.equals(temp));
		setCurrentLayout(temp);
		return change;
	}
	
	/**
	 * Get count of occupied surrounding seats of seat[row][col]
	 * @param row 
	 * @param col
	 * @return count
	 */
	private int getSurroundings(int row, int col) {
		int count = 0;
		for (int x = -1; x < 2; x++)
			for(int y=-1; y < 2; y++) {
				if (x == 0 && y == 0) continue;
				try {
					if (currentLayout.get(row + x).get(col + y) == '#') count++;
				} catch (Exception e) {
					//do nothing
					//not a valid seat exists at relative x,y
					//implies edge seat
				}
			}
		return count;
	}
	
	/**
	 * Get total amount of occupied seats
	 * @return int
	 */
	public int countOccupied() {
		int count = 0;
		for(int r = 0; r < currentLayout.size(); r ++)		//for every element
			for (int c = 0; c < currentLayout.get(0).size(); c ++)
				if (currentLayout.get(r).get(c) == '#') count++;
		return count;
	}
}
