package no.stelar7.api.r4j.pojo.lor.staticdata;

import com.google.gson.reflect.TypeToken;
import no.stelar7.api.r4j.basic.utils.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class LoRSetInstance
{
    private String set;
    private String language;
    private Path   folderLocation;
    
    public LoRSetInstance(Path folderLocation, String set, String language)
    {
        this.set = set;
        this.language = language;
        this.folderLocation = folderLocation;
    }
    
    public String getSet()
    {
        return set;
    }
    
    public String getLanguage()
    {
        return language;
    }
    
    public Path getFolderLocation()
    {
        return folderLocation;
    }
    
    public Path getSourceFileLocation()
    {
        String basePath = getLanguage() + File.separator + "data" + File.separator;
        String filename = basePath + getSet() + "-" + getLanguage() + ".json";
        return getFolderLocation().resolve(filename);
    }
    
    public boolean isValid()
    {
        return Files.exists(getSourceFileLocation());
    }
    
    
    public List<StaticLoRCard> loadData()
    {
        try
        {
            byte[]              content     = Files.readAllBytes(getSourceFileLocation());
            String              contentJSON = new String(content, StandardCharsets.UTF_8);
            List<StaticLoRCard> cards       = Utils.getGson().fromJson(contentJSON, new TypeToken<List<StaticLoRCard>>() {}.getType());
            return cards;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        LoRSetInstance that = (LoRSetInstance) o;
        return Objects.equals(set, that.set) &&
               Objects.equals(language, that.language) &&
               Objects.equals(folderLocation, that.folderLocation);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(set, language, folderLocation);
    }
    
    @Override
    public String toString()
    {
        return "LoRSetInstance{" +
               "set='" + set + '\'' +
               ", language='" + language + '\'' +
               ", folderLocation=" + folderLocation +
               '}';
    }
}
