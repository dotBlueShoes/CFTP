package cftp.entity.creeper;

import cftp.entity.base.CreeperElementalEntity;
import cftp.goals.CreeperElementalIgniteGoal;

import cftp.utility.CreeperMath;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl;
import net.fabricmc.fabric.mixin.registry.sync.RegistriesAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.minecraft.world.explosion.ExplosionImpl;

import java.util.Optional;
import java.util.function.Function;

public class CreeperWindEntity extends CreeperElementalEntity {

    //private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
    //        false, false, Optional.of(1.22F),
    //        //Registries.BLOCK.getOptional(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity())
    //        Registries.BLOCK.getOptional(
    //                TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("blocks_wind_charge_explosions"))
    //        ).map(Function.identity())
    //        //TagKey.of(RegistryKeys.BLOCK, Identifier.of("minecraft", "blocks_wind_charge_explosions"));
    //);

    protected int ExplosionDiameter = 5;

    public CreeperWindEntity(
            EntityType<? extends CreeperElementalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MAX_HEALTH, 20)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25f)
                .add(EntityAttributes.ATTACK_DAMAGE, 10)
                .add(EntityAttributes.FOLLOW_RANGE, 20);
    }

    @Override
    protected int getElementalCreeperType() {
        return CreeperMath.CREEPER_TYPE.WIND.getType();
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new CreeperElementalIgniteGoal(this, 4.0F)); // closer
        this.goalSelector.add(3, new FleeEntityGoal<>(this, OcelotEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(3, new FleeEntityGoal<>(this, CatEntity.class, 6.0F, 1.0, 1.2));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    protected void explode() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            this.dead = true;

            final Difficulty difficulty = this.getWorld().getDifficulty();

            final float chargedPower = this.isCharged() ? 3.27F * 2.0f : 3.27F;
            final Vec3d entityPosition = this.getPos();

            int ghostCreeperChance;

            switch (difficulty) {
                case PEACEFUL:
                case EASY: {
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_EASY);
                }
                case NORMAL: {
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_NORMAL);
                } break;
                case HARD:
                default: {
                    ghostCreeperChance = (int)(255 * GHOST_CREEPER_EXPLODE_CHANCE_HARD);
                } break;
            }

            final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(
                    false, false, Optional.of(chargedPower),
                    Registries.BLOCK.getOptional(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS)
                            .map(Function.identity())
            );

            // Fabric NETWORKING
            // search - https://duckduckgo.com/?q=minecraft+fabric+existing+network+packet&t=vivaldi&ia=web.
            // about - https://wiki.fabricmc.net/tutorial:networking
            // state example - https://wiki.fabricmc.net/tutorial:persistent_states

            //{ // Mimicking explosion (ideally we would want to be always pushed up at full value)
            //
            //    ExplosionImpl explosionImpl = new ExplosionImpl(
            //            serverWorld, this, null, EXPLOSION_BEHAVIOR,
            //            entityPosition, 1.2f, false, Explosion.DestructionType.KEEP
            //    );
            //
            //    explosionImpl.explode();
            //
            //    MinecraftServer server = serverWorld.getServer();
            //
            //    for (ServerPlayerEntity serverPlayerEntity : serverWorld.getPlayers()) {
            //        if (serverPlayerEntity.squaredDistanceTo(entityPosition) < 4096.0) {
            //
            //            // Change Player Position.
            //            Optional<Vec3d> optional = Optional.ofNullable(
            //                    explosionImpl.getKnockbackByPlayer().get(serverPlayerEntity)
            //            );
            //
            //            // Create a Packet with Changed Position information.
            //            ExplosionS2CPacket packet = new ExplosionS2CPacket(
            //                    entityPosition, optional, null, null
            //            );
            //
            //            //ServerPlayNetworking.send(
            //            //        serverPlayerEntity,
            //            //
            //            //        //CustomPayloadS2CPacket
            //            //        //ServerNetworkingImpl.createS2CPacket(packet)
            //            //        // new ExplosionS2CPacket(entityPosition, optional, null, null)
            //            //        //PacketByteBufs.empty()
            //            //);
            //
            //            // PROB. Only possible from client ?!
            //            //serverWorld.sendPacket(new ExplosionS2CPacket(entityPosition, optional, null, null));
            //
            //            // PROB. Breaks (prob runs on a separate thread)
            //            //serverPlayerEntity.networkHandler.sendPacket(
            //            //        new ExplosionS2CPacket(entityPosition, optional, null, null)
            //            //);
            //
            //            // Does not help
            //            //server.execute(() -> {
            //            //});
            //        }
            //    }
            //}

            // Prob. It works perfectly if we stand inside it.
            serverWorld.createExplosion(
                    this,
                    null,
                    EXPLOSION_BEHAVIOR,
                    entityPosition,
                    ExplosionDiameter, // HACK. We can cheat by setting this value higher than trigger distance.
                    false,
                    World.ExplosionSourceType.MOB
                    //ParticleTypes.GUST_EMITTER_SMALL,
                    //ParticleTypes.GUST_EMITTER_LARGE,
                    //SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST
            );

            this.spawnEffectsCloud();
            this.onRemoval(serverWorld, RemovalReason.KILLED);
            this.discard();

            SpawnGhostCreeper(serverWorld, ghostCreeperChance);
        }
    }

}
