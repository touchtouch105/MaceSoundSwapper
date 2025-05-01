package com.macesoundswapper;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Preferences;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "Mace Sound Swapper",
        description = "Swaps weak sound of maces with chunkier sounds"
)
public class MaceSoundSwapperPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private EventBus eventBus;

    @Inject
    private MaceSoundSwapperConfig config;

    private int replacementSoundID = -1;

    private static final String CONFIG_GROUP = "macesoundswapper";

    @Provides
    MaceSoundSwapperConfig provideConfig(ConfigManager configManager )
    {
        return configManager.getConfig( MaceSoundSwapperConfig.class );
    }

    @Override
    protected void startUp() throws Exception
    {
        replacementSoundID = config.swapSelection().getSoundID();
    }

    @Override
    protected void shutDown() throws Exception
    {
        replacementSoundID = -1;
    }

    @Subscribe
    public void onConfigChanged( ConfigChanged event )
    {
        if (!CONFIG_GROUP.equals( event.getGroup() ) )
        {
            return;
        }

        replacementSoundID = config.swapSelection().getSoundID();
    }

    @Subscribe
    public void onSoundEffectPlayed( SoundEffectPlayed event )
    {
        if ( !config.enableSwap() )
        {
            return;
        }

        int soundId = event.getSoundId();
        Preferences preferences = client.getPreferences();
        int originalVolume = preferences.getSoundEffectVolume();
        int volume = originalVolume;

        if ( config.volumeEnable() )
        {
             volume = config.volume();
        }

        if ( soundId == WeaponSounds.DEFAULT.getSoundID() )
        {
                event.consume();
                soundId = -1;
                preferences.setSoundEffectVolume( volume );
                client.playSoundEffect( replacementSoundID, volume );
                preferences.setSoundEffectVolume( originalVolume );

        }

    }
}
