package com.stackroute.muzixservice;

import com.stackroute.muzixservice.domain.Music;
import com.stackroute.muzixservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixservice.repository.MusicRepository;
import com.stackroute.muzixservice.service.MusicService;
import com.stackroute.muzixservice.service.MusicServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MusicServiceTest {

    private Music music;
    private MusicService musicService;
@Mock
    private MusicRepository musicRepository;
@InjectMocks
    private MusicServiceImpl musicServiceImpl;
List<Music>musiclist=null;

@Before
    public void setUp()
{
    MockitoAnnotations.initMocks(this);
    music = new Music();
    music.setTid(1);
    music.setTname("dilbar");
    music.setComments("hit song");
    musiclist = new ArrayList<>();
    musiclist.add(music);
}

@Test
public void saveTrackTestSuccess() throws TrackAlreadyExistsException {

    when(musicRepository.save((Music)any())).thenReturn(music);
    Music savedTrack=musicRepository.save(music);
    Assert.assertEquals(music,savedTrack);

    //verify here verifies that userRepository save method is only called once
    verify(musicRepository,times(1)).save(music);

}

//    @Test(expected = TrackAlreadyExistsException.class)
//    public void saveTrackTestFailure() throws TrackAlreadyExistsException {
//        when(musicRepository.save((Music) any())).thenReturn(null);
//        Music savedTrack = musicService.saveTrack(music);
//        System.out.println("savedTrack" + savedTrack);
//        Assert.assertEquals(music,savedTrack);
//
////       doThrow(new TrackAlreadyExistsException()).when(musicRepository).findById(eq(1));
////       musicService.saveTrack(music);
//
//    }

    @Test
    public void testSaveTrackTestFailure() {
        Music testMuzix = new Music(1, "dilbar", "hit song");
        musicRepository.save(music);
        Optional<Music> fetchTrack = musicRepository.findById(music.getTid());
        Assert.assertNotSame(testMuzix, music);
    }





    @Test
    public void getAllTracks(){

        musicRepository.save(music);
        //stubbing the mock to return specific data
        when(musicRepository.findAll()).thenReturn(musiclist);
        List<Music> musiclist = (List<Music>) musicRepository.findAll();
        Assert.assertEquals(musiclist,musiclist);
    }

}
