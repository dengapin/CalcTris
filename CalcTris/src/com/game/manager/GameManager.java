package com.game.manager;

public class GameManager {
	/* Since this class is a singleton, we must declare an instance
	 * of this class within itself. The singleton will be instantiated
	 * a single time during the course of an application's full life-cycle
	 */
	private static GameManager INSTANCE;
	
	private static final int OPERATION_COUNT = 0;
	
	private int mCurrentScore=0;
	
	public GameManager(){}
	public static GameManager getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GameManager();
		}
		return INSTANCE;
	}
	// get the current score
	public int getCurrentScore(){
		return this.mCurrentScore;
	}
	// increase the current score, most likely when an enemy is destroyed
	public void incrementScore(int pIncrementBy){
		mCurrentScore += pIncrementBy;
	}
	
	// Resetting the game simply means we must revert back to initial values.
		public void resetGame(){
			this.mCurrentScore = GameManager.OPERATION_COUNT;
		}
}
