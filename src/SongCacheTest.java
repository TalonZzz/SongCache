import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
/**
 * @author Xiangzhu Yan
 */
public class SongCacheTest {

    /**
     * Test the basic functionalities of our songCache
     */
    @Test
    public void BasicTest() {
        SongCache cache = new SongCacheImpl();
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 3);

        assertThat(cache.getPlaysForSong("ID-1"), is(1));
        assertThat(cache.getPlaysForSong("ID-2"), is(2));
        assertThat(cache.getPlaysForSong("ID-3"), is(3));
        assertThat(cache.getPlaysForSong("ID-4"), is(-1));

        assertThat(cache.getTopNSongsPlayed(2).contains("ID-1"), is(false));
        assertThat(cache.getTopNSongsPlayed(2).contains("ID-2"), is(true));
        assertThat(cache.getTopNSongsPlayed(2).contains("ID-3"), is(true));
        assertThat(cache.getTopNSongsPlayed(0).size(), is(0));
    }

    /**
     * Test the case that all songs have same played count.
     * Since all three songs have same count, if we want to get top 2, we will sort them in alphabetical order and
     * get first two songs(ID-1 and ID-2)
     */
    @Test
    public void TestSameCount() {
        SongCache cache = new SongCacheImpl();
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 1);
        cache.recordSongPlays("ID-3", 1);

        assertThat(cache.getTopNSongsPlayed(2).contains("ID-1"), is(true));
        assertThat(cache.getTopNSongsPlayed(2).contains("ID-2"), is(true));
        assertThat(cache.getTopNSongsPlayed(2).contains("ID-3"), is(false));
    }

    /**
     * If we keep play the same song, we will keep add duplicates to the priorityQueue, we need to make sure that
     * the list returned from getTopNSongsPlayed() will not contain duplicates.
     */
    @Test
    public void TestPlayRepeatedly() {
        SongCache cache = new SongCacheImpl();
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 3);
        cache.recordSongPlays("ID-3", 1);
        cache.recordSongPlays("ID-3", 1);
        cache.recordSongPlays("ID-3", 1);
        cache.recordSongPlays("ID-3", 1);

        assertThat(cache.getTopNSongsPlayed(2).contains("ID-1"), is(false));
        assertThat(cache.getTopNSongsPlayed(2).contains("ID-2"), is(true));
        assertThat(cache.getTopNSongsPlayed(2).contains("ID-3"), is(true));
    }

    //TODO: Need to test if our SongCache is threadsafe!
}



