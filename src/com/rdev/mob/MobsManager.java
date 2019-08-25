package com.rdev.mob;

import com.rdev.entityai.MobsBase;
import com.rdev.entityai.ZombieMobBaseEntity;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class MobsManager {

    @Getter private final List<MobMachine> mobs = new ArrayList<>();

    public MobMachine buildMiniatureMobs(String name, MobsBase baseMob) {
        MobMachine mobMachine = new MobMachine(name, baseMob);
        mobs.add(mobMachine);
        return mobMachine;
    }

    public void removeAll() {
        //Created in order to avoid ConcurrentModificationException.
        List<MobMachine> cloneList = new ArrayList<>();
        cloneList.addAll(mobs);

        cloneList.forEach(mobMachine -> {
            remove(mobMachine);
        });
    }

    public void remove(MobMachine mobMachine) {
        if(!(mobMachine.getBaseMob().getEntity() == null || mobMachine.getBaseMob().getEntity().isDead()))
            mobMachine.getBaseMob().getEntity().remove();

        if(mobMachine.getParts().keySet() != null )mobMachine.getParts().keySet().forEach(part -> {part.getArmorstand().remove();});
        if(mobMachine.getNametag() != null)mobMachine.getNametag().remove();
        mobs.remove(mobMachine);
    }

    public MobMachine getMachineByEntity(LivingEntity matchMob) {
        for(MobMachine mb : mobs)
            if(mb.getBaseMob().getEntity().equals(matchMob)) return mb;
        return null;
    }
}
