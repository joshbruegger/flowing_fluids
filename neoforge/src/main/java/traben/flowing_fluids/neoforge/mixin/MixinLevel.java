package traben.flowing_fluids.neoforge.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import traben.flowing_fluids.FFFluidUtils;

@Mixin(Level.class)
public abstract class MixinLevel {


    @Inject(method = "setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;II)Z",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"
                    #if MC < MC_21_2 ,ordinal = 1 #endif
            ),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void flowing_fluids$displaceFluids(
            #if MC < MC_21_2
            final BlockPos pos, final BlockState state, final int flags, final int recursionLeft,
                                               final CallbackInfoReturnable<Boolean> cir, final LevelChunk levelchunk,
                                               final Block block, final BlockSnapshot blockSnapshot,
                                               final BlockState old, final int oldLight,
                                               final int oldOpacity, final BlockState blockstate
                                               #else
            final BlockPos pos, final BlockState state, final int flags, final int recursionLeft,
            final CallbackInfoReturnable<Boolean> cir, final LevelChunk levelchunk, final Block block,
            final BlockSnapshot blockSnapshot, final BlockState old
    #endif) {
        FFFluidUtils.displaceFluids((Level) (Object) this, pos, state, flags, levelchunk, old);
    }

}
