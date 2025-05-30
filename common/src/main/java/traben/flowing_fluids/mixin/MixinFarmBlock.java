package traben.flowing_fluids.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import traben.flowing_fluids.FFFluidUtils;
import traben.flowing_fluids.FlowingFluids;

@Mixin(FarmBlock.class)
public class MixinFarmBlock {

    @Inject(
            method = "isNearWater",
            at = @At(value = "RETURN", ordinal = 0)
    )
    private static void ff$drainWater(final LevelReader level, final BlockPos pos, final CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 1) final BlockPos blockPos) {
        if (FlowingFluids.config.enableMod
                && FlowingFluids.config.farmlandDrainWaterChance > 0
                && FlowingFluids.config.isWaterAllowed()
                && level instanceof ServerLevel serverLevel // always true
                && !FlowingFluids.config.dontTickAtLocation(blockPos, serverLevel)) {

            if (serverLevel.random.nextFloat() <= FlowingFluids.config.farmlandDrainWaterChance) {
                FFFluidUtils.removeAmountFromFluidAtPosWithRemainder(serverLevel, blockPos, Fluids.WATER,1);
            }
        }
    }
}
