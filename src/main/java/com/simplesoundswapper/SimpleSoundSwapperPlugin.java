package com.simplesoundswapper;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Preferences;
import net.runelite.api.events.AreaSoundEffectPlayed;
import net.runelite.api.events.SoundEffectPlayed;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@PluginDescriptor(
        name = "Simple Sound Swapper",
        description = "Allows swapping in game sounds with new ones based off sound id"
)
public class SimpleSoundSwapperPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Inject
    private EventBus eventBus;

    @Inject
    private SimpleSoundSwapperConfig config;

    public List<Integer> simpleIdsToSwap = new ArrayList<>();
    public List<Integer> simpleIdReplacements = new ArrayList<>();

    private static final String CONFIG_GROUP = "simplesoundswapper";

    @Provides
    SimpleSoundSwapperConfig provideConfig( ConfigManager configManager )
    {
        return configManager.getConfig( SimpleSoundSwapperConfig.class );
    }

    @Override
    protected void startUp() throws Exception
    {
        simpleIdsToSwap = getIds( config.simpleIdsToReplace() );
        simpleIdReplacements = getIds( config.simpleIdsReplacements() );
    }

    @Override
    protected void shutDown() throws Exception
    {
        simpleIdsToSwap = new ArrayList<>();
        simpleIdReplacements = new ArrayList<>();
    }

    @Subscribe
    public void onConfigChanged( ConfigChanged event )
    {
        if (!CONFIG_GROUP.equals( event.getGroup() ) )
        {
            return;
        }

        switch ( event.getKey() )
        {
            case "simpleIdsToReplace": {
                simpleIdsToSwap = getIds( event.getNewValue() );
                break;
            }

            case "simpleIdsReplacements": {
                simpleIdReplacements = getIds( event.getNewValue() );
                break;
            }
        }
    }

    @Subscribe
    public void onSoundEffectPlayed( SoundEffectPlayed event )
    {
        if ( !config.simpleIdSwap() || simpleIdsToSwap.isEmpty() || simpleIdReplacements.isEmpty() )
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

        if ( simpleIdsToSwap.contains( soundId ) )
        {
            int idx = simpleIdsToSwap.indexOf( soundId );

            if ( idx < simpleIdReplacements.size() )
            {
                event.consume();
                preferences.setSoundEffectVolume( volume );
                client.playSoundEffect( simpleIdReplacements.get( idx ), volume );
                preferences.setSoundEffectVolume( originalVolume );
            }
        }

    }

    @Subscribe
    public void onAreaSoundEffectPlayed(AreaSoundEffectPlayed event)
    {
        if ( !config.simpleIdSwap() || simpleIdsToSwap.isEmpty() || simpleIdReplacements.isEmpty() )
        {
            return;
        }

        int soundId = event.getSoundId();
        Preferences preferences = client.getPreferences();
        int originalVolume = preferences.getAreaSoundEffectVolume();
        int volume = originalVolume;

        if ( config.volumeEnable() )
        {
            volume = config.volume();
        }

        if (simpleIdsToSwap.contains( soundId ) )
        {
            int idx = simpleIdsToSwap.indexOf( soundId );

            if ( idx < simpleIdReplacements.size() )
            {
                event.consume();
                preferences.setAreaSoundEffectVolume( volume );
                client.playSoundEffect( simpleIdReplacements.get( idx ), volume );
                preferences.setAreaSoundEffectVolume( originalVolume );
            }
        }
    }

    private List<Integer> getIds(String configText)
    {
        if ( configText == null || configText.isEmpty() )
        {
            return List.of();
        }

        List<Integer> ids = new ArrayList<>();
        for ( String s : Text.fromCSV( configText ) )
        {
            try
            {
                int id = Integer.parseInt( s );
                ids.add( id );
            }
            catch ( NumberFormatException e )
            {
                log.warn( "Invalid id when parsing {}: {}", configText, s );
            }
        }

        return ids;
    }
}
