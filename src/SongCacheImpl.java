import java.util.*;

public class SongCacheImpl implements SongCache{

    private Map<String,Integer> PlayCount;
    private PriorityQueue<String> TopSong;

    public SongCacheImpl() {
        PlayCount = new HashMap<>();
        TopSong = new PriorityQueue<>((a,b)->PlayCount.get(b) - PlayCount.get(a));
    }

    @Override
    public void recordSongPlays(String songId, int numPlays) {
        PlayCount.put(songId,PlayCount.getOrDefault(songId,0)+numPlays);
        TopSong.add(songId);
    }

    @Override
    public int getPlaysForSong(String songId) {
        if(!PlayCount.containsKey(songId)) return -1;
        return PlayCount.get(songId);
    }

    @Override
    public List<String> getTopNSongsPlayed(int n) {
        n = Math.min(n,PlayCount.size());

        List<String> result = new ArrayList<>();
        Set<String> set = new HashSet<>();

        while(n!=0){
            String song = TopSong.poll();
            if(!set.contains(song)){
                set.add(song);
                result.add(song);
                n--;
            }
        }

        for(String s : set) TopSong.add(s);

        return result;
    }



}
