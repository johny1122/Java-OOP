import oop.ex2.GameGUI;

import java.awt.*;

/**
 * A class that represents a Drunkard SpaceShip
 */
public class Drunkard extends SpaceShip {

	/**
	 * count the round to activate the shield
	 */
	private int shield_round_counter = 0;

	/** The number of rounds to wait till turning on again the shield */
	private static final double NUMBER_OF_ROUNDS_TO_START_SHIELD = 50;

	/** The number of rounds to wait till turning off the shield */
	private static final double NUMBER_OF_ROUNDS_TO_STOP_SHIELD = 100;


	/**
	 * Does the actions of this ship for this round. This is called once per round by the SpaceWars game
	 * driver. This ship is drunk so it always accelerates to the left. It always tries to fire and it turns
	 * on and off the shield every 50 rounds.
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {
		//------------- Resets -------------
		shield_is_on = false;

		//------------- Accelerate and Turn -------------
		getPhysics().move(ACCELERATE, TURN_LEFT);

		//------------- Shield -------------
		shield_round_counter++;
		if (shield_round_counter >= NUMBER_OF_ROUNDS_TO_START_SHIELD) {
			shieldOn();
			if (shield_round_counter == NUMBER_OF_ROUNDS_TO_STOP_SHIELD) {
				shield_round_counter = 0;
			}
		}

		//------------- Firing a shot -------------
		if (rounds_left_after_fire != NO_ROUNDS_LEFT_AFTER_FIRE) {
			rounds_left_after_fire--;
		} else {
			fire(game);
		}

		//------------- Add 1 Energy unit -------------
		if (current_energy < max_energy) {
			current_energy++;
		}
	}


	/**
	 * Gets the image of this ship. This method should return the image of the ship with or without the
	 * shield. This will be displayed on the GUI at the end of the round.
	 * @return the image of this ship.
	 */
	public Image getImage() {
		if (shield_is_on) {
			return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
		}
		return GameGUI.ENEMY_SPACESHIP_IMAGE;
	}

}

