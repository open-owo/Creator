package com.xiyue.creator.api.BlockEntities.Machines;

import com.xiyue.creator.api.capability.energy.EnergyConfig;
import com.xiyue.creator.api.capability.fluid.FluidConfig;
import com.xiyue.creator.api.capability.item.ItemConfig;

public class MachineSpec {
    private final ItemConfig itemConfig;
    private final FluidConfig fluidConfig;
    private final EnergyConfig energyConfig;

    public ItemConfig getItemConfig() {
        return itemConfig;
    }

    public FluidConfig getFluidConfig() {
        return fluidConfig;
    }

    public EnergyConfig getEnergyConfig() {
        return energyConfig;
    }

    private MachineSpec(Builder builder) {
        itemConfig = builder.automationConfig;
        fluidConfig = builder.fluidConfig;
        energyConfig = builder.energyConfig;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ItemConfig automationConfig;
        private FluidConfig fluidConfig;
        private EnergyConfig energyConfig;

        public Builder item(ItemConfig config) {
            this.automationConfig = config;
            return this;
        }
        public Builder fluid(FluidConfig config) {
            this.fluidConfig = config;
            return this;
        }
        public Builder energy(EnergyConfig config) {
            this.energyConfig = config;
            return this;
        }

        public MachineSpec build() {
            return new MachineSpec(this);
        }
    }
}
