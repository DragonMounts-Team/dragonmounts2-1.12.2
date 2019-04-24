package com.TheRPGAdventurer.ROTD.server.initialization;

import com.TheRPGAdventurer.ROTD.server.blocks.BlockDragonNest;
import com.TheRPGAdventurer.ROTD.server.blocks.BlockDragonShulker;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;
import java.util.List;

public class ModBlocks
{
	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	public static final Block pileofsticks = new BlockDragonNest("pileofsticks", Material.WOOD);
	public static final Block dragonshulker = new BlockDragonShulker("block_dragon_shulker");
//	public static final Block pileofsticks = new BlockBase("pileofstickssnow");
	
}