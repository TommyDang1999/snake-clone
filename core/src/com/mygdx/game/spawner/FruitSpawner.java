package com.mygdx.game.spawner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.entity.Fruit;
import com.mygdx.game.util.GameDataProvider;

/**
 * Controls the spawning of fruit
 */
public class FruitSpawner {
    /**
     * The current round
     */
    private int round;
    /**
     * The delay between each spawn
     */
    private static final float SPAWN_TIME = 3f;
    /**
     * The maximum number of fruit
     */
    private static final int MAX_FRUIT = 100;
    /**
     * The starting fruit
     */
    private static final int STARTING_FRUIT = 10;
    /**
     * The fruit increase per round
     */
    private static final int FRUIT_INCREASE_PER_ROUND = 2;
    /**
     * The amount of fruit in the current round
     */
    private int currentRoundFruit;
    /**
     * The index of the next fruit
     */
    private int nextFruit;
    /**
     * The time until the next spawn
     */
    private float currentSpawnTime;
    /**
     * The number of fruit yet to be eaten
     */
    private int uneatenFruit;
    /**
     * If the spawner is spawning any fruit
     */
    private boolean isSpawning;
    /**
     * The fruit
     */
    private Fruit[] fruits;

    /**
     * Fruit Constructor with no parameters, the starting round is defined by the GameDataProvider
     */
    public FruitSpawner() {
        this(GameDataProvider.STARTING_ROUND);
    }

    public FruitSpawner(int startingRound) {
        round = startingRound;
        fruits = new Fruit[MAX_FRUIT];
        newRound();
    }

    /**
     * Resets the spawner for the next round, automatically called when the round value is changed
     */
    public void newRound() {
        int temp = STARTING_FRUIT + (FRUIT_INCREASE_PER_ROUND * round);
        currentRoundFruit = (temp < MAX_FRUIT ? temp : MAX_FRUIT);
        currentSpawnTime = SPAWN_TIME;
        uneatenFruit = currentRoundFruit;
        nextFruit = 0;
    }

    /**
     * Increments the spawner to the next round
     */
    public void incrementRound() {
        ++round;
        newRound();
    }

    /**
     * Starts the spawner
     */
    public void start() {
        isSpawning = true;
    }

    /**
     * Stops the spawner
     */
    public void stop() {
        isSpawning = false;
    }

    /**
     * If the spawner is spawning fruit
     * @return True if spawning, false otherwise
     */
    public boolean isSpawning() {
        return isSpawning;
    }

    /**
     * If the spawner is expended for this round
     * @return True if expended, false otherwise
     */
    public boolean isExpended() {
        return nextFruit >= currentRoundFruit;
    }

    /**
     * If all of the fruit has been ate
     * @return True if all the fruit have been ate, false otherwise
     */
    public boolean allAte() {
        return isExpended() && uneatenFruit == 0;
    }

    /**
     * Cleans up a fruit after being eaten
     * @param index The index of the fruit that was eaten
     */
    public void eatFruit(int index) {
        fruits[index].setAte(true);
        --uneatenFruit;
    }

    /**
     * Updates the state of the spawner
     * @param elapsedTime The time elapsed since the previous frame
     */
    public void update(float elapsedTime) {
        if(isSpawning) {
            currentSpawnTime -= elapsedTime;
            if(currentSpawnTime <= 0) {
                // add new fruit

                ++nextFruit;
                if(nextFruit >= currentRoundFruit) {
                    stop();
                }
                currentSpawnTime = SPAWN_TIME;
            }
        }
    }

    /**
     * Renders the fruit to the screen
     * @param batch The sprite batch
     */
    public void render(SpriteBatch batch) {
        for(int i = 0; i < currentRoundFruit; ++i) {
            if(fruits[i] != null && !fruits[i].isAte()) {
                fruits[i].render(batch);
            }
        }
    }

    public void dispose() {
        for(Fruit f : fruits) {
            if(f != null) {
                f.dispose();
            }
        }
    }
}