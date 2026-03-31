package com.xiyue.creator.api.capability;

import com.xiyue.creator.api.capability.item.ItemConfig;

public interface IHandlerProvider {
    ItemConfig.ContextRules getItemHandlerForContext(HandlerContext context);
}
