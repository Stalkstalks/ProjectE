package moze_intel.projecte.emc.pregenerated;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import moze_intel.projecte.emc.NormalizedSimpleStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class PregeneratedEMC
{
	private static final Gson gson =  new GsonBuilder().registerTypeAdapter(NormalizedSimpleStack.class, new NSSJsonTypeAdapter().nullSafe()).enableComplexMapKeySerialization().setPrettyPrinting().create();

	public static boolean tryRead(File f, Map<NormalizedSimpleStack, Integer> map)
	{
		try {
			Map<NormalizedSimpleStack, Integer> m = read(f);
			map.clear();
			map.putAll(m);
			return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Map<NormalizedSimpleStack, Integer> read(File file) throws IOException
	{
		Type type = new TypeToken<Map<NormalizedSimpleStack, Integer>>() {}.getType();
		FileReader reader = new FileReader(file);
		Map<NormalizedSimpleStack, Integer> map = gson.fromJson(reader, type);
		reader.close();
		map.remove(null);
		return map;
	}

	public static void write(File file, Map<NormalizedSimpleStack, Integer> map) throws IOException
	{
		Type type = new TypeToken<Map<NormalizedSimpleStack, Integer>>() {}.getType();
		FileWriter writer = new FileWriter(file);
		gson.toJson(map, type, writer);
		writer.close();
	}
}
