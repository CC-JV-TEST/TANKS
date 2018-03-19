package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMap {
    private List<IGameMapFunction> gameMapFunctions;
    private float GAMEWIDTH;
    private float GAMEHEIGHT;


    /**
     *
     */
    public GameMap(float gameWidth, float gameHeight) {
        gameMapFunctions = new ArrayList<>();
        this.GAMEHEIGHT = gameHeight;
        this.GAMEWIDTH = gameWidth;
    }

    /**
     * Gets all game map functions
     * @return A List of game map functions
     */
    public List<IGameMapFunction> getGameMapFunctions() {
        return gameMapFunctions;
    }

    /**
     * Sets the list of game map functions
     * @param gameMapFunctions A list of game map functions
     */
    public void setGameMapFunctions(List<IGameMapFunction> gameMapFunctions) {
        this.gameMapFunctions = gameMapFunctions;
    }

    /**
     * Adds a gamemap function to the map
     * @param gameMapFunction A game map function
     */
    public void addGameMapFunction(IGameMapFunction gameMapFunction) {
        this.gameMapFunctions.add(gameMapFunction);
    }

    /**
     * Get all vertices in the map.
     *
     * @return List of all vertices of type Vector2D
     */
    public List<Vector2D> getVertices(float startX, float endX, int amountOfVertices) {
        List<Vector2D> vertices = new ArrayList<>();
        for (float x = startX; x <= endX; x+=(endX-startX)/amountOfVertices) {
            for (IGameMapFunction gameMapFunction : gameMapFunctions) {
                if(gameMapFunction.isWithin(x)){
                    float y = gameMapFunction.getYValue(x);
                    vertices.add(new Vector2D(x,y));
                }
            }

        }


        vertices.add(new Vector2D(GAMEWIDTH,0));
        vertices.add(new Vector2D(0,0));
        return vertices;
    }

    /**
     * '
     * Returns the list of vertices as a float array
     *
     * @return float[]
     */
    public float[] getVerticesAsFloats(float startX, float endX, int amountOfVertices) {
        List<Vector2D> vertices = getVertices(startX,endX,amountOfVertices);
        float[] floatVertices = new float[vertices.size() * 2];
        int i = 0;
        for (Vector2D vertex : vertices) {
            floatVertices[i] = vertex.getX();
            i++;
            floatVertices[i] = vertex.getY();
            i++;
        }
        return floatVertices;
    }

    /**
     * Gets the direction at the given x coordinate, as a vector
     * @param xCoordinate
     * @return a unit vector of the direction
     */
    public Vector2D getDirectionVector(float xCoordinate) {
        float y = getHeight(xCoordinate);
        float y2 = getHeight(xCoordinate + 0.1f);
        Vector2D vector = new Vector2D((xCoordinate + 0.1f) - xCoordinate, y2 - y);
        return vector.unitVector();
    }

    /**
     * Gets the height of the map at the given xcoordinate
     * @param x
     * @return The height of the game map, or -1 if outside of the game map
     */
    public float getHeight(float x) {
        for (IGameMapFunction gameMapFunction : gameMapFunctions) {
            if(gameMapFunction.isWithin(x)){
                return gameMapFunction.getYValue(x);
            }
        }
        return -1f;
    }
}