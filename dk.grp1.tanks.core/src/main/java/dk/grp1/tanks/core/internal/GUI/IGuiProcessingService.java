package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;

public interface IGuiProcessingService {

    public void draw(World world, GameData gameData);

    /**
     * Sets the camera of this UI processor to match the game's
     * @param camera
     */
    public void setCam(OrthographicCamera camera);
}