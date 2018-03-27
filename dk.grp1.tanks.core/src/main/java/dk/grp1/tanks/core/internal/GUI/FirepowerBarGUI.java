package dk.grp1.tanks.core.internal.GUI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.GameKeys;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.CannonPart;

public class FirepowerBarGUI implements IGuiProcessingService{
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture firepowerTexture;
    private Pixmap pixmap;
    private float barHeight = 27;
    private float barWidth = 5;
    private float xOffSet = 20;
    private float yOffSet = -5;

    @Override
    public void draw(World world, GameData gameData) {
        for (Entity entity: world.getEntities()) {
            CannonPart cannonPart = entity.getPart(CannonPart.class);
            if (cannonPart != null && gameData.getKeys().isDown(GameKeys.SPACE)){
                firepowerBar(entity, cannonPart);
            }
        }

    }

    private void firepowerBar(Entity entity, CannonPart cannonPart){
        pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        batch = new SpriteBatch();
        pixmap.setColor(Color.CYAN);
        pixmap.fill();
        firepowerTexture = new Texture(pixmap);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(firepowerTexture, cannonPart.getJointX()+xOffSet, cannonPart.getJointY()+yOffSet, barWidth
                , barHeight*cannonPart.getFirepower()/cannonPart.getMaxFirepower());
        batch.end();
        batch.dispose();
        pixmap.dispose();
    }

    @Override
    public void setCam(OrthographicCamera camera) {
        this.camera = camera;
    }
}