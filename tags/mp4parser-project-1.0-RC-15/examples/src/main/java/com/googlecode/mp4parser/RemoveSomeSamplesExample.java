package com.googlecode.mp4parser;

import com.coremedia.iso.IsoFile;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.builder.FragmentedMp4Builder;
import com.googlecode.mp4parser.authoring.builder.SyncSampleIntersectFinderImpl;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;

/**
 * Removes some samples in the middle
 */
public class RemoveSomeSamplesExample {
    public static void main(String[] args) throws IOException {

        Movie originalMovie = MovieCreator.build(Channels.newChannel(RemoveSomeSamplesExample.class.getResourceAsStream("/count-english-audio.mp4")));

        Track audio = originalMovie.getTracks().get(0);

        Movie nuMovie = new Movie();
        nuMovie.addTrack(new AppendTrack(new CroppedTrack(audio, 0, 10), new CroppedTrack(audio, 100, audio.getSamples().size())));

        IsoFile out = new DefaultMp4Builder().build(nuMovie);
        FileOutputStream fos = new FileOutputStream(new File("output.mp4"));
        out.getBox(fos.getChannel());
        fos.close();
    }

}
