package cftp.mixin.client;

import cftp.mixin.BlockStateMixin;
import cftp.utility.IColor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = FluidRenderer.class, priority = 999)
public abstract class FluidRendererMixin {

    @Shadow @Final private Sprite[] lavaSprites;
    @Shadow @Final private Sprite[] waterSprites;
    @Shadow private Sprite waterOverlaySprite;

    //@Shadow
    //private static boolean isSameFluid(FluidState a, FluidState b);

    @Shadow
    private static boolean isSameFluid(FluidState a, FluidState b) {
        throw new AbstractMethodError(); // standard dummy body for private static shadow
    }

    @Shadow
    private static boolean method_3344(Direction direction, float f, BlockState blockState) {
        throw new AbstractMethodError(); // standard dummy body for private static shadow
    }

    //@Shadow
    //private static boolean isOppositeSideCovered(BlockState blockState, Direction direction) {
    //    return false;
    //}

    //@Shadow
    //public static boolean shouldRenderSide(FluidState fluidState, BlockState blockState, Direction direction, FluidState fluidState2)

    @Shadow protected abstract float getFluidHeight(BlockRenderView world, Fluid fluid, BlockPos pos, BlockState blockState, FluidState fluidState);
    @Shadow protected abstract float calculateFluidHeight(BlockRenderView world, Fluid fluid, float originHeight, float northSouthHeight, float eastWestHeight, BlockPos pos);
    @Shadow protected abstract int getLight(BlockRenderView world, BlockPos pos);
    @Shadow protected abstract void vertex(VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, float k, float l, float m, int n);

    /**
     * @author dotBlueShoes
     * @reason no reason at all
     */
    @Overwrite
    public void render(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState) {
        boolean bl = fluidState.isIn(FluidTags.LAVA);
        Sprite[] sprites = bl ? this.lavaSprites : this.waterSprites;
        int i = bl ? 16777215 : BiomeColors.getWaterColor(world, pos);
        float f = (float)(i >> 16 & 255) / 255.0F;
        float g = (float)(i >> 8 & 255) / 255.0F;
        float h = (float)(i & 255) / 255.0F;

        // Weird. Injecting would be so much better...
        // Anyway I can add calls to methods and such it seems.
        // but I cannot remove the old code.
        // So I should be able to make a flag and a color variable inside
        // a) fluidState, b) blockState, c) BlockPos related
        // and reference it here. by default that value would be 1.0 for each
        // i guess when a color is already being modified by something like here biomeColors
        //  I should not mix them. So I would have full control of the color. just do extra colors
        // if a flag says to.

        //  WRONG
        // Instead. For every chunk array I need a color array.
        // So not only we READ/SAVE every block in a chunk but now also every color of a block in a chunk.
        //  So...
        // a) Would need to read/write to a file.
        // b) Would need an array of chunk size that of one int size (maybe less actually).
        // c) Having that I would read and apply that value here then.

        IColor color = (IColor) blockState;
        if (color.getIsPainted()) {
            f = 1.0f;
            g = 0.0f;
            h = 0.0f;
        }
        //CFTP.LOGGER.info("H!");

        BlockState blockState2 = world.getBlockState(pos.offset(Direction.DOWN));
        FluidState fluidState2 = blockState2.getFluidState();
        BlockState blockState3 = world.getBlockState(pos.offset(Direction.UP));
        FluidState fluidState3 = blockState3.getFluidState();
        BlockState blockState4 = world.getBlockState(pos.offset(Direction.NORTH));
        FluidState fluidState4 = blockState4.getFluidState();
        BlockState blockState5 = world.getBlockState(pos.offset(Direction.SOUTH));
        FluidState fluidState5 = blockState5.getFluidState();
        BlockState blockState6 = world.getBlockState(pos.offset(Direction.WEST));
        FluidState fluidState6 = blockState6.getFluidState();
        BlockState blockState7 = world.getBlockState(pos.offset(Direction.EAST));
        FluidState fluidState7 = blockState7.getFluidState();
        boolean bl2 = !isSameFluid(fluidState, fluidState3);
        boolean bl3 = FluidRenderer.shouldRenderSide(fluidState, blockState, Direction.DOWN, fluidState2) && !method_3344(Direction.DOWN, 0.8888889F, blockState2);
        boolean bl4 = FluidRenderer.shouldRenderSide(fluidState, blockState, Direction.NORTH, fluidState4);
        boolean bl5 = FluidRenderer.shouldRenderSide(fluidState, blockState, Direction.SOUTH, fluidState5);
        boolean bl6 = FluidRenderer.shouldRenderSide(fluidState, blockState, Direction.WEST, fluidState6);
        boolean bl7 = FluidRenderer.shouldRenderSide(fluidState, blockState, Direction.EAST, fluidState7);
        if (bl2 || bl3 || bl7 || bl6 || bl4 || bl5) {
            float j = world.getBrightness(Direction.DOWN, true);
            float k = world.getBrightness(Direction.UP, true);
            float l = world.getBrightness(Direction.NORTH, true);
            float m = world.getBrightness(Direction.WEST, true);
            Fluid fluid = fluidState.getFluid();
            float n = this.getFluidHeight(world, fluid, pos, blockState, fluidState);
            float o;
            float p;
            float q;
            float r;
            if (n >= 1.0F) {
                o = 1.0F;
                p = 1.0F;
                q = 1.0F;
                r = 1.0F;
            } else {
                float s = this.getFluidHeight(world, fluid, pos.north(), blockState4, fluidState4);
                float t = this.getFluidHeight(world, fluid, pos.south(), blockState5, fluidState5);
                float u = this.getFluidHeight(world, fluid, pos.east(), blockState7, fluidState7);
                float v = this.getFluidHeight(world, fluid, pos.west(), blockState6, fluidState6);
                o = this.calculateFluidHeight(world, fluid, n, s, u, pos.offset(Direction.NORTH).offset(Direction.EAST));
                p = this.calculateFluidHeight(world, fluid, n, s, v, pos.offset(Direction.NORTH).offset(Direction.WEST));
                q = this.calculateFluidHeight(world, fluid, n, t, u, pos.offset(Direction.SOUTH).offset(Direction.EAST));
                r = this.calculateFluidHeight(world, fluid, n, t, v, pos.offset(Direction.SOUTH).offset(Direction.WEST));
            }

            float s = (float)(pos.getX() & 15);
            float t = (float)(pos.getY() & 15);
            float u = (float)(pos.getZ() & 15);
            float v = 0.001F;
            float w = bl3 ? 0.001F : 0.0F;
            if (bl2 && !method_3344(Direction.UP, Math.min(Math.min(p, r), Math.min(q, o)), blockState3)) {
                p -= 0.001F;
                r -= 0.001F;
                q -= 0.001F;
                o -= 0.001F;
                Vec3d vec3d = fluidState.getVelocity(world, pos);
                float x;
                float z;
                float ab;
                float ad;
                float y;
                float aa;
                float ac;
                float ae;
                if (vec3d.x == (double)0.0F && vec3d.z == (double)0.0F) {
                    Sprite sprite = sprites[0];
                    x = sprite.getFrameU(0.0F);
                    y = sprite.getFrameV(0.0F);
                    z = x;
                    aa = sprite.getFrameV(1.0F);
                    ab = sprite.getFrameU(1.0F);
                    ac = aa;
                    ad = ab;
                    ae = y;
                } else {
                    Sprite sprite = sprites[1];
                    float af = (float) MathHelper.atan2(vec3d.z, vec3d.x) - ((float)Math.PI / 2F);
                    float ag = MathHelper.sin(af) * 0.25F;
                    float ah = MathHelper.cos(af) * 0.25F;
                    float ai = 0.5F;
                    x = sprite.getFrameU(0.5F + (-ah - ag));
                    y = sprite.getFrameV(0.5F + -ah + ag);
                    z = sprite.getFrameU(0.5F + -ah + ag);
                    aa = sprite.getFrameV(0.5F + ah + ag);
                    ab = sprite.getFrameU(0.5F + ah + ag);
                    ac = sprite.getFrameV(0.5F + (ah - ag));
                    ad = sprite.getFrameU(0.5F + (ah - ag));
                    ae = sprite.getFrameV(0.5F + (-ah - ag));
                }

                float aj = (x + z + ab + ad) / 4.0F;
                float af = (y + aa + ac + ae) / 4.0F;
                float ag = sprites[0].getAnimationFrameDelta();
                x = MathHelper.lerp(ag, x, aj);
                z = MathHelper.lerp(ag, z, aj);
                ab = MathHelper.lerp(ag, ab, aj);
                ad = MathHelper.lerp(ag, ad, aj);
                y = MathHelper.lerp(ag, y, af);
                aa = MathHelper.lerp(ag, aa, af);
                ac = MathHelper.lerp(ag, ac, af);
                ae = MathHelper.lerp(ag, ae, af);
                int ak = this.getLight(world, pos);
                float ai = k * f;
                float al = k * g;
                float am = k * h;
                this.vertex(vertexConsumer, s + 0.0F, t + p, u + 0.0F, ai, al, am, x, y, ak);
                this.vertex(vertexConsumer, s + 0.0F, t + r, u + 1.0F, ai, al, am, z, aa, ak);
                this.vertex(vertexConsumer, s + 1.0F, t + q, u + 1.0F, ai, al, am, ab, ac, ak);
                this.vertex(vertexConsumer, s + 1.0F, t + o, u + 0.0F, ai, al, am, ad, ae, ak);
                if (fluidState.canFlowTo(world, pos.up())) {
                    this.vertex(vertexConsumer, s + 0.0F, t + p, u + 0.0F, ai, al, am, x, y, ak);
                    this.vertex(vertexConsumer, s + 1.0F, t + o, u + 0.0F, ai, al, am, ad, ae, ak);
                    this.vertex(vertexConsumer, s + 1.0F, t + q, u + 1.0F, ai, al, am, ab, ac, ak);
                    this.vertex(vertexConsumer, s + 0.0F, t + r, u + 1.0F, ai, al, am, z, aa, ak);
                }
            }

            if (bl3) {
                float x = sprites[0].getMinU();
                float z = sprites[0].getMaxU();
                float ab = sprites[0].getMinV();
                float ad = sprites[0].getMaxV();
                int an = this.getLight(world, pos.down());
                float aa = j * f;
                float ac = j * g;
                float ae = j * h;
                this.vertex(vertexConsumer, s, t + w, u + 1.0F, aa, ac, ae, x, ad, an);
                this.vertex(vertexConsumer, s, t + w, u, aa, ac, ae, x, ab, an);
                this.vertex(vertexConsumer, s + 1.0F, t + w, u, aa, ac, ae, z, ab, an);
                this.vertex(vertexConsumer, s + 1.0F, t + w, u + 1.0F, aa, ac, ae, z, ad, an);
            }

            int ao = this.getLight(world, pos);

            for(Direction direction : Direction.Type.HORIZONTAL) {
                float ad;
                float y;
                float aa;
                float ac;
                float ae;
                float ap;
                boolean bl8;
                switch (direction) {
                    case NORTH:
                        ad = p;
                        y = o;
                        aa = s;
                        ae = s + 1.0F;
                        ac = u + 0.001F;
                        ap = u + 0.001F;
                        bl8 = bl4;
                        break;
                    case SOUTH:
                        ad = q;
                        y = r;
                        aa = s + 1.0F;
                        ae = s;
                        ac = u + 1.0F - 0.001F;
                        ap = u + 1.0F - 0.001F;
                        bl8 = bl5;
                        break;
                    case WEST:
                        ad = r;
                        y = p;
                        aa = s + 0.001F;
                        ae = s + 0.001F;
                        ac = u + 1.0F;
                        ap = u;
                        bl8 = bl6;
                        break;
                    default:
                        ad = o;
                        y = q;
                        aa = s + 1.0F - 0.001F;
                        ae = s + 1.0F - 0.001F;
                        ac = u;
                        ap = u + 1.0F;
                        bl8 = bl7;
                }

                if (bl8 && !method_3344(direction, Math.max(ad, y), world.getBlockState(pos.offset(direction)))) {
                    BlockPos blockPos = pos.offset(direction);
                    Sprite sprite2 = sprites[1];
                    if (!bl) {
                        Block block = world.getBlockState(blockPos).getBlock();
                        if (block instanceof TranslucentBlock || block instanceof LeavesBlock) {
                            sprite2 = this.waterOverlaySprite;
                        }
                    }

                    float ah = sprite2.getFrameU(0.0F);
                    float ai = sprite2.getFrameU(0.5F);
                    float al = sprite2.getFrameV((1.0F - ad) * 0.5F);
                    float am = sprite2.getFrameV((1.0F - y) * 0.5F);
                    float aq = sprite2.getFrameV(0.5F);
                    float ar = direction.getAxis() == Direction.Axis.Z ? l : m;
                    float as = k * ar * f;
                    float at = k * ar * g;
                    float au = k * ar * h;
                    this.vertex(vertexConsumer, aa, t + ad, ac, as, at, au, ah, al, ao);
                    this.vertex(vertexConsumer, ae, t + y, ap, as, at, au, ai, am, ao);
                    this.vertex(vertexConsumer, ae, t + w, ap, as, at, au, ai, aq, ao);
                    this.vertex(vertexConsumer, aa, t + w, ac, as, at, au, ah, aq, ao);
                    if (sprite2 != this.waterOverlaySprite) {
                        this.vertex(vertexConsumer, aa, t + w, ac, as, at, au, ah, aq, ao);
                        this.vertex(vertexConsumer, ae, t + w, ap, as, at, au, ai, aq, ao);
                        this.vertex(vertexConsumer, ae, t + y, ap, as, at, au, ai, am, ao);
                        this.vertex(vertexConsumer, aa, t + ad, ac, as, at, au, ah, al, ao);
                    }
                }
            }

        }
    }
}
