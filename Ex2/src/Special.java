import oop.ex2.GameGUI;

import javax.swing.*;
import java.awt.*;

/**
 * A class that represents a Special SpaceShip
 */
public class Special extends SpaceShip {

	/**
	 * Does the actions of this ship for this round. This is called once per round by the SpaceWars game
	 * driver. This ship is from a parallel universe of the Human spaceship. It will turn to the opposite
	 * direction as the Human spaceship and will fire and turn on the shield when the Human spaceship does
	 * but
	 * will also teleport when the Human fires - this is because it has a spy on the Human spaceship that
	 * sends messages from the cockpit about his plan (I wonder who it could be...)
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {
		//------------- Resets -------------
		shield_is_on = false;

		//------------- Teleport -------------
		if (game.getGUI().isShotPressed()) {
			teleport();
		}

		//------------- Accelerate and Turn -------------
		boolean left_pressed = game.getGUI().isLeftPressed();
		boolean right_pressed = game.getGUI().isRightPressed();
		int turn;
		if (left_pressed && !right_pressed) {    // just left pressed
			turn = TURN_RIGHT;
		} else if (!left_pressed && right_pressed) {     // just right pressed
			turn = TURN_LEFT;
		} else {    // not right and not left
			turn = NO_TURN;
		}
		getPhysics().move(game.getGUI().isUpPressed(), turn);

		//------------- Shield -------------
		if (game.getGUI().isShieldsPressed()) {
			shieldOn();
		}

		//------------- Firing a shot -------------
		if (rounds_left_after_fire != NO_ROUNDS_LEFT_AFTER_FIRE) {
			rounds_left_after_fire--;
		} else if (game.getGUI().isShotPressed()) {
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

