package net.fabricmc.example;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.concurrent.TimeUnit;

public class BellItem extends Item {

    public BellItem(Settings settings) {
        super(settings);
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        new Thread(){
            @Override
            public void run() {

                playerEntity.playSound(SoundEvents.BLOCK_BELL_USE, 50.0F, 1.0F);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                playerEntity.playSound(SoundEvents.BLOCK_BELL_USE, 25.0F, 1.0F);

            }


        }.start();





        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}