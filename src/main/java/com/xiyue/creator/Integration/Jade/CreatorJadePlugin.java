package com.xiyue.creator.Integration.Jade;

import com.xiyue.creator.BuilderSystem.BuilderSystem;
import com.xiyue.creator.Integration.Jade.Provider.BuilderComponentProvider;
import com.xiyue.creator.Integration.Jade.Provider.DryingRackComponentProvider;
import com.xiyue.creator.Integration.Jade.Provider.StrainerFrameComponentProvider;
import com.xiyue.creator.api.BlockEntities.DryingRackEntity;
import com.xiyue.creator.ModBlockEntities.MyModBlockEntities.StrainerFrameEntity.StrainerFrameEntity;
import com.xiyue.creator.api.Blocks.DryingRackBlock;
import com.xiyue.creator.ModBlocks.FunctionBlocks.StrainerFrame.StrainerFrameBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class CreatorJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        // 注册服务端数据提供器
        registration.registerBlockDataProvider(StrainerFrameComponentProvider.INSTANCE, StrainerFrameEntity.class);
        registration.registerBlockDataProvider(BuilderComponentProvider.INSTANCE, BuilderSystem.BuilderBlockEntity.class);
        registration.registerBlockDataProvider(DryingRackComponentProvider.INSTANCE, DryingRackEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        // 注册客户端组件提供器
        registration.registerBlockComponent(StrainerFrameComponentProvider.INSTANCE, StrainerFrameBlock.class);
        registration.registerBlockComponent(BuilderComponentProvider.INSTANCE, BuilderSystem.BuilderBlock.class);
        registration.registerBlockComponent(DryingRackComponentProvider.INSTANCE, DryingRackBlock.class);
    }
}