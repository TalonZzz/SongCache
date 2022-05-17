import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

public class SongCacheTest {
    @Test
    public void cacheIsWorking() {
        SongCache cache = new SongCacheImpl();
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 3);
        cache.recordSongPlays("ID-4", 4);
        cache.recordSongPlays("ID-5", 5);
        cache.recordSongPlays("ID-6", 6);
        cache.recordSongPlays("ID-6", 6);


        assertThat(cache.getPlaysForSong("ID-6"), is(12));
        assertThat(cache.getPlaysForSong("ID-9"), is(-1));


        assertThat(cache.getTopNSongsPlayed(2).contains("ID-5"), is(true));
        assertThat(cache.getTopNSongsPlayed(2).contains("ID-6"), is(true));

        assertThat(cache.getTopNSongsPlayed(0).size(), is(0));
    }
}
