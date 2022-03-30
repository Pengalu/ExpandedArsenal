package net.fabricmc.example;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ToneGrowthItem extends ToolItem {

    public ToneGrowthItem(ToolMaterial material, Settings settings) {
        super(material,settings);
    }
    public boolean using = false;
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 300;
    }
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {




        if (!world.isClient && user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            player.getItemCooldownManager().set(this, this.getMaxUseTime(stack)*5); // 20 * 5



        }
        using =false;
        return super.finishUsing(stack, world, user);
    }

    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

        if (user instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) user;
            player.getItemCooldownManager().set(this, 5*(this.getMaxUseTime(stack)-remainingUseTicks)); // 20 * 5

            using =false;

        }



    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        using = true;
        //playerEntity.playSound(SoundEvents.ENTITY_VILLAGER_DEATH, 50.0F, 0.70F);
        playerEntity.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 50.0F, 0.45F);

        ItemStack itemStack = playerEntity.getStackInHand(hand);
        playerEntity.setCurrentHand(hand);
        return TypedActionResult.consume(playerEntity.getStackInHand(hand));
    }
    public int ticksToGrow = 20;
   @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
       int radius =  1;

        if (entity instanceof PlayerEntity playerEntity) {

            if (selected) {

                if (using) {
                    if (ticksToGrow == 0){
                        ticksToGrow=20;
                        var blockPos = playerEntity.getBlockPos();
                        var playerPosBelow = new BlockPos(blockPos.getX(),blockPos.getY()-1,blockPos.getZ());
                        BlockState belowState = world.getBlockState(playerPosBelow);
                        Block below = belowState.getBlock();
                        if(below ==Blocks.GRASS_BLOCK){

                            if (playerEntity.getStatusEffect(StatusEffects.REGENERATION) != null
                                    && playerEntity.getStatusEffect(StatusEffects.REGENERATION).getDuration() > 10) return;

                            playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 610,
                                    2, true, false, true));

                        }
                    for (int x = -radius; x <= radius; ++x) {
                        for (int y = -1; y <= 1; ++y) {

                            for (int z = -radius; z <= radius; ++z) {

                                BlockPos cropPos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                                BlockState blockState = world.getBlockState(cropPos);
                                Block block = blockState.getBlock();

                                if (block instanceof CropBlock) {

                                    ((CropBlock) block).applyGrowth(world, cropPos, blockState);


                                } else if (block == Blocks.COBBLESTONE) {

                                    world.setBlockState(cropPos, Blocks.MOSSY_COBBLESTONE.getDefaultState());

                                }


                            }


                        }

                    }

                    }
                    else{

                       ticksToGrow-=1;
                    }

               }

            }
        }
    }
   // public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
     //   ItemStack itemStack = playerEntity.getStackInHand(hand);
       // playerEntity.setCurrentHand(hand);

        //new Thread(){
          //  @Override
            //public void run() {

              //  playerEntity.playSound(SoundEvents.BLOCK_BELL_RESONATE, 50.0F, 1.0F);
               // try {
                 //   Thread.sleep(1000);
                //} catch (InterruptedException e) {
                  //  e.printStackTrace();
                //}
               // playerEntity.playSound(SoundEvents.BLOCK_BELL_USE, 25.0F, 1.0F);

            //}


        //}.start();





       // return TypedActionResult.consume(playerEntity.getStackInHand(hand));
    //}
}