package com.simplesoundswapper;

import net.runelite.client.config.*;

@ConfigGroup("simplesoundswapper")
public interface SimpleSoundSwapperConfig extends Config
{
    @ConfigItem(
            keyName = "simpleIdSwap",
            name = "Swap Sound IDs",
            description = "Enable Swapping of Sound IDs",
            position = 1
    )
    default boolean simpleIdSwap()
    {
        return true;
    }
    @ConfigItem(
            keyName = "simpleIdsToReplace",
            name = "IDs to Replace",
            description = "Sound IDs to replace with replacement IDs. Comma separated vals. Ex: 2508,1321,1316",
            position = 2
    )
    default String simpleIdsToReplace()
    {
        return "";
    }
    @ConfigItem(
            keyName = "simpleIdsReplacements",
            name = "Replacement IDs",
            description = "Sound IDs to replace the items in 'IDs to replace' field",
            position = 3
    )
    default String simpleIdsReplacements()
    {
        return "";
    }
    @ConfigItem(
            keyName = "volumeEnable",
            name = "Use Custom Volume",
            description = "Uses Value in Custom Volume Field to Play New Sound Effects",
            position = 4
    )
    default boolean volumeEnable()
    {
        return false;
    }
    @ConfigItem(
            keyName = "volume",
            name = "Custom Volume",
            description = "Volume replacement sounds will be played at",
            position = 5
    )
    default int volume()
    {
        return 100;
    }
}
