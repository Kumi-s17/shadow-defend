/**
 * Wave event is a single spawn event or single delay event in a wave
 */
public class WaveEvent {
    private String waveType;
    private int numberToSpawn;
    private String enemyType;
    private double spawnDelay;
    private int spawnedSlicers;
    private double delay;
    private static final double MILLISECOND_PER_SECOND = 1000;

    /**
     * creates a new SpawnEvent
     *
     * @param numberToSpawn  The number of slicers to spawn in the spawn event
     * @param enemyType  The type of slicer that is spawned in the spawn event
     * @param spawnDelayMillisecond   The spawn delay in milliseconds between slicers spawned in the spawn event
     */
    public WaveEvent(int numberToSpawn, String enemyType, double spawnDelayMillisecond){
        this.waveType = "spawn";
        this.numberToSpawn = numberToSpawn;
        this.enemyType = enemyType;
        this.spawnDelay = spawnDelayMillisecond / MILLISECOND_PER_SECOND;
        this.spawnedSlicers = 0;

    }

    /**
     * creates a new DelayEvent
     *
     * @param delayMillisecond  The number of milliseconds of delay in the delay event
     */
    public WaveEvent(Double delayMillisecond){
        this.waveType = "delay";
        this.delay = delayMillisecond / MILLISECOND_PER_SECOND;
    }

    public String getWaveType() {
        return waveType;
    }

    public int getNumberToSpawn() {
        return numberToSpawn;
    }

    public String getEnemyType() {
        return enemyType;
    }

    public double getSpawnDelay() {
        return spawnDelay;
    }

    public int getSpawnedSlicers() {
        return spawnedSlicers;
    }

    public void setSpawnedSlicers(int spawnedSlicers) {
        this.spawnedSlicers = spawnedSlicers;
    }

    public double getDelay() {
        return delay;
    }

    /**
     * award the player money after a wave is completed
     * @param level  The current level of the game
     */
    public static void rewardCash(Level level){
        double rewardAmount = 150 + level.getCurrentWaveNumber() * 100;
        level.setCash(level.getCash() + rewardAmount);
    }

}
