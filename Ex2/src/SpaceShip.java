import java.awt.Image;

import oop.ex2.*;

import javax.swing.*;

/**
 * The API spaceships need to implement for the SpaceWars game. It is your decision whether SpaceShip.java
 * will be an interface, an abstract class, a base class for the other spaceships or any other option you will
 * choose.
 * @author Jonathan Birnbaum
 */
public abstract class SpaceShip {
	private SpaceShipPhysics shipPhysics;
	protected int max_energy;
	protected int current_energy;
	protected int health;
	protected boolean shield_is_on;
	protected int rounds_left_after_fire;

	/** The start level of maximal energy */
	private static final int START_MAX_ENERGY = 210;

	/** The start level of current energy */
	private static final int START_CURRENT_ENERGY = 190;

	/** The start level of health */
	private static final int START_HEALTH = 22;

	/** when there is no health */
	private static final int NO_HEALTH = 0;

	/** The reduce from the maximal energy level when shot without shield */
	private static final int REDUCE_MAX_ENERGY_WITHOUT_SHIELD = 10;

	/** Teleport energy cost */
	private static final int TELEPORT_ENERGY_COST = 140;

	/** Shield energy cost */
	private static final int SHIELD_ENERGY_COST = 3;

	/** Fire energy cost */
	private static final int FIRE_ENERGY_COST = 19;

	/** Rounds left after fire at the beginning */
	protected static final int NO_ROUNDS_LEFT_AFTER_FIRE = 0;

	/** Rounds left after fire */
	private static final int ROUNDS_LEFT_AFTER_FIRE = 7;

	/** No energy */
	private static final int NO_ENERGY = 0;

	/** Energy added because of bashing */
	private static final int BASH_ENERGY_ADD = 18;

	/** Symbols the ship turns left */
	protected static final int TURN_LEFT = 1;

	/** Symbols the ship turns right */
	protected static final int TURN_RIGHT = (-1);

	/** Symbols the ship doesnt turn */
	protected static final int NO_TURN = 0;

	/** Zero angle degree */
	protected static final double ZERO_ANGLE = 0;

	/** used to symbol accelerate as argument to the move method */
	protected static final boolean ACCELERATE = true;


	/**
	 * SpaceShip constructor sets all the data members to the default and create a new SpaceShipPhysics
	 */
	public SpaceShip() {
		shipPhysics = new SpaceShipPhysics();
		max_energy = START_MAX_ENERGY;
		current_energy = START_CURRENT_ENERGY;
		health = START_HEALTH;
		shield_is_on = false;
		rounds_left_after_fire = NO_ROUNDS_LEFT_AFTER_FIRE;
	}


	/**
	 * Does the actions of this ship for this round. This is called once per round by the SpaceWars game
	 * driver.
	 * @param game the game object to which this ship belongs.
	 */
	abstract void doAction(SpaceWars game);


	/**
	 * This method is called every time a collision with this ship occurs
	 */
	public void collidedWithAnotherShip() {
		if (shield_is_on) {
			max_energy += BASH_ENERGY_ADD;
			current_energy += BASH_ENERGY_ADD;
		} else {
			health--;
			max_energy -= REDUCE_MAX_ENERGY_WITHOUT_SHIELD;
			if (max_energy < NO_ENERGY) {
				max_energy = NO_ENERGY;
			}
			if (current_energy > max_energy) {
				current_energy = max_energy;
			}
		}
	}


	/**
	 * This method is called whenever a ship has died. It resets the ship's attributes, and starts it at a
	 * new
	 * random position.
	 */
	public void reset() {
		shipPhysics = new SpaceShipPhysics();
		max_energy = START_MAX_ENERGY;
		current_energy = START_CURRENT_ENERGY;
		health = START_HEALTH;
		shield_is_on = false;
		rounds_left_after_fire = NO_ROUNDS_LEFT_AFTER_FIRE;
	}


	/**
	 * Checks if this ship is dead.
	 * @return true if the ship is dead. false otherwise.
	 */
	public boolean isDead() {
		return health <= NO_HEALTH;
	}


	/**
	 * Gets the physics object that controls this ship.
	 * @return the physics object that controls the ship.
	 */
	public SpaceShipPhysics getPhysics() {
		return shipPhysics;
	}


	/**
	 * This method is called by the SpaceWars game object when ever this ship gets hit by a shot.
	 */
	public void gotHit() {
		if (!shield_is_on) {
			health--;
			max_energy -= REDUCE_MAX_ENERGY_WITHOUT_SHIELD;
			if (max_energy < NO_ENERGY) {
				max_energy = NO_ENERGY;
			}
			if (current_energy > max_energy) {
				current_energy = max_energy;
			}
		}
	}


	/**
	 * Gets the image of this ship. This method should return the image of the ship with or without the
	 * shield. This will be displayed on the GUI at the end of the round.
	 * @return the image of this ship.
	 */
	abstract Image getImage();


	/**
	 * Attempts to fire a shot.
	 * @param game the game object.
	 */
	public void fire(SpaceWars game) {
		if (current_energy < FIRE_ENERGY_COST) { // has enough energy
			return;
		}
		if (rounds_left_after_fire == NO_ROUNDS_LEFT_AFTER_FIRE) {    // possible to fire
			game.addShot(getPhysics());
			current_energy -= FIRE_ENERGY_COST;
			rounds_left_after_fire = ROUNDS_LEFT_AFTER_FIRE;
		}
	}


	/**
	 * Attempts to turn on the shield.
	 */
	public void shieldOn() {
		if (current_energy < SHIELD_ENERGY_COST) {   // has enough energy
			return;
		}
		current_energy -= SHIELD_ENERGY_COST;
		shield_is_on = true;
	}


	/**
	 * Attempts to teleport.
	 */
	public void teleport() {
		if (current_energy < TELEPORT_ENERGY_COST) { // has enough energy
			return;
		}
		current_energy -= TELEPORT_ENERGY_COST;
		shipPhysics = new SpaceShipPhysics();
	}

}
