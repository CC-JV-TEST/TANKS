package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.utils.Vector2D;

public class WeaponFactory {
    public Entity create(){
        Entity e = new Bullet();
        e.add( new MovementPart(new Vector2D(25, 22), 10000, 10));
        e.add(new PositionPart(30,30, (float) (Math.PI/4)));
        e.add(new ShapePart());
        e.add(new CirclePart(30,30,1));
        e.add(new PhysicsPart(30, -9.82f));
        e.add(new TexturePart("fakePath.png"));
        return e;
    }

    public Entity create(Event ev, GameData gameData) {
        Entity e = new Bullet();

        CannonPart cannonPart = ev.getSource().getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        e.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelecationVector = cannonPart.getDirectionVector();
        accelecationVector.multiplyWithConstant(100);
        e.add( new MovementPart(accelecationVector, 10000, 10));

        e.add(new ShapePart());
        e.add(new CirclePart(30,30,1));
        e.add(new PhysicsPart(30, -9.82f));
        e.add(new CollisionPart(true,0));
        e.add(new TexturePart("fakePath.png"));
        return e;
    }
}