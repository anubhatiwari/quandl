package quandl.test;

import java.util.HashMap;
import java.util.Map;

public class ScenarioCacheManager {

	private final Map<String, Object> cache = new HashMap<String, Object>();

	private static ScenarioCacheManager scenarioCacheManager = null;

	private ScenarioCacheManager() {
	}

	public static ScenarioCacheManager getInstance() {
		if (scenarioCacheManager == null) {

			scenarioCacheManager = new ScenarioCacheManager();
			return scenarioCacheManager;
		} else {

			return scenarioCacheManager;
		}
	}

	public void store(final String key, final Object value) {
		cache.put(key, value);

	}

	public Object retrieve(final String key) {
		return cache.get(key);
	}

	public String retrieveAsString(final String key) {
		return (String) cache.get(key);
	}

	public boolean retrieveAsBoolean(final String key) {
		return Boolean.valueOf(cache.get(key).toString());
	}

	public Object remove(final String key) {
		return cache.remove(key);
	}

	public void clear() {
		cache.clear();
	}

	/*
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ScenarioCacheManager [cache=" + cache + "]";
	}

}
