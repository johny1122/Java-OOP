import oop.ex2.GameGUI;

import javax.swing.*;
import java.awt.*;

/**
 * A class that represents an Human SpaceShip
 */
public class Human extends SpaceShip {

	/**
	 * Does the actions of this ship for this round. This is called once per round by the SpaceWars game
	 * driver. Controlled by the user. The keys are: left-arrow and right-arrow to turn, up-arrow to
	 * accelerate, ’d’ to fire a shot, ’s’ to turn on the shield, ’a’ to teleport.
	 * @param game the game object to which this ship belongs.
	 */
	public void doAction(SpaceWars game) {
		//------------- Resets -------------
		shield_is_on = false;

		//------------- Teleport -------------
		if (game.getGUI().isTeleportPressed()) {
			teleport();
		}

		//------------- Accelerate and Turn -------------
		boolean left_pressed = game.getGUI().isLeftPressed();
		boolean right_pressed = game.getGUI().isRightPressed();
		int turn;
		if (left_pressed && !right_pressed) {    // just left pressed
			turn = TURN_LEFT;
		} else if (!left_pressed && right_pressed) {     // just right pressed
			turn = TURN_RIGHT;
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
			return GameGUI.SPACESHIP_IMAGE_SHIELD;
		}
		return GameGUI.SPACESHIP_IMAGE;
	}

}
