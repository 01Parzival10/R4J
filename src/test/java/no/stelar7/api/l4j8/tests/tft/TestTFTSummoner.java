package no.stelar7.api.l4j8.tests.tft;

import no.stelar7.api.l4j8.basic.cache.impl.*;
import no.stelar7.api.l4j8.basic.calling.DataCall;
import no.stelar7.api.l4j8.basic.constants.api.Platform;
import no.stelar7.api.l4j8.impl.L4J8;
import no.stelar7.api.l4j8.impl.lol.builders.spectator.SpectatorBuilder;
import no.stelar7.api.l4j8.impl.tft.TFTSummonerAPI;
import no.stelar7.api.l4j8.pojo.summoner.Summoner;
import no.stelar7.api.l4j8.tests.SecretFile;
import org.junit.*;

import java.util.function.Consumer;

public class TestTFTSummoner
{
    private final Consumer<Summoner> doAssertions = (final Summoner value) ->
    {
        Assert.assertNotNull("Summoner name is NULL", value.getName());
        Assert.assertNotEquals("Summoner profile icon is NULL", value.getProfileIconId(), 0);
        Assert.assertNotEquals("Summoner revision date is NULL", value.getRevisionDate(), 0L);
        Assert.assertNotEquals("Summoner level is NULL", value.getSummonerLevel(), 0);
        Assert.assertNotNull("Summoner revison date DATE is NULL", value.getRevisionDateAsDate());
        Assert.assertNotNull("Summoner PUUID is null", value.getPUUID());
    };
    
    final L4J8 l4j8 = new L4J8(SecretFile.CREDS);
    TFTSummonerAPI api = l4j8.getTFTAPI().getSummonerAPI();
    
    @Before
    public void before()
    {
        DataCall.setCacheProvider(new TieredCacheProvider(new MemoryCacheProvider(), new FileSystemCacheProvider()));
    }
    
    @Test
    public void testById()
    {
        // assume that byName works, since we cant get any summoner ids otherwise..
        String   id = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        Summoner s  = api.getSummonerByName(Platform.EUW1, "stelar7");
        Summoner s2 = api.getSummonerById(Platform.EUW1, s.getSummonerId());
        doAssertions.accept(s2);
    }
    
    @Test
    public void testByName()
    {
        String   id = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        Summoner s  = api.getSummonerByName(Platform.EUW1, "stelar7");
        doAssertions.accept(s);
    }
    
    @Test
    public void testByAccount()
    {
        String   id = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        Summoner s  = api.getSummonerByName(Platform.EUW1, "stelar7");
        
        Summoner optional = api.getSummonerByAccount(Platform.EUW1, s.getAccountId());
        doAssertions.accept(optional);
    }
    
    @Test
    public void testByPUUID()
    {
        String   id = new SpectatorBuilder().withPlatform(Platform.EUW1).getFeaturedGames().get(0).getParticipants().get(0).getSummonerName();
        Summoner s  = api.getSummonerByName(Platform.EUW1, "stelar7");
        
        Summoner optional = api.getSummonerByPUUID(Platform.EUW1, s.getPUUID());
        doAssertions.accept(optional);
    }
}
