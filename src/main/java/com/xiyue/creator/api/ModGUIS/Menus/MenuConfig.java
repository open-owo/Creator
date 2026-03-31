package com.xiyue.creator.api.ModGUIS.Menus;

import com.xiyue.creator.api.capability.SlotType;

import java.util.HashMap;
import java.util.Map;

public class MenuConfig {
    private final Map<SlotType, Builder.config> SlotConfigs;
    private final int InvStarX;
    private final int InvStarY;

    private MenuConfig(Map<SlotType, Builder.config> slotConfigs,int InvStarX,int InvStarY) {
        SlotConfigs = Map.copyOf(slotConfigs);
        this.InvStarX = InvStarX;
        this.InvStarY = InvStarY;

    }

    public Map<SlotType, Builder.config> getSlotConfigs() {
        return SlotConfigs;
    }

    public int getInvStarX() {
        return InvStarX;
    }

    public int getInvStarY() {
        return InvStarY;
    }

    public static Builder Builder(int InvStarX, int InvStarY){
        return new Builder(InvStarX, InvStarY);
    }

    public static class Builder{
        private final Map<SlotType, Builder.config> SlotConfigs = new HashMap<>();
        int InvStarX;
        int InvStarY;


        private Builder(int InvStarX, int InvStarY){
            this.InvStarX = InvStarX;
            this.InvStarY = InvStarY;
        }

        public Builder set(SlotType type, int SlotStartX, int SlotStartY, int row, int column){
            SlotConfigs.put(type, new config(SlotStartX, SlotStartY, row, column));
            return this;
        }

        public MenuConfig build(){
            return new MenuConfig(SlotConfigs, InvStarX, InvStarY);
        }

        public static class config{
           private final int SlotStartX;
           private final int  SlotStartY;
           private final int row;
           private final int column;

           public int getSlotStartX() {
               return SlotStartX;
           }

           public int getRow() {
               return row;
           }

           public int getColumn() {
               return column;
           }
           public int getSlotStartY() {
               return SlotStartY;
           }

           private config(int startX, int startY ,int row, int column){
                this.SlotStartX = startX;
                this.SlotStartY = startY;
                this.row = row;
                this.column = column;
           }
        }
    }
}
