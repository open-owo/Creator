package com.xiyue.creator.api.capability.HandlerConfig;

import com.xiyue.creator.api.BlockEntities.Machines.MachineSpec;

import static com.xiyue.creator.api.capability.HandlerConfig.ItemConfigs.*;

public class MachineSpecs {
    public static final MachineSpec STRAINER_FRAME_SPEC = MachineSpec.builder()
            .item(STRAINER_FRAME_CONFIG)
            .build();
    public static final MachineSpec IRON_STRAINER_FRAME_SPEC = MachineSpec.builder()
            .item(IRON_STRAINER_FRAME_CONFIG)
            .build();

    public static final MachineSpec DRYING_RACK_SPEC = MachineSpec.builder()
            .item(DRYING_RACK_CONFIG)
            .build();
}
