package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameMap;
import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.IEventCallback;
import dk.grp1.tanks.common.eventManager.events.GameMapChangedEvent;
import dk.grp1.tanks.common.eventManager.events.MapDestructionEvent;
import dk.grp1.tanks.common.services.IGamePluginService;

import java.util.Random;

public class GameMapPlugin implements IGamePluginService {
    private final float BOTTOMBOUNDARY = 30f;
    private final float TOPBOUNDARY = 400f;
    private IEventCallback eventCallback;

    @Override
    public void start(World world, GameData gameData) {
        world.setGameMap(createNewGameMap(gameData));
        eventCallback =  new GameMapProcessing(gameData,world);
        gameData.getEventManager().register(MapDestructionEvent.class, eventCallback);
        gameData.getEventManager().addEvent(new GameMapChangedEvent(null));
        System.out.println("Created a new game map");
    }


    /**
     * Creates a simple square map
     *
     * @param gameData
     * @return
     */
    private GameMap createNewGameMap(GameData gameData) {
        GameMap map = new GameMap(gameData.getGameWidth(), gameData.getGameHeight());
        //Generate map functions
        generateRandomMap(map, gameData);

        return map;
    }

    private void generateRandomMap(GameMap map, GameData gameData) {
        Random random = new Random();
        int amountOfFunctions = random.nextInt(10); //Prevent 0 functions
        float mapFunctionInterval = (gameData.getGameWidth() / amountOfFunctions);
        //Pick the first function randomly
        //System.out.println("Amount of functions: " + amountOfFunctions + " Map function interval: " + mapFunctionInterval);
        IGameMapFunction predecessor = generateRandomFirstFunction(gameData, mapFunctionInterval);
        map.addGameMapFunction(predecessor);
        for (int i = 1; i < amountOfFunctions; i++) {
            switch (random.nextInt(2)) {
                case 0:
                    IGameMapFunction mapFunction1 = generateLinearMapFunction(predecessor,random, mapFunctionInterval);
                    map.addGameMapFunction(mapFunction1);
                    predecessor = mapFunction1;
                    break;
                case 1:
                    IGameMapFunction mapFunction2 = generateSinMapFunction(predecessor,random,mapFunctionInterval);
                    map.addGameMapFunction(mapFunction2);
                    predecessor = mapFunction2;
                    break;
            }
        }
    }

    private IGameMapFunction generateLinearMapFunction(IGameMapFunction predecessor, Random random, float mapFunctionInterval) {
        IGameMapFunction toReturn = new GameMapLinear((random.nextFloat()*2f-1f), predecessor.getEndX(), (predecessor.getEndX() + mapFunctionInterval), predecessor);
        int maxIterations = 100;
        int iteration = 0;
        while(isFunctionExceedingBoundaries(toReturn) && maxIterations >= iteration){
            toReturn = new GameMapLinear((random.nextFloat()*2f-1f), predecessor.getEndX(), (predecessor.getEndX() + mapFunctionInterval), predecessor);
            iteration++;
        }
        return toReturn;
    }

    private IGameMapFunction generateSinMapFunction(IGameMapFunction predecessor, Random random, float mapFunctionInterval){
        IGameMapFunction toReturn = new GameMapSin(100f,(1/66f),0,predecessor,predecessor.getEndX(),predecessor.getEndX()+mapFunctionInterval);
        int maxIterations = 100;
        int iteration = 0;
        while(isFunctionExceedingBoundaries(toReturn)&& maxIterations >= iteration){
            toReturn = new GameMapSin(random.nextFloat()*100f,(1/66f),0,predecessor,predecessor.getEndX(),predecessor.getEndX()+mapFunctionInterval);
            iteration++;
        }
        return toReturn;

    }

    private boolean isFunctionExceedingBoundaries(IGameMapFunction toReturn) {
        for (float i = toReturn.getStartX(); i < toReturn.getEndX(); i+=1f) {
            if(toReturn.getYValue(i) < BOTTOMBOUNDARY || toReturn.getYValue(i) > TOPBOUNDARY){
                return true;
            }
        }
        return false;
    }

    private IGameMapFunction generateRandomFirstFunction(GameData gameData, float mapFunctionInterval) {
        Random random = new Random();
        switch (random.nextInt(2)) {
            case 0:
                return generateLinearMapFunction(new GameMapLinear(random.nextFloat() * 2f - 1f, BOTTOMBOUNDARY + (random.nextFloat() * TOPBOUNDARY), 0f, 1f),random,mapFunctionInterval);
            case 1:
                return new GameMapSin(100f, (1 / 66f), 0, gameData.getGameHeight() / 3f, 0f, mapFunctionInterval);
        }
        return new GameMapLinear(0, BOTTOMBOUNDARY, 0, mapFunctionInterval);
    }


    @Override
    public void stop(World world, GameData gameData) {
        System.out.println("Map stopped");
        gameData.getEventManager().unRegister(MapDestructionEvent.class, eventCallback);
        gameData.getEventManager().addEvent(new GameMapChangedEvent(null));
        if(world.getGameMap() != null){
            world.getGameMap().getGameMapFunctions().clear();
        }
    }
}
