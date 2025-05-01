package com.macesoundswapper;

import net.runelite.client.config.*;

@ConfigGroup("macesoundswapper")
public interface MaceSoundSwapperConfig extends Config
{
    @ConfigItem(
            keyName = "enableSwap",
            name = "Enable Swap",
            description = "Enables the swapping of mace sound to selected sound",
            position = 1
    )
    default boolean enableSwap()
    {
        return true;
    }

    @ConfigItem(
            keyName = "replacementSound",
            name = "Replacement Sound",
            description = "Sound to replace mace sound with",
            position = 2
    )
    default WeaponSounds swapSelection()
    {
        return WeaponSounds.DEFAULT;
    }

    @ConfigItem(
            keyName = "volumeEnable",
            name = "Use Custom Volume",
            description = "Uses Value in Custom Volume Field to Play New Sound Effects",
            position = 3
    )
    default boolean volumeEnable()
    {
        return false;
    }
    @ConfigItem(
            keyName = "volume",
            name = "Custom Volume",
            description = "Volume replacement sounds will be played at",
            position = 4
    )
    default int volume()
    {
        return 100;
    }
}
