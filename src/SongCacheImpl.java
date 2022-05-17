import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Xiangzhu Yan
 */
public class SongCacheImpl implements SongCache{

    private Map<String,Integer> PlayCount;
    private PriorityBlockingQueue<String> TopSong;

    /**
     * Constructor of SongCacheImpl
     */
    public SongCacheImpl() {
        //Use concurrentHashMap to ensure thread safe.
        PlayCount = new ConcurrentHashMap<>();

        //Use PriorityBlockingQueue to ensure thread safe
        TopSong = new PriorityBlockingQueue<String>(10,(a,b)-> {
            if(PlayCount.get(b).equals(PlayCount.get(a))) return a.compareTo(b);//If two songs have same played count, sort them in alphabetical order
            return PlayCount.get(b) - PlayCount.get(a);
        });
    }

    /**
     * Time Complexity O(logL)
     * L is the size of the priority queue
     * @param songId id of the song.
     * @param numPlays Times played of the song.
     */
    @Override
    public void recordSongPlays(String songId, int numPlays) {
        PlayCount.put(songId,PlayCount.getOrDefault(songId,0)+numPlays);
        TopSong.add(songId);
    }

    /**
     * Time Complexity O(1)
     * @param songId id of the song.
     * @return the number of plays for a song.
     */
    @Override
    public int getPlaysForSong(String songId) {
        if(!PlayCount.containsKey(songId)) return -1;
        return PlayCount.get(songId);
    }

    /**
     * Time Complexity O(L) + O(nLogL)
     * L is the size of the priority queue
     * @param n
     * @return
     */
    @Override
    public List<String> getTopNSongsPlayed(int n) {
        n = Math.min(n,PlayCount.size());//n can not be larger than the size of the hashmap

        List<String> result = new ArrayList<>();
        Set<String> set = new HashSet<>();  //Make sure we don't add same songId to the result list.

        while(n!=0){
            String song = TopSong.poll();
            if(!set.contains(song)){
                set.add(song);
                result.add(song);
                n--;
            }
        }

        for(String s : set) TopSong.add(s); //Push songId back to the priority queue.

        return result;
    }



}
