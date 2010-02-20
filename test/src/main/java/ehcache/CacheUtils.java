package ehcache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheUtils {
	
	private static CacheManager cacheManager = null;
	
	private static CacheManager getCacheManager() throws Exception {
		if(cacheManager == null) {
			cacheManager = CacheManager.create();
		}
		return cacheManager;
	}
	
	private static Cache getCache(String cacheName) throws Exception {
		Cache cache = getCacheManager().getCache(cacheName);
		if(cache == null) {
			throw new Exception("没有定义cache: " + cacheName);
		}
		return cache;
	}
	
	
	public static void put(String cacheName, Object key, Object value) throws Exception {
		Cache cache = getCache(cacheName);
		Element element = new Element((Serializable)key, (Serializable)value);
		cache.put(element);  		
	}
	
    public static Object get(String cacheName, Object key) throws Exception {
    	Object obj = null;
    	Cache cache = getCache(cacheName);
    	Element element = cache.get((Serializable)key);
    	if(element != null) {
    		obj = element.getValue();
    	}
    	return obj;
	}
    
    
    public static void clearCache(String cacheName) throws Exception {
    	Cache cache = getCache(cacheName);
    	cache.removeAll();
    }
    
    public static void removeCache(String cacheName) throws Exception {
    	getCacheManager().removeCache(cacheName);
    }
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CacheUtils.put("HBE_CACHE", "o1", "11111");
			System.out.println(CacheUtils.get("HBE_CACHE", "o1"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
