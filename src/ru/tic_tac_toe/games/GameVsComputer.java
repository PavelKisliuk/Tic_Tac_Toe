package ru.tic_tac_toe.games;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

public class GameVsComputer extends Game {
	//-----------------------------------------------------------------------------fields
	public static int go_Number = 0;
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	//-----------------------------------------------------------------------------save & load
	public AreaConditions loadGame(String path) {
		try(final BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
			String savedGame = reader.readLine();
			savedGame = savedGame.replaceAll("[]\\[]", "");
			//-----------------------------------------------------------------------------
			int count = 0;
			for(String s : savedGame.split(", ")) {
				switch (s){
					case "EMPTY":
						super.main_Area[count++] = AreaConditions.EMPTY;
						break;
					case "DASH":
						super.main_Area[count++] = AreaConditions.DASH;
						break;
					case "ZERO":
						super.main_Area[count++] = AreaConditions.ZERO;
						break;
					default:
						break;
				}
			}
			//-----------------------------------------------------------------------------
			GameVsComputer.go_Number = Integer.valueOf(reader.readLine());
			savedGame = reader.readLine();
			return (savedGame.equals("DASH")) ? AreaConditions.DASH : AreaConditions.ZERO;
		}catch (IOException e) {
			e.printStackTrace();
		}
		return AreaConditions.EMPTY;
	}

	public void saveGame(final String path, AreaConditions symbolPlayer) {
		try(final BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
			writer.write(Arrays.asList(super.main_Area).toString());
			writer.write("\n");
			writer.write(String.valueOf(GameVsComputer.go_Number));
			writer.write("\n");
			writer.write(symbolPlayer.toString());
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------------goes
	public void player_Go(AreaConditions symbolPlayer, int place)
	{
		super.main_Area[place] = symbolPlayer;
	}

	public int computer_Go(AreaConditions symbolComputer, AreaConditions symbolPlayer)
	{
		SecureRandom randomNumbers = new SecureRandom();
		int place = 0;
		if(1 == GameVsComputer.go_Number) {
			place = 4;
			if(AreaConditions.EMPTY == super.main_Area[place]){
				super.main_Area[place] = symbolComputer;
			}
			else {
				int[] ar = {0, 2, 6, 8};
				place = ar[randomNumbers.nextInt(4)];
				super.main_Area[place] = symbolComputer;
			}
		}
		else if((2 == GameVsComputer.go_Number) && isTwoAngles(symbolPlayer)) {
			int[] ar = {1, 3, 5, 7};
			place = ar[randomNumbers.nextInt(4)];
			super.main_Area[place] = symbolComputer;
		}
		else if((2 == GameVsComputer.go_Number) &&
				(symbolPlayer == super.main_Area[4]) && isTupic(symbolPlayer, symbolComputer)) {
			if(AreaConditions.EMPTY != super.main_Area[0]) {
				int[] ar = {2, 6};
				place = ar[randomNumbers.nextInt(2)];
				super.main_Area[place] = symbolComputer;
			}
			else {
				int[] ar = {0, 8};
				place = ar[randomNumbers.nextInt(2)];
				super.main_Area[place] = symbolComputer;
			}
		}
		else if(((2 == GameVsComputer.go_Number) || ((3 == GameVsComputer.go_Number) && (symbolComputer == AreaConditions.DASH))) &&
				(symbolComputer == super.main_Area[4]) && isCritical(symbolPlayer)) {
			if((symbolPlayer == super.main_Area[2]) && (symbolPlayer == super.main_Area[3]) ||
					(symbolPlayer == super.main_Area[6]) && (symbolPlayer == super.main_Area[1])) {
				//place = 0;
				super.main_Area[place] = symbolComputer;
			}
			else if((symbolPlayer == super.main_Area[0]) && ((symbolPlayer == super.main_Area[5])) ||
					(symbolPlayer == super.main_Area[8]) && (symbolPlayer == super.main_Area[1])) {
				place = 2;
				super.main_Area[place] = symbolComputer;
			}
			else if((symbolPlayer == super.main_Area[0]) && (symbolPlayer == super.main_Area[7]) ||
					(symbolPlayer == super.main_Area[8]) && (symbolPlayer == super.main_Area[3])) {
				place = 6;
				super.main_Area[place] = symbolComputer;
			}
			else if((symbolPlayer == super.main_Area[2]) && (symbolPlayer == super.main_Area[7]) ||
					(symbolPlayer == super.main_Area[6]) && (symbolPlayer == super.main_Area[5])) {
				place = 8;
				super.main_Area[place] = symbolComputer;
			}
		}
		else if((2 == GameVsComputer.go_Number) && (symbolComputer == super.main_Area[4]) && isDiagonal(symbolPlayer)) {
			if((symbolPlayer == super.main_Area[1]) && (symbolPlayer == super.main_Area[3])) {
				//place = 0;
				super.main_Area[place] = symbolComputer;
			}
			else if((symbolPlayer == super.main_Area[1]) && (symbolPlayer == super.main_Area[5])) {
				place = 2;
				super.main_Area[place] = symbolComputer;
			}
			else if((symbolPlayer == super.main_Area[3]) && (symbolPlayer == super.main_Area[7])) {
				place = 6;
				super.main_Area[place] = symbolComputer;
			}
			else if((symbolPlayer == super.main_Area[5]) && (symbolPlayer == super.main_Area[7])) {
				place = 8;
				super.main_Area[place] = symbolComputer;
			}
		}
		else {
			place = choice(symbolComputer, symbolPlayer);
			super.main_Area[place] = symbolComputer;
		}
		return place;
	}
	//-----------------------------------------------------------------------------special situationes
	private boolean isTwoAngles(AreaConditions symbolPlayer)
	{
		return (((symbolPlayer == super.main_Area[0]) && (symbolPlayer == super.main_Area[8])) ||
				((symbolPlayer == super.main_Area[2]) && (symbolPlayer == super.main_Area[6])));
	}

	private boolean isTupic(AreaConditions symbolPlayer, AreaConditions symbolComputer)
	{
		return ((symbolPlayer == super.main_Area[0]) && (symbolComputer == super.main_Area[8])) ||
				((symbolPlayer == super.main_Area[8]) && (symbolComputer == super.main_Area[0])) ||
				((symbolPlayer == super.main_Area[2]) && (symbolComputer == super.main_Area[6])) ||
				((symbolPlayer == super.main_Area[6])) && (symbolComputer == super.main_Area[2]);
	}

	private boolean isCritical(AreaConditions symbolPlayer) {
		return ((symbolPlayer == super.main_Area[0]) && ((symbolPlayer == super.main_Area[5]) || (symbolPlayer == super.main_Area[7]))) ||
		((symbolPlayer == super.main_Area[2]) && ((symbolPlayer == super.main_Area[3]) || (symbolPlayer == super.main_Area[7]))) ||
		((symbolPlayer == super.main_Area[6]) && ((symbolPlayer == super.main_Area[1]) || (symbolPlayer == super.main_Area[5]))) ||
		((symbolPlayer == super.main_Area[8]) && ((symbolPlayer == super.main_Area[1]) || (symbolPlayer == super.main_Area[3])));

	}

	private boolean isDiagonal(AreaConditions symbolPlayer) {
		return ((symbolPlayer == super.main_Area[1]) && ((symbolPlayer == super.main_Area[3]) || (symbolPlayer == super.main_Area[5]))) ||
				((symbolPlayer == super.main_Area[3]) && ((symbolPlayer == super.main_Area[7]))) ||
				((symbolPlayer == super.main_Area[5]) && (symbolPlayer == super.main_Area[7]));

	}
	//-----------------------------------------------------------------------------choice
	private int choice(AreaConditions symbolComputer, AreaConditions symbolPlayer)
	{
		int ifWin = ifCanWin(symbolComputer);
		if(ifWin != -1) {
			return ifWin;
		}
		//------------------------------------------------------
		int ifLose = ifCanLose(symbolPlayer);
		if(ifLose != -1) {
			return ifLose;
		}
		//------------------------------------------------------
		return primeChoice(symbolComputer);
	}

	private int ifCanWin(AreaConditions symbolComputer)
	{
		int counterSymbolComputer = 0;
		int counterEmpty = 0;
		int squareNumber = -1;

		for(int i = 0; i < super.combinationsAmount; i++) {
			for(int j = 0; j < super.size; j++) {
				if(AreaConditions.EMPTY == super.main_Area[super.winningCombinations[i][j]]) {
					counterEmpty++;
					squareNumber = super.winningCombinations[i][j];
				}
				else if(symbolComputer == super.main_Area[super.winningCombinations[i][j]]) {
					counterSymbolComputer++;
				}
			}
			if((1 == counterEmpty) && (2 == counterSymbolComputer)) {
				return squareNumber;
			}
			else {
				counterSymbolComputer = 0;
				counterEmpty = 0;
				squareNumber = -1;
			}
		}
		return squareNumber;
	}

	private int ifCanLose(AreaConditions symbolPlayer)
	{
		int counterSymbolPlayer = 0;
		int counterEmpty = 0;
		int squareNumber = -1;

		for(int i = 0; i < super.combinationsAmount; i++) {
			for(int j = 0; j < super.size; j++) {
				if(AreaConditions.EMPTY == super.main_Area[super.winningCombinations[i][j]]) {
					counterEmpty++;
					squareNumber = super.winningCombinations[i][j];
				}
				else if(symbolPlayer == super.main_Area[super.winningCombinations[i][j]]) {
					counterSymbolPlayer++;
				}
			}
			if((1 == counterEmpty) && (2 == counterSymbolPlayer)) {
				return squareNumber;
			}
			else {
				counterSymbolPlayer = 0;
				counterEmpty = 0;
				squareNumber = -1;
			}
		}
		return squareNumber;
	}

	private int primeChoice(AreaConditions symbolComputer)
	{
		SecureRandom randomNumbers = new SecureRandom();

		ArrayList<Integer> possibleSquares = new ArrayList<>();
		ArrayList<Integer> notWinPossibleSquares = new ArrayList<>();
		for(int i = 0; i < super.combinationsAmount; i++) {
			int counterEmpty = 0;
			int counterSymbolComputer = 0;
			ArrayList<Integer> emptySquares = new ArrayList<>();
			for(int j = 0; j < super.size; j++) {
				if(AreaConditions.EMPTY == super.main_Area[super.winningCombinations[i][j]]) {
					counterEmpty++;
					emptySquares.add(super.winningCombinations[i][j]);
				}
				else if(symbolComputer == super.main_Area[super.winningCombinations[i][j]]) {
					counterSymbolComputer++;
				}
			}
			if((2 == counterEmpty) && (1 == counterSymbolComputer)) {
				for(int s : emptySquares) {
					if(!possibleSquares.contains(s)) {
						possibleSquares.add(s);
					}
				}
			}
			else {
				for(int s : emptySquares) {
					if(!notWinPossibleSquares.contains(s)) {
						notWinPossibleSquares.add(s);
					}
				}
			}
		}
		if(possibleSquares.isEmpty()) {
			return notWinPossibleSquares.get(randomNumbers.nextInt(notWinPossibleSquares.size()));
		}
		else {
			ArrayList<Integer> playerPossibleSquares = new ArrayList<>();
			for(int i = 0; i < super.combinationsAmount; i++) {
				int counterEmpty = 0;
				int counterSymbolPlayer = 0;
				ArrayList<Integer> emptySquares = new ArrayList<>();
				for(int j = 0; j < super.size; j++) {
					if(AreaConditions.EMPTY == super.main_Area[super.winningCombinations[i][j]]) {
						counterEmpty++;
						emptySquares.add(super.winningCombinations[i][j]);
					}
					else if(symbolComputer == super.main_Area[super.winningCombinations[i][j]]) {
						counterSymbolPlayer++;
					}
				}
				if((2 == counterEmpty) && (1 == counterSymbolPlayer)) {
					for(int s : emptySquares) {
						if((possibleSquares.contains(s)) && (!playerPossibleSquares.contains(s))) {
							playerPossibleSquares.add(s);
						}
					}
				}
			}
			int[] squares = new int[9];
			for(int i : playerPossibleSquares) {

				squares[i]++;
			}
			int max = 0;
			for (int i : squares) {
				if(max < i) {
					max = i;
				}
			}
			possibleSquares.clear();
			for(int i = 0; i < squares.length; i++) {
				if(squares[i] == max) {
					possibleSquares.add(i);
				}
			}
			return possibleSquares.get(randomNumbers.nextInt(possibleSquares.size()));
		}
	}
}