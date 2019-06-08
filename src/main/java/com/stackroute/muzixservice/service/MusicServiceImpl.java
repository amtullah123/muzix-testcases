package com.stackroute.muzixservice.service;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.exceptions.TrackNotFoundException;
import com.stackroute.muzixservice.repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.List;
@CacheConfig(cacheNames = "music")
@Service
@Primary
public class MusicServiceImpl implements MusicService{

private MusicRepository musicRepository;
public void simulateDelay()
{
    try {
        Thread.sleep(3000l);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

}
@Autowired
    public MusicServiceImpl(MusicRepository musicRepository)
{
    this.musicRepository=musicRepository;
}
@CacheEvict(value = "music",allEntries = true)
@Override
    public Music saveTrack(Music music)
{
    Music savedTrack=musicRepository.save(music);
    return savedTrack;
}
@Cacheable("music")
@Override
public List<Music> getAllTracks()
{
    simulateDelay();
List<Music> musiclist=(List<Music>) musicRepository.findAll();
return musiclist;
}

//@Override
//public Music displayTrackByName(String tname)
//{
//    return musicRepository.displayTrackByName(tname);
//}

@CacheEvict(value = "music",allEntries = true)
@Override
    public Music displayTrackById(int tid)
    {
        return musicRepository.findById(tid).get();
    }

    @Override
    public List<Music> updateTrack(Music music, int tid) {
        return null;
    }

//    @Override
//    public List<Music> displayTrack() {
//        return musicRepository.findAll();
//    }

@CacheEvict(value = "music",allEntries = true)
@Override
    public void deleteTrack(int tid) {
        musicRepository.deleteById(tid);

    }
@CacheEvict(value = "music",allEntries = true)
  @Override
    public Music updateTrackComments(Music music, int tid) throws TrackNotFoundException {
        if(!musicRepository.existsById(tid))
        {
            throw new TrackNotFoundException("track is not found");
        }
        Music music1=musicRepository.findById(tid).get();
        music1.setComments(music.getComments());
        return musicRepository.save(music1);
    }

    @PostConstruct
    public void loadData()
    {
        musicRepository.save(Music.builder().tid(1).tname("pal").comments("best song").build());
        musicRepository.save(Music.builder().tid(2).tname("pal pal").comments("old song").build());
        musicRepository.save(Music.builder().tid(3).tname("kamali").comments("new song").build());
    }


}











