package com.herobrine.mod.util.blocks;

import com.herobrine.mod.util.misc.ElementsHerobrine;
import com.herobrine.mod.util.misc.Variables;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.NotNull;

@ElementsHerobrine.ModElement.Tag
public class ActivateAlter extends ElementsHerobrine.ModElement {

    public ActivateAlter(ElementsHerobrine instance) {
        super(instance, 3);
    }

    public static void executeProcedure(@NotNull java.util.HashMap<String, Object> dependencies){
        Entity entity = (Entity) dependencies.get("entity");
        int x = (int) dependencies.get("x");
        int y = (int) dependencies.get("y");
        int z = (int) dependencies.get("z");
        World world = (World) dependencies.get("world");
        if (((entity instanceof PlayerEntity) && ((PlayerEntity) entity).inventory.hasItemStack(new ItemStack(ItemList.cursed_diamond, 1))) && (!(Variables.WorldVariables.get(world).Spawn))) {
            assert false;
            entity.sendMessage(new StringTextComponent("<Herobrine> You have no idea what you have done"));
            Variables.WorldVariables.get(world).Spawn = true;
            if (world instanceof ServerWorld)
                ((ServerWorld) world).addLightningBolt(new LightningBoltEntity(world, x, y, z, false));
            if (entity instanceof PlayerEntity && !((PlayerEntity) entity).abilities.isCreativeMode)
                ((PlayerEntity) entity).inventory.clearMatchingItems(p -> new ItemStack(ItemList.cursed_diamond, 1).getItem() == p.getItem(), 1);
        }
    }
}