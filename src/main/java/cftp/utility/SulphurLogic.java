package cftp.utility;

import cftp.entity.SulphurEntity;
import cftp.registries.CFTPBlocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class SulphurLogic {

    public static void primeSulphur(ServerWorld world, BlockPos pos) {

        SulphurEntity sulphurEntity = new SulphurEntity(
                world,
                (double)pos.getX() + (double)0.5F,
                pos.getY(),
                (double)pos.getZ() + (double)0.5F,
                null,
                CFTPBlocks.SULPHUR_CLOUD.getDefaultState()
                //this.getDefaultState()
        );

        //tntEntity.surphurBlockState = this.getDefaultState();
        //sulphurEntity.surphurBlockState = CFTPBlocks.NETHER_SULPHUR_ORE.getDefaultState();

        world.spawnEntity(sulphurEntity);

        //world.playSound(null,
        //        tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(),
        //        SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS,
        //        1.0F, 1.0F
        //);

        //world.emitGameEvent(null, GameEvent.PRIME_FUSE, pos);
    }

}
