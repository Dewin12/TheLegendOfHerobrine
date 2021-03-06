package com.herobrine.mod.entities;

import com.herobrine.mod.util.entities.EntityRegistry;
import com.herobrine.mod.util.items.ItemList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnholyWaterEntity extends SnowballEntity {
    public UnholyWaterEntity(EntityType<? extends UnholyWaterEntity> entityType, World world) {
        super(entityType, world);
    }

    public UnholyWaterEntity(World worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }

    @SuppressWarnings("unchecked")
    public UnholyWaterEntity(World worldIn) {
        this((EntityType<? extends UnholyWaterEntity>) EntityRegistry.UNHOLY_WATER_ENTITY, worldIn);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public @NotNull ItemStack getItem() {
        return new ItemStack(ItemList.unholy_water, 1);
    }

    @Override
    protected void onImpact(@NotNull RayTraceResult result) {
        if (!this.world.isRemote) {
            AxisAlignedBB axisalignedbb = this.getBoundingBox().grow(1.0D, 1.0D, 1.0D);
            List<LivingEntity> list = this.world.getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);
            if (!list.isEmpty()) {
                for (LivingEntity entity : list) {
                    entity.addPotionEffect(new EffectInstance(Effects.WITHER, 300, 1));
                    entity.addPotionEffect(new EffectInstance(Effects.HUNGER, 300, 1));
                    entity.setFire(15);
                    entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 4.0F);
                }
            }
            this.playSound(SoundEvents.BLOCK_GLASS_BREAK, 0.8F,  0.9F / (rand.nextFloat() * 0.4F + 0.8F));
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }
}