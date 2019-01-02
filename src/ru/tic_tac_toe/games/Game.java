package ru.tic_tac_toe.games;


import java.util.Arrays;

abstract public class Game {
	//-----------------------------------------------------------------------------fields
	AreaConditions[] main_Area;
	final int size = 3; //size of column, row and amount align symbols for winning
	final int[][] winningCombinations = {	{0, 4, 8},
											{1, 4, 7},
											{2, 4, 6},
											{3, 4, 5},
											{0, 1, 2},
											{6, 7, 8},
											{0, 3, 6},
											{2, 5, 8}};
	final int combinationsAmount = 8;
	//-----------------------------------------------------------------------------constructors
	Game()
	{
		this.main_Area = new AreaConditions[this.size * this.size];
		Arrays.fill(this.main_Area, AreaConditions.EMPTY);
	}
	//-----------------------------------------------------------------------------
	//-----------------------------------------------------------------------------methods
	public int isWin(AreaConditions symbol)
	{
		for(int i = 0; i < this.combinationsAmount; i++) {
			//-----------------------------------------------------------------------------
			int token = 0;
			for (int j = 0; j < this.size; j++) {
				if(symbol == (this.main_Area[winningCombinations[i][j]])) {
					token++;
				}
			}
			if(this.size == token) return i;
			//-----------------------------------------------------------------------------
		}
		return -1;
	}

	public AreaConditions getElement(int numberOfElement) {
		return this.main_Area[numberOfElement];
	}

	abstract public AreaConditions loadGame(String path);
	abstract public void saveGame(final String path, AreaConditions symbolPlayer);
	abstract public void player_Go(AreaConditions symbolPlayer, int place);
	abstract public int opponent_Go(AreaConditions symbolOpponent, AreaConditions symbolPlayer);
}