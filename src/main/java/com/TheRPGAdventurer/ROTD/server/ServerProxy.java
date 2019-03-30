/*
 ** 2012 August 27
 **
 ** The author disclaims copyright to this source code.  In place of
 ** a legal notice, here is a blessing:
 **    May you do good and not evil.
 **    May you find forgiveness for yourself and forgive others.
 **    May you share freely, never taking more than you give.
 */
package com.TheRPGAdventurer.ROTD.server;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.server.cmd.CommandDragon;
import com.TheRPGAdventurer.ROTD.server.entity.EntityCarriage;
import com.TheRPGAdventurer.ROTD.server.entity.EntityTameableDragon;
import com.TheRPGAdventurer.ROTD.server.handler.DragonEggBlockHandler;
import com.TheRPGAdventurer.ROTD.server.items.entity.ImmuneEntityItem;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * @author Nico Bergemann <barracuda415 at yahoo.de>
 * 2nd @author TheRPGAdventurer
 */
public class ServerProxy {

    private SimpleNetworkWrapper network;
    public final byte DCM_DISCRIMINATOR_ID = 35;  // arbitrary non-zero ID (non-zero makes troubleshooting easier)
    public final byte DOT_DISCRIMINATOR_ID = 73;  // arbitrary non-zero ID (non-zero makes troubleshooting easier)

    private final int ENTITY_TRACKING_RANGE = 80;
    private final int ENTITY_UPDATE_FREQ = 5; // 3
    private final int ENTITY_ID = 1;
    private final boolean ENTITY_SEND_VELO_UPDATES = true;

    public SimpleNetworkWrapper getNetwork() {
        return this.network;
    }

    public void PreInitialization(FMLPreInitializationEvent event) {
        DragonMountsConfig.PreInit();
    }


    public void Initialization(FMLInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(new DragonEggBlockHandler());
        network = NetworkRegistry.INSTANCE.newSimpleChannel("DragonControls");
    }

    public void PostInitialization(FMLPostInitializationEvent event) {
        registerEntities();

    }

    public void ServerStarting(FMLServerStartingEvent evt) {
        MinecraftServer server = evt.getServer();
        ServerCommandManager cmdman = (ServerCommandManager) server.getCommandManager();
        cmdman.registerCommand(new CommandDragon());
    }

    public void ServerStopped(FMLServerStoppedEvent evt) {
    }

    private void registerEntities() {
        EntityRegistry.registerModEntity(new ResourceLocation(DragonMounts.MODID, "dragon"), EntityTameableDragon.class, "DragonMount",
                ENTITY_ID, DragonMounts.instance, ENTITY_TRACKING_RANGE, ENTITY_UPDATE_FREQ,
                ENTITY_SEND_VELO_UPDATES);
        EntityRegistry.registerModEntity(new ResourceLocation(DragonMounts.MODID, "carriage"), EntityCarriage.class, "DragonCarriage",
                2, DragonMounts.instance, 32, ENTITY_UPDATE_FREQ,
                ENTITY_SEND_VELO_UPDATES);
        EntityRegistry.registerModEntity(new ResourceLocation(DragonMounts.MODID, "indestructible"), ImmuneEntityItem.class, "Indestructible Item",
                3, DragonMounts.instance, 32, 5, true);

    }

    public void render() {
    }

    public int getDragon3rdPersonView() {
        return 0;
    }

    public void setDragon3rdPersonView(int view) {
    }

    public int getDragonFollowYaw() {
        return 0;
    }

    public void setDragonFollowYaw(int yaw) {
    }

    public int getDragonHover() {
        return 0;
    }

    public void setDragonHover(int hover) {
    }

}