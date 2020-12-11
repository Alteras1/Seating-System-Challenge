package com.seating.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seating.service.SeatingService;

@RestController
@RequestMapping("/seating")
@CrossOrigin
public class SeatingController {
	@Autowired
	SeatingService ss;
	
	/**
	 * Formats an unformatted input
	 * @param input String
	 * @return ArrayList of ArrayList of Characters
	 */
	private ArrayList<ArrayList<Character>> format(String input) {
		input = input.trim();
		ArrayList<ArrayList<Character>> formatted = new ArrayList<ArrayList<Character>>();
		ArrayList<String> rows = new ArrayList<String>(Arrays.asList(input.split("\n")));
		rows.forEach(string -> {
			// Convert String to ArrayList of Characters
			ArrayList<Character> temp = (ArrayList<Character>) string.chars().mapToObj(c -> (char) c)
					.collect(Collectors.toList());
			formatted.add(temp);
		});
		return formatted;
	}

	/**
	 * Returns the final state of the seating arrangement
	 * @param input
	 * @return String
	 */
	@PostMapping("/finalState")
	public String getFinalState(@RequestBody String input) {
		ss.setCurrentLayout(format(input));
		ArrayList<ArrayList<Character>> finalState = ss.stablize();
		StringBuilder finalString = new StringBuilder();
		finalState.forEach(row -> {
			String rowString = row.stream().map(ch -> Character.toString(ch)).collect(Collectors.joining());
//			System.out.println(rowString);
			finalString.append(rowString + '\n');
		});
//		System.out.println(finalString.toString().trim());
//		System.out.println(ss.round());
		return finalString.toString().trim();
	}
	
	/**
	 * Returns the final count of occupied seats
	 * @param input
	 * @return int
	 */
	@PostMapping("/finalCount")
	public int getFinalCount(@RequestBody String input) {
		ss.setCurrentLayout(format(input));
		ss.stablize();
		return ss.countOccupied();
	}
}
