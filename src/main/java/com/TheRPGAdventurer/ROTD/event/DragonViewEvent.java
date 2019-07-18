package com.TheRPGAdventurer.ROTD.event;

import com.TheRPGAdventurer.ROTD.DragonMounts;
import com.TheRPGAdventurer.ROTD.DragonMountsConfig;
import com.TheRPGAdventurer.ROTD.inits.ModKeys;
import com.TheRPGAdventurer.ROTD.objects.entity.entitycarriage.EntityCarriage;
import com.TheRPGAdventurer.ROTD.objects.entity.entitytameabledragon.EntityTameableDragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DragonViewEvent {
    Minecraft mc = Minecraft.getMinecraft();

    private double blockHit(EntityTameableDragon dragon, double thirdPersonDistancePrev) {
        double d3 = thirdPersonDistancePrev;
        float eyeHeight = dragon.getEyeHeight();
        double x = dragon.prevPosX + (dragon.posX - dragon.prevPosX);
        double y = dragon.prevPosY + (dragon.posY - dragon.prevPosY) + (double) eyeHeight;
        double z = dragon.prevPosZ + (dragon.posZ - dragon.prevPosZ);
        float yaw = dragon.rotationYaw;
        float pitch = dragon.rotationPitch;
//        if (this.mc.gameSettings.thirdPersonView == 2) {
//            yaw += 180.0F;
//        }
        double x1 = (double) (-MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F)) * d3;
        double y1 = (double) (-MathHelper.sin(pitch * 0.017453292F)) * d3;
        double z1 = (double) (MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F)) * d3;


        for (int i = 0; i < 8; ++i) {
            float f3 = (float) ((i & 1) * 2 - 1);
            float f4 = (float) ((i >> 1 & 1) * 2 - 1);
            float f5 = (float) ((i >> 2 & 1) * 2 - 1);
            f3 = f3 * 0.1F;
            f4 = f4 * 0.1F;
            f5 = f5 * 0.1F;

            // dragon's position/coordinates
            Vec3d start = new Vec3d(x + (double) f3, y + (double) f4, z + (double) f5);
            // behind the dragon
            Vec3d end = new Vec3d(x - x1 + (double) f3 + (double) f5, y - y1 + (double) f4, z - z1 + (double) f5);
            RayTraceResult raytraceresult = this.mc.world.rayTraceBlocks(start, end);

            if (raytraceresult != null) {
                double rayHitVecDist = raytraceresult.hitVec.distanceTo(new Vec3d(x, y, z));
                if (rayHitVecDist < d3) {
                    d3 = rayHitVecDist;
                }
            }
        }

        return -d3;
    }

    /**
     * Credit to AlexThe666 : iceandfire
     *
     * @param event
     */
    @SubscribeEvent
    public void extendZoom(EntityViewRenderEvent.CameraSetup event) {
        EntityPlayer player = Minecraft.getMinecraft().player;
        int currentView = DragonMounts.proxy.getDragon3rdPersonView();

        if (player.getRidingEntity() instanceof EntityTameableDragon) {
            EntityTameableDragon dragon = (EntityTameableDragon) player.getRidingEntity();
            double blockHit = blockHit(dragon, DragonMountsConfig.ThirdPersonZoom);
//            double blockHit = DragonMountsConfig.ThirdPersonZoom * dragon.getScale();
            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
                GlStateManager.translate(0F, -0.6F * dragon.getScale(), 0);
            }

            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
                if (currentView == 0) {
                    GlStateManager.translate(0F, -1.3F * dragon.getScale(), -blockHit);
                } else if (currentView == 1) {
                    GlStateManager.translate(-4.7F, -0.08F * dragon.getScale(), -blockHit);
                } else if (currentView == 2) {
                    GlStateManager.translate(4.7F, -0.08F * dragon.getScale(), -blockHit);
                }
            }

            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                if (currentView == 0) {
                    GlStateManager.translate(0F, -1.3F * dragon.getScale(), blockHit);
                } else if (currentView == 1) {
                    GlStateManager.translate(-4.7F, -0.08F * dragon.getScale(), blockHit);
                } else if (currentView == 2) {
                    GlStateManager.translate(4.7F, -0.08F * dragon.getScale(), blockHit);
                }
            }
        } else if (player.getRidingEntity() instanceof EntityCarriage) {
            EntityCarriage carriage = (EntityCarriage) player.getRidingEntity();
            if (carriage.getRidingEntity() instanceof EntityTameableDragon) {
                EntityTameableDragon dragon = (EntityTameableDragon) carriage.getRidingEntity();
                double blockHit = blockHit(dragon, DragonMountsConfig.ThirdPersonZoom * dragon.getScale());
//                double blockHit = DragonMountsConfig.ThirdPersonZoom * dragon.getScale();
                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
                    GlStateManager.translate(0F, -0.9F, 0);
                }

                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 1) {
                    if (currentView == 0) {
                        GlStateManager.translate(0F, -1.3F * dragon.getScale(), -blockHit);
                    } else if (currentView == 1) {
                        GlStateManager.translate(4.7F, -0.08F * dragon.getScale(), -blockHit);
                    } else if (currentView == 2) {
                        GlStateManager.translate(-4.7F, -0.08F * dragon.getScale(), -blockHit);
                    }
                }

                if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
                    if (currentView == 0) {
                        GlStateManager.translate(0F, -1.3F * dragon.getScale(), blockHit);
                    } else if (currentView == 1) {
                        GlStateManager.translate(4.7F, -0.08F * dragon.getScale(), blockHit);
                    } else if (currentView == 2) {
                        GlStateManager.translate(-4.7F, -0.08F * dragon.getScale(), blockHit);
                    }
                }
            } else {
                GlStateManager.translate(0F, -0.5F, 0F);
            }
        }
    }

//    @SubscribeEvent
//    public void rideDragonGameOverlay(RenderGameOverlayEvent.Pre event) {
//        EntityPlayer player = Minecraft.getMinecraft().player;
//        if (player.getRidingEntity() instanceof EntityTameableDragon) {
//            EntityTameableDragon dragon = (EntityTameableDragon) player.getRidingEntity();
//            if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) event.setCanceled(true);
//                GuiDragonRide rideGui = new GuiDragonRide(dragon);
//                rideGui.renderDragonBoostHotbar();
//        }
//    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (player.getRidingEntity() instanceof EntityTameableDragon) {
                if (ModKeys.dragon_change_view.isPressed()) {
                    int currentView = DragonMounts.proxy.getDragon3rdPersonView();
                    if (currentView + 1 > 2) {
                        currentView = 0;
                    } else {
                        currentView++;
                    }

                    DragonMounts.proxy.setDragon3rdPersonView(currentView);
                }
            }
        }
    }
}