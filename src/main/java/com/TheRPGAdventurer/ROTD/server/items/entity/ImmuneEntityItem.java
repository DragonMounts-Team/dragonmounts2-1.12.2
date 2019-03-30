package com.TheRPGAdventurer.ROTD.server.items.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

public class ImmuneEntityItem extends EntityItem {

    public ImmuneEntityItem(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        isImmuneToFire = true;
    }

    public ImmuneEntityItem(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
        isImmuneToFire = true;
    }

    public ImmuneEntityItem(World worldIn) {
        super(worldIn);
        isImmuneToFire = true;
    }

    @Override
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if (source.getDamageType().equals(DamageSource.OUT_OF_WORLD.damageType)) {
            return true;
        }
        // prevent any damage besides out of world
        return false;
    }

    public static class EventHandler {

        public static final EventHandler instance = new EventHandler();

        private EventHandler() {
        }

        @SubscribeEvent
        public void onExpire(ItemExpireEvent event) {
            if (event.getEntityItem() instanceof ImmuneEntityItem) {
                event.setCanceled(true);
            }
        }
    }
}
