package groovy;

import org.junit.Assert;
import org.junit.Test;

public class TestHelloWorld {

	@Test
	public void testSongToString() {
		Song sng  = new Song();
		sng.setArtist("Village People");
		sng.setName("Y.M.C.A");
		sng.setGenre("Disco");
//		System.out.println(sng.toString());
		Assert.assertEquals("Y.M.C.A, Village People, Disco",  sng.toString());
	}
}

