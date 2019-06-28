package in.xiandan.mmrc;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;

import java.io.IOException;

import in.xiandan.mmrc.datasource.DataSource;
import in.xiandan.mmrc.datasource.FileDescriptorSource;
import in.xiandan.mmrc.datasource.FileSource;
import in.xiandan.mmrc.datasource.HTTPSource;
import in.xiandan.mmrc.datasource.UriSource;

/**
 * 原生的Retriever
 *
 * @author dengyuhan
 * created 2019-06-25 11:26
 */
public class AndroidMediaMetadataRetriever implements IMediaMetadataRetriever {
    private MediaMetadataRetriever mRetriever;

    public AndroidMediaMetadataRetriever() {
        this.mRetriever = new MediaMetadataRetriever();
    }

    @Override
    public void setDataSource(@NonNull DataSource source) throws IOException {
        if (source instanceof FileSource) {
            //File
            this.mRetriever.setDataSource(((FileSource) source).source().getAbsolutePath());
        } else if (source instanceof HTTPSource) {
            //HTTP
            final HTTPSource httpSource = ((HTTPSource) source);
            this.mRetriever.setDataSource(httpSource.source(), httpSource.getHeaders());
        } else if (source instanceof FileDescriptorSource) {
            //FileDescriptor
            final FileDescriptorSource fdSource = ((FileDescriptorSource) source);
            this.mRetriever.setDataSource(fdSource.source(), fdSource.getOffset(), fdSource.getLength());
        } else if (source instanceof UriSource) {
            //Uri
            final UriSource uriSource = ((UriSource) source);
            this.mRetriever.setDataSource(uriSource.getContext(), uriSource.source());
        } else {
            MediaRetriever.Config.get().setCustomDataSource(this);
        }
    }


    @Override
    public Bitmap getFrameAtTime() {
        return this.mRetriever.getFrameAtTime();
    }

    @Override
    public Bitmap getFrameAtTime(long timeUs, int option) {
        return this.mRetriever.getFrameAtTime(timeUs, option);
    }

    @Override
    public Bitmap getScaledFrameAtTime(long timeUs, int option, int dstWidth, int dstHeight) {
        return this.mRetriever.getScaledFrameAtTime(timeUs, option, dstWidth, dstHeight);
    }

    @Override
    public byte[] getEmbeddedPicture() {
        return this.mRetriever.getEmbeddedPicture();
    }

    @Override
    public String extractMetadata(String keyCode) {
        try {
            return this.mRetriever.extractMetadata(Integer.parseInt(keyCode));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void release() {
        this.mRetriever.release();
    }
}
