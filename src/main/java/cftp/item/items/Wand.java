package cftp.item.items;

import cftp.CFTP;
import cftp.entity.charge.EarthChargeEntity;
import cftp.entity.charge.WaterChargeEntity;
import cftp.registries.CFTPItems;
import cftp.registries.CFTPTags;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class Wand extends RangedWeaponItem {

    public static final Predicate<ItemStack> PROJECTILES = stack -> stack.isIn(CFTPItems.CHARGES);

    public Entity controlledProjectile;

    public Wand(Settings settings) {
        super(settings);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return PROJECTILES;
    }

        //return stack -> stack.getItem() == CFTPTags.CHARGES;
        //return BOW_PROJECTILES;
    //}

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        projectile.setVelocity(shooter, shooter.getPitch(), shooter.getYaw() + yaw, 0.0F, speed, divergence);
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return false;

        boolean creative = player.getAbilities().creativeMode;
        ItemStack ammoStack = player.getProjectileType(stack);

        //if (world instanceof ServerWorld serverWorld) {
            var direction = user.getRotationVec(1.0F); // direction player is looking
            var velocity = direction.multiply(1.5);    // speed of projectile

            controlledProjectile.setVelocity(velocity);
        //}

        if (!creative) {
            ammoStack.decrement(1);
        }

        //if (!ammoStack.isEmpty() || creative) {
        //    if (!creative) {
        //        ammoStack.decrement(1);
        //    }

            //var lookVector = player.getRotationVec(1.0F);
            //var position = player.getPos();
            //position.add(lookVector.multiply(4.0));

            //if (!world.isClient) {
            //    // Spawn your projectile
            //    CustomBulletEntity bullet = new CustomBulletEntity(world, player);
            //    bullet.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 3.0F, 1.0F);
            //    world.spawnEntity(bullet);
            //}

            //world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0F, 1.0F);
        //}

        //player.getItemCooldownManager().set(this, 20); // 1-second cooldown
        return creative;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        //CFTP.LOGGER.info("press!");
        //ItemStack itemStack = user.getStackInHand(hand);
        //boolean bl = !user.getProjectileType(itemStack).isEmpty();
        //
        //if (!user.isInCreativeMode() && !bl) {
        //    return ActionResult.FAIL;
        //} else {
        //    user.setCurrentHand(hand);
        //    return ActionResult.CONSUME;
        //}

        //ItemStack itemStack = user.getStackInHand(hand);

        //if (world instanceof ServerWorld serverWorld) {

        ItemStack wand = user.getStackInHand(hand);
        ItemStack ammoStack = user.getProjectileType(wand);

        final float RADIUS = 2.0f;
        var lookVector = user.getRotationVec(1.0F);
        var position = user.getEyePos();
        position = position.add(lookVector.multiply(RADIUS));

        Item item = Registries.ITEM.get(Identifier.of("minecraft", "fire_charge"));
        System.out.println("Item is in CHARGES? " + item.getDefaultStack().isIn(CFTPItems.CHARGES));

        for (ItemStack stack : user.getInventory().main) {
            if (stack.isIn(CFTPItems.CHARGES)) {
                System.out.println("Found valid charge: " + stack);
            }
        }

        ItemStack ammo = user.getProjectileType(wand);
        System.out.println("Found projectile: " + ammo);

        if (ammoStack.getItem() == Items.FIRE_CHARGE) {
            controlledProjectile = new WaterChargeEntity(user, world, position.getX(), position.getY(), position.getZ());
            CFTP.LOGGER.info("fire_c!");
        } else if (ammoStack.getItem() == Items.WIND_CHARGE) {
            controlledProjectile = new WindChargeEntity(user, world, position.getX(), position.getY(), position.getZ());
            CFTP.LOGGER.info("wind_c!");
        } else if (ammoStack.getItem() == CFTPItems.EARTH_CHARGE) {
            controlledProjectile = new EarthChargeEntity(user, world, position.getX(), position.getY(), position.getZ());
            CFTP.LOGGER.info("earth_c!");
        } else if (ammoStack.getItem() == CFTPItems.WATER_CHARGE) {
            controlledProjectile = new WaterChargeEntity(user, world, position.getX(), position.getY(), position.getZ());
            CFTP.LOGGER.info("water_c!");
        } else if (ammoStack.getItem() == Items.ARROW) {
            controlledProjectile = new WaterChargeEntity(user, world, position.getX(), position.getY(), position.getZ());
            CFTP.LOGGER.info("arrow!");
        } else {
            return ActionResult.FAIL;
        }

        if (!ammoStack.isEmpty()) {

            controlledProjectile.setVelocity(0.0f, 0.0f, 0.0f);
            world.spawnEntity(controlledProjectile);

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.playSound(
                        null,
                        user.getX(), user.getY(), user.getZ(),
                        SoundEvents.ENTITY_WIND_CHARGE_THROW,
                        SoundCategory.NEUTRAL,
                        0.5F,
                        0.4F / (serverWorld.getRandom().nextFloat() * 0.4F + 0.8F)
                );
            }

            user.setCurrentHand(hand);
            return ActionResult.CONSUME;

        } else {
            return ActionResult.FAIL;
        }


    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        //if (world instanceof ServerWorld serverWorld) {
            final float RADIUS = 2.0f;

            var lookVector = user.getRotationVec(1.0F);
            var position = user.getEyePos();

            position = position.add(lookVector.multiply(RADIUS));

            ////controlledProjectile.setPosition(position);
            controlledProjectile.refreshPositionAndAngles(position.x, position.y, position.z, controlledProjectile.getYaw(), controlledProjectile.getPitch());
        //}
    }

}
